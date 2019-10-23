package parser;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import compilers.Parser;
import compilers.Lexer;
import compilers.Token;
import compilers.Symbol;
import compilers.SymbolTable;
import compilers.AbstractSyntax;

import globals.TCglobals;
import abstractSyntax.*;

import output.TCoutput;
import parser.TCtoken.Tokens;
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
        Token token = buff;
        accept(t);
        return token;
    }

    public AbstractSyntax parse() {
        buff = scanner.getToken();
        Program p = program();
        return p;
    }

	private Boolean isTypeToken(Token t) {
        TCtoken.Tokens tok = (Tokens) t.getTokenType();
        return tok.equals(TCtoken.Tokens.INT) || tok.equals(TCtoken.Tokens.CHAR);
    }

    private Program program() {
        List<Definition> definitionList = new ArrayList<>();
        while (isTypeToken(buff)) {
            definitionList.add(definition());
        }
        accept(TCtoken.Tokens.EOF);

        return new Program(definitionList);
    }

    private Definition definition() {
        Type ty = type();
        Identifier id = identifier();
        if (buff.getTokenType().equals(TCtoken.Tokens.LPAREN)) {
            List<VariableDefinition> fd = functionHeader();
			Statement s = compoundStatement();
            return new FunctionDefinition(ty, id, fd, s);
        }
        else {
            accept(TCtoken.Tokens.SEMICOLON);
            return new VariableDefinition(ty, id);
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

    private List<VariableDefinition> functionHeader() {
        accept(TCtoken.Tokens.LPAREN);
        List<VariableDefinition> fpl = ArrayList<>();
        if (isTypeToken(buff)) {
            formalParamList(fpl);
        }
        accept(TCtoken.Tokens.RPAREN);

        return fpl;
    }
    
    private Statement functionBody() {
    	return compoundStatement();
    }
    
    private List<VariableDefinition> formalParamList(List<VariableDefinition> paramlist) {
    	Type ty = type();
    	Identifier id = identifier();
    	
    	paramlist.add(new VariableDefinition(ty, id));
    	
    	if (buff.getTokenType().equals(TCtoken.Tokens.COMMA))
    	{
    		return formalParamList(paramlist);
    	}
    	else
    	{
    		return paramlist;
    	}
    }

	private Identifier identifier() {
		return new Identifier(acceptSave(TCtoken.Tokens.ID).getLexeme())
	}
    
    private Statement statement() {
    	enteringDEBUG("statement");
    	switch((TCtoken.Tokens)buff.getTokenType()) {
    		case BREAK:
    			return breakStatement();    			
    		case LCURLY:
    			return compoundStatement();
    		case IF:
    			return ifStatement();
    		case SEMICOLON:
    			return nullStatement();
    		case WHILE:
    			return whileStatement();
    		case READ:
    			return readStatement();
    		case WRITE:
    			return writeStatement();
    		case NEWLINE:
    			return newlineStatement();
			case RETURN:
				return returnStatement();
    		default:
    			return expressionStatement();
    	}
    }
    
    private void enteringDEBUG(String string) {
		// TODO Auto-generated method stub

		// please use this as a reference to fill this out,
		// and make an exitingDEBUG() as well
		// every method needs an entering and exiting debug call
		
	}

	private NewlineStatement newlineStatement() {
		accept(TCtoken.Tokens.NEWLINE);
		accept(TCtoken.Tokens.SEMICOLON);

		return new NewlineStatement();
	}

	private BreakStatement breakStatement() {
		accept(TCtoken.Tokens.BREAK);
		accept(TCtoken.Tokens.SEMICOLON);

		return new BreakStatement();
	}

	private CompoundStatement compoundStatement() {
		ArrayList<VariableDefinition> dl = new ArrayList<>();
		ArrayList<Statement> sl = new ArrayList<>();

    	accept(TCtoken.Tokens.LCURLY);
		while (isTypeToken(buff))
		{
			Type ty = type();
    		Identifier id = identifier();
			accept(TCtoken.Tokens.SEMICOLON);
			dl.add(ty, id);
		}
		while (!buff.getTokenType().equals(TCtoken.Tokens.RCURLY))
		{
			sl.add(statement());	
		}
		accept(TCtoken.Tokens.RCURLY);

		return new CompoundStatement(dl, sl);
    }

    private IfStatement ifStatement() {
    	accept(TCtoken.Tokens.IF);
		accept(TCtoken.Tokens.RPAREN);
		Expression condition = expression(new ArrayList<Expression>());
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
		Expression condition = expression(new ArrayList<Expression>());
		accept(TCtoken.Tokens.LPAREN);
		Statement s = statement();

		return new WhileStatement(condition, s);
    }
    
    private ReadStatement readStatement() {
		ArrayList<String> ids = new ArrayList<String>();

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
    	List<Expression> ap = actualParameters(new ArrayList<Expression>());
    	accept(TCtoken.Tokens.RPAREN);
    	accept(TCtoken.Tokens.SEMICOLON);
    	
    	return new WriteStatement(ap);
    }

	private ReturnStatement returnStatement() {
		accept(TCtoken.Tokens.RETURN);
		Expression expr = expression();

		return new ReturnStatement(expr);
	}

	private Operation operation(TCtoken.Tokens t) {
		return new Operation(acceptSave(t).getLexeme());
	}
    
    private ExpressionStatement expressionStatement() {
    	Expression expr = expression();
    	accept(TCtoken.Tokens.SEMICOLON);
    	
    	return new ExpressionStatement(expr);
    }
    
	/*  
		THIS SHOULD BE THE MODEL FOR ALL OF THE OTHER RECURSIVE
		EXPRESSION METHODS
		please follow this template exactly
	*/
    private Expression expression() {
		Expression left = relopExpression();
		if (buff.getTokenType().equals(TCtoken.Tokens.ASSIGNOP)) {
			Operation op = operation(TCtoken.Tokens.ASSIGNOP);
			Expression right = expression();
			return new Expr(op, left, right);
		}
    	else {
    		return left;
    	}
    }
    
    private Expression relopExpression(List<Expression> seList) {
    	seList.add(simpleExpression(new ArrayList<Expression>()));
    	
    	if (scanner.getToken().getTokenType().equals(TCtoken.Tokens.RELOP)) {
    		accept(TCtoken.Tokens.RELOP);
    		relopExpression(seList);
    	}
    	else {
    		return new RelopExpression(seList);
    	}
    	return new RelopExpression(seList);
    }
    
    private Expression simpleExpression(List<Expression> termList) {
    	termList.add(term(new ArrayList<Expression>()));
    	
    	if (scanner.getToken().getTokenType().equals(TCtoken.Tokens.ADDOP)) {
    		accept(TCtoken.Tokens.ADDOP);
    		simpleExpression(termList);
    	}
    	else {
    		return new SimpleExpression(termList);
    	}
    	return new SimpleExpression(termList);
    }
    
    private Expression term(List<Expression> primList) {
    	primList.add(primary());
    	
    	if (scanner.getToken().getTokenType().equals(TCtoken.Tokens.MULOP)) {
    		term(primList);
    	}
    	else {
        	return new Term(primList);
    	}
    	return new Term(primList);
    }
    
    private Expression primary() {
		TCtoken sw = buff;
		buff = scanner.getToken();

    	switch((TCtoken.Tokens)sw.getTokenType()) {
    		case ID:
				Identifier id = new Identifier(sw.getLexeme());
				if (buff.getTokenType().equals(TCtoken.Tokens.LPAREN))
				{
					List<Expression> asp = functionCall();
					return new FunctionCall(id, asp); 
				}
				else
				{
	    			return new Identifier(sw.getLexeme());
				}
    		case NUMBER:
				return new Number(sw.getLexeme());
    		case STRING:
    			return new StringLiteral(sw.getLexeme());
    		case CHAR:
    			return new CharLiteral(sw.getLexeme());
    		case LPAREN:
    			Expression expr = expression(new ArrayList<Expression>());
    			accept(TCtoken.Tokens.RPAREN);
				return new Primary(expr);
    		case NOT:
    			Expression p = primary();
				p.setNot();
    			return p;
			case ADDOP:
				Expression p = primary();
				if (sw.getLexeme().equals("-"))
					p.setMinus();
				return p;
    	}
    }
    
    private List<Expression> functionCall() {
    	accept(TCtoken.Tokens.LPAREN);
    	List<Expression> ap = actualParameters(new ArrayList<Expression>());
    	accept(TCtoken.Tokens.RPAREN);
    	return ap;
    }
    
    private List<Expression> actualParameters(List<Expression> params) {
    	params.add(expression(params));
    	
    	if (scanner.getToken().equals(TCtoken.Tokens.COMMA)) {
    		accept(TCtoken.Tokens.COMMA);
    		actualParameters(params);
    	}

    	return params;
    }
}