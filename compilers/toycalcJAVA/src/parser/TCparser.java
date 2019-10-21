package parser;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.EnumMap;

import compilers.Parser;
import compilers.Lexer;
import compilers.Token;
import compilers.Symbol;
import compilers.SymbolTable;
import compilers.AbstractSyntax;

import globals.TCglobals;
import abstractSyntax.*;

import output.TCoutput;
import symTable.TCsymbol;
import symTable.TCsymTable;

/*
 Concrete syntax
 ---------------
 ToyCProgram --> Definition* EOF
 Definition --> Type identifier (FunctionDefintion | ; )
 Type --> int | char
 FunctionDefinition --> FunctionHeader FunctionBody
 FunctionHeader --> ( FormalParamList? )
 FunctionBody --> CompoundStatement
 FormalParamList --> Type identifier (, Type identifier)*
 Statement --> ExpressionStatement
             | BreakStatement
             | CompoundStatement
             | IfStatement
             | NullStatement
             | ReturnStatement
             | WhileStatement
             | ReadStatement
             | WriteStatement
             | NewLineStatement
 ExpressionStatement --> Expression ;
 BreakStatement --> break ;
 CompoundStatement --> { (Type identifier ;)* Statement* }
 IfStatement --> if ( Expression ) Statement (else Statement)?
 NullStatement --> ;
 WhileStatement --> while ( Expression ) Statement
 ReadStatement --> read ( identifier (, identifier)* ) ;
 WriteStatement --> write ( ActualParameters ) ;
 NewLineStatement --> newline ;
 Expression --> RelopExpression (assignop RelopExpression)*
 RelopExpression --> SimpleExpression (relop SimpleExpression)*
 SimpleExpression --> Term (addop Term)*
 Term --> Primary (mulop Primary)*
 Primary --> identifier FunctionCall?
           | number
           | stringConstant
           | charConstant
           | ( Expression )
           | (- | not) Primary
 FunctionCall --> ( ActualParameters? )
 ActualParameters --> Expression (, Expression)*
*/

public class TCparser implements Parser {

    Lexer scanner;
    Token buff;

    public TCparser(Lexer s) {
        scanner = s;
    }

    private void accept(TCtoken.Tokens t) {
        if (t.equals(buff.getTokenType()))
            buff = scanner.getToken();
        else 
            TCoutput.reportSYNTAX_ERROR(scanner,t+" expected");
    }

    private Token acceptSave(TCtoken.Tokens t) {
        Token t = buff;
        accept(t);
        return t;
    }

    public AbstractSyntax parse() {
        buff = scanner.getToken();
        ToyCProgram p = program();
        checkIfAllLabelTargetsAreDefined(p);
        return p;
    }

    private Boolean isTypeToken(Token t) {
        TCtoken.Tokens tok = t.getTokenType();
        return tok.equals(TCtoken.Tokens.INT) || tok.equals(TCtoken.Tokens.CHAR);
    }

    private ToyCProgram program() {
        List<Definition> definitionList = new ArrayList<>();
        while (isTypeToken(buff)) {
            definitionList.add(definition());
        }
        accept(TCtoken.Tokens.EOF);

        return new ToyCProgram(definitionList);
    }

    private Definition definition() {
        Type ty = type();
        String id = acceptSave(TCtoken.Tokens.ID).getLexeme();
        if (buff.getTokenType().equals(TCtoken.Tokens.LPAREN)) {
            FunctionDefinition fd = functionDefinition();
            return new Definition(ty, id, fd);
        }
        else {
            accept(TCtoken.Tokens.SEMICOLON);
            return new Definition(ty, id);
        }
    }

    private Type type() {
        String ty = null;
        if (buff.getTokenType().equals(TCtoken.Tokens.INT))
            ty = "int";
        else if (buff.getTokenType().equals(TCtoken.Tokens.CHAR))
            ty = "char";
        else
            TCoutput.reportSYNTAX_ERROR(scanner, "expected type");

        buff = scanner.getToken();
        return new Type(ty);
    }

    private FunctionDefinition functionDefinition() {
        FunctionHeader fh = functionHeader();
        FunctionBody fb = functionBody();

        return new FunctionDefinition(fh, fb);
    }

    private FunctionHeader functionHeader() {
        accept(TCtokens.Tokens.LPAREN);
        FormalParamList fpl = null;
        if (isTypeToken(buff)) {
        	paramlist<Param> = new List<Param>();
            fpl = formalparamlist(paramlist);
        }
        accept(TCtokens.Tokens.RPAREN);

        return new FunctionHeader(fpl);
    }
    
    private FunctionBody functionBody() {
    	return new FunctionBody(compoundStatement());
    }
    
    private FormalParamList formalParamList(List<Param> paramlist) {
    	Type ty = type();
    	String id = acceptSave(TCtoken.Tokens.ID).getLexeme();
    	
    	paramlist.add(new Param(ty, id));
    	
    	if (buff.getTokenType().equals(TCtoken.Tokens.COMMA))
    	{
    		return formalParamList(paramlist);
    	}
    	else
    	{
    		return new FormalParamList(paramlist);
    	}
    }
    
    private Statement statement() {
    	Statement s = null;
    	enteringDEBUG("statement");
    	
    	switch((TCtoken.Tokens)buff.getTokenType()) {
    		case BREAK:
    			if (scanner.getToken().getTokenType().equals(TCtoken.Tokens.SEMICOLON)) {
    				s = new BreakStatement();
    			}
    			else {
    				TCoutput.reportSYNTAX_ERROR(scanner, TCtoken.Tokens.SEMICOLON + " expected");
    			}
    			break;
    			
    		case LCURLY:
    			s = compoundStatement();
    			break;
    			
    		case IF:
    			s = ifStatement();
    			break;
    			
    		case SEMICOLON:
    			s = new NullStatement();
    			break;
    			
    		case WHILE:
    			s = whileStatement();
    			break;
    			
    		case READ:
    			s = readStatement();
    			break;
    			
    		case WRITE:
    			s = writeStatement();
    			break;
    			
    		case NEWLINE:
    			if (scanner.getToken().getTokenType().equals(TCtoken.Tokens.SEMICOLON)) {
    				s = new NewlineStatement();
    			}
    			else {
    				TCoutput.reportSYNTAX_ERROR(scanner, TCtoken.Tokens.SEMICOLON + " expected");
    			}
    			break;
    			
    		case ID:
    			s = expressionStatement();
    			break;
    	}
    	return s;
    }
    
    private CompoundStatement compoundStatement() {
		ArrayList<Definition> dl = ArrayList<>();
		ArrayList<Statement> sl = ArrayList<>();

    	accept(TCtoken.Tokens.LCURLY);
		while (isTypeToken(buff))
		{
			Type ty = type();
    		String id = acceptSave(TCtoken.Tokens.ID).getLexeme();
			accept(TCtoken.Tokens.SEMICOLON);
			dl.add(ty, id);
		}
		while (!buff.getTokenType().equals(TCtoken.Tokens.RCURLY))
		{
			sl.add(Statement());	
		}
		accept(TCtoken.Tokens.RCURLY);

		return new CompoundStatement(dl, sl);
    }

    private IfStatement ifStatement() {
    	accept(TCtoken.Tokens.IF);
		accept(TCtoken.Tokens.RPAREN);
		Expression condition = expression();
		accept(TCtoken.Tokens.LPAREN);
		Statement ifs = statement();
		if (buff.getTokenType().equals(TCtoken.Tokens.ELSE))
		{
			accept(TCtoken.Tokens.ELSE);
			Statement els = statement();
			return new IfStatement(condition, ifs, els);
		}
		else
		{
			return new IfStatement(condition, ifs);
		}
    }
    
    private WhileStatement whileStatement() {
    	accept(TCtoken.Tokens.WHILE);
		accept(TCtoken.Tokens.RPAREN);
		Expression condition = expression();
		accept(TCtoken.Tokens.LPAREN);
		Statement s = statement();

		return new WhileStatement(condition, s);
    }
    
    private ReadStatement readStatement() {
		ArrayList<String> ids = ArrayList<>();

		accept(TCtoken.Tokens.READ);
		accept(TCtoken.Tokens.RPAREN);
		ids.add(acceptSave(TCtoken.Tokens.ID).getLexeme());
		while (buff.getTokenType().equals(TCtoken.Tokens.COMMA))
		{
			accept(TCtoken.Tokens.COMMA);
			ids.add(acceptSave(TCtoken.Tokens.ID).getLexeme());
		}
		accept(TCtoken.Tokens.LPAREN);

		return new ReadStatement(ids);
    }
    
    private WriteStatement writeStatement() {
    	accept(TCtoken.Tokens.WRITE);
    	accept(TCtoken.Tokens.LPAREN);
    	ActualParameters ap = actualParameters(new List<Expression>());
    	accept(TCtoken.Tokens.RPAREN);
    	accept(TCtoken.Tokens.SEMICOLON);
    	
    	return new WriteStatement(ap);
    }
    
    private ExpressionStatement expressionStatement() {
    	Expression expr = expression(new List<RelopExpression>());
    	accept(TCtoken.Tokens.SEMICOLON);
    	
    	return new ExpressionStatement(expr);
    }
    
    private Expression expression(List<RelopExpression> reList) {
    	reList.add(relopExpression(new List<SimpleExpression>()));
    	
    	if (buff.getTokenType().equals(TCtoken.Tokens.ASSIGNOP)) {
    		accept(TCtoken.Tokens.ASSIGNOP);
    		relopExpression(reList);
    	}
    	else {
    		return new RelopExpression(reList);
    	}
    }
    
    private RelopExpression relopExpression(List<SimpleExpression> seList) {
    	seList.add(simpleExpression(new List<Term>()));
    	
    	if (scanner.getToken().getTokenType().equals(TCtoken.Tokens.RELOP)) {
    		accept(TCtoken.Tokens.RELOP);
    		relopExpression(seList);
    	}
    	else {
    		return new RelopExpression(seList);
    	}
    }
    
    private SimpleExpression simpleExpression(List<Term> termList) {
    	termList.add(term(new List<Primary>()));
    	
    	if (scanner.getToken().getTokenType().equals(TCtoken.Tokens.ADDOP)) {
    		accept(TCtoken.Tokens.ADDOP);
    		simpleExpression(termList);
    	}
    	else {
    		return new SimpleExpression(termList);
    	}
    }
    
    private Term term(List<Primary> primList) {
    	primList.add(primary());
    	
    	if (scanner.getToken().getTokenType().equals(TCtoken.Tokens.MULOP)) {
    		term(primList);
    	}
    	else {
        	return new Term(primList);
    	}
    }
    
    private Primary primary() {
    	switch((TCtoken.Tokens)buff.getTokenType()) {
    	
    		case ID:
    			;
    			break;
    		case NUMBER:
    			break;
    		case STRING:
    			break;
    		case CHAR:
    			break;
    		case LPAREN:
    			accept(TCtoken.Tokens.LPAREN);
    			Expression expr = expression();
    			accept(TCtoken.Tokens.RPAREN);
				return Expr(expr);
    			break;
    		case NOT: 
    			Primary p = primary();
    			break;
    	}
    }
    
    private FunctionCall functionCall() {
    	accept(TCtoken.Tokens.LPAREN);
    	ActualParameters ap = actualParameters(new List<Expression>());
    	accept(TCtoken.Tokens.RPAREN);
    	return new FunctionCall(ap);
    }
    
    private ActualParameters actualParameters(List<Expression> params) {
    	params.add(expression());
    	
    	if (scanner.getToken().getTokenType.equals(TCtoken.Tokens.COMMA)) {
    		accept(TCtoken.Tokens.COMMA);
    		actualParameters(params);
    	}
    	else {
    		return new ActualParameters(params);
    	}
    }
}