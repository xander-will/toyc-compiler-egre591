package parser;

import java.util.List;
import java.util.ArrayList;
import compilers.Parser;
import compilers.Lexer;
import compilers.Token;
import compilers.AbstractSyntax;

import abstractSyntax.*;
import abstractSyntax.Number;
import output.TCoutput;
import parser.TCtoken.Tokens;
import globals.TCglobals;

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
	Boolean verbose;
	byte debugLevel;

	int var_num = 0;

	public TCparser(Lexer s) {
		scanner = s;
		verbose = TCglobals.verbose;
		debugLevel = TCglobals.debug;
	}

	private void accept(TCtoken.Tokens t) {
		if (t.equals(buff.getTokenType()))
			buff = scanner.getToken();
		else
			TCoutput.reportSYNTAX_ERROR(scanner, t + " expected");
	}

	private Token acceptSave(TCtoken.Tokens t) {
		Token token = buff;
		accept(t);
		return token;
	}

	private Boolean isTypeToken(Token t) {
		TCtoken.Tokens tok = (Tokens) t.getTokenType();
		return tok.equals(TCtoken.Tokens.INT) || tok.equals(TCtoken.Tokens.CHAR);
	}

	private void enteringDEBUG(String string) {
		if (verbose || debugLevel == 2 || debugLevel == 0)
			System.err.println("[PARSER] ENTERING [" + string + "]");
	}

	private void exitingDEBUG(String string) {
		if (verbose || debugLevel == 2 || debugLevel == 0)
			System.err.println("[PARSER] EXITING [" + string + "]");
	}

	public AbstractSyntax parse() {
		enteringDEBUG("abstractSyntax");
		buff = scanner.getToken();
		Program p = program();
		exitingDEBUG("abstractSyntax");
		return p;
	}

	private Program program() {
		enteringDEBUG("program");
		List<Definition> definitionList = new ArrayList<Definition>();
		while (isTypeToken(buff)) {
			definitionList.add(definition());
			var_num = 0;
		}
		accept(TCtoken.Tokens.EOF);
		exitingDEBUG("program");
		return new Program(definitionList);
	}

	private Definition definition() {
		enteringDEBUG("definition");
		Type ty = type();
		Identifier id = identifier();
		if (buff.getTokenType().equals(TCtoken.Tokens.LPAREN)) {
			List<VariableDefinition> fd = functionHeader();
			Statement s = compoundStatement();
			exitingDEBUG("definition");
			return new FunctionDefinition(ty, id, fd, s, var_num);
		} else {
			accept(TCtoken.Tokens.SEMICOLON);
			exitingDEBUG("definition");
			return new VariableDefinition(ty, id);
		}
	}

	private Type type() {
		enteringDEBUG("type");
		String ty = "";
		if (buff.getTokenType().equals(TCtoken.Tokens.INT))
			ty = "int";
		else if (buff.getTokenType().equals(TCtoken.Tokens.CHAR))
			ty = "char";
		else
			TCoutput.reportSYNTAX_ERROR(scanner, "expected type");

		buff = scanner.getToken();
		exitingDEBUG("type");
		return new Type(ty);
	}

	private List<VariableDefinition> functionHeader() {
		enteringDEBUG("functionHeader");
		accept(TCtoken.Tokens.LPAREN);
		List<VariableDefinition> fpl = new ArrayList<VariableDefinition>();
		if (isTypeToken(buff)) {
			formalParamList(fpl);
		}
		accept(TCtoken.Tokens.RPAREN);
		exitingDEBUG("functionHeader");
		return fpl;
	}

	private List<VariableDefinition> formalParamList(List<VariableDefinition> paramlist) {
		enteringDEBUG("formalParamList");
		Type ty = type();
		Identifier id = identifier();

		paramlist.add(new VariableDefinition(ty, id));
		var_num++;

		if (buff.getTokenType().equals(TCtoken.Tokens.COMMA)) {
			exitingDEBUG("formalParamList");
			return formalParamList(paramlist);
		} else {
			exitingDEBUG("formalParamList");
			return paramlist;
		}
	}

	private Identifier identifier() {
		enteringDEBUG("identifier");
		Identifier id = new Identifier(acceptSave(TCtoken.Tokens.ID).getLexeme());
		exitingDEBUG("identifier");
		return id;
	}

	private Statement statement() {
		enteringDEBUG("statement");
		Statement statement;
		switch ((TCtoken.Tokens) buff.getTokenType()) {
		case BREAK:
			statement = breakStatement();
			break;
		case LCURLY:
			statement = compoundStatement();
			break;
		case IF:
			statement = ifStatement();
			break;
		case SEMICOLON:
			statement = nullStatement();
			break;
		case WHILE:
			statement = whileStatement();
			break;
		case READ:
			statement = readStatement();
			break;
		case WRITE:
			statement = writeStatement();
			break;
		case NEWLINE:
			statement = newlineStatement();
			break;
		case RETURN:
			statement = returnStatement();
			break;
		default:
			statement = expressionStatement();
			break;
		}

		exitingDEBUG("statement");
		return statement;
	}

	private NullStatement nullStatement() {
		enteringDEBUG("nullStatement");
		accept(TCtoken.Tokens.SEMICOLON);
		exitingDEBUG("nullStatement");
		return new NullStatement();
	}

	private NewlineStatement newlineStatement() {
		enteringDEBUG("newlineStatement");
		accept(TCtoken.Tokens.NEWLINE);
		accept(TCtoken.Tokens.SEMICOLON);
		exitingDEBUG("newlineStatement");
		return new NewlineStatement();
	}

	private BreakStatement breakStatement() {
		enteringDEBUG("breakStatement");
		accept(TCtoken.Tokens.BREAK);
		accept(TCtoken.Tokens.SEMICOLON);
		exitingDEBUG("breakStatement");
		return new BreakStatement();
	}

	private CompoundStatement compoundStatement() {
		enteringDEBUG("compoundStatement");
		ArrayList<VariableDefinition> dl = new ArrayList<VariableDefinition>();
		ArrayList<Statement> sl = new ArrayList<Statement>();
		ReturnStatement rs = null;

		accept(TCtoken.Tokens.LCURLY);
		while (isTypeToken(buff)) {
			Type ty = type();
			Identifier id = identifier();
			accept(TCtoken.Tokens.SEMICOLON);
			dl.add(new VariableDefinition(ty, id));
			var_num++;
		}
		while (!buff.getTokenType().equals(TCtoken.Tokens.RCURLY)) {
			// if (buff.getTokenType().equals(TCtoken.Tokens.RETURN)) {
			// sl.add(rs = returnStatement());
			// }
			sl.add(statement());
		}
		// if (rs == null) {
		// TCoutput.reportSYNTAX_ERROR(scanner, "reached end of function without return
		// statement");
		// }
		accept(TCtoken.Tokens.RCURLY);

		exitingDEBUG("compoundStatement");
		return new CompoundStatement(dl, sl);
	}

	private IfStatement ifStatement() {
		enteringDEBUG("ifStatement");
		accept(TCtoken.Tokens.IF);
		accept(TCtoken.Tokens.LPAREN);
		Expression condition = expression();
		accept(TCtoken.Tokens.RPAREN);
		Statement ifs = statement();
		if (buff.getTokenType().equals(TCtoken.Tokens.ELSE)) {
			accept(TCtoken.Tokens.ELSE);
			Statement els = statement();
			exitingDEBUG("ifStatement");
			return new IfStatement(condition, ifs, els);
		} else {
			exitingDEBUG("ifStatement");
			return new IfStatement(condition, ifs);
		}
	}

	private WhileStatement whileStatement() {
		enteringDEBUG("whileStatement");
		accept(TCtoken.Tokens.WHILE);
		accept(TCtoken.Tokens.LPAREN);
		Expression condition = expression();
		accept(TCtoken.Tokens.RPAREN);
		Statement s = statement();

		exitingDEBUG("whileStatement");
		return new WhileStatement(condition, s);
	}

	private ReadStatement readStatement() {
		enteringDEBUG("readStatement");
		ArrayList<Identifier> ids = new ArrayList<Identifier>();

		accept(TCtoken.Tokens.READ);
		accept(TCtoken.Tokens.LPAREN);
		ids.add(identifier());
		while (buff.getTokenType().equals(TCtoken.Tokens.COMMA)) {
			accept(TCtoken.Tokens.COMMA);
			ids.add(identifier());
		}
		accept(TCtoken.Tokens.RPAREN);
		accept(TCtoken.Tokens.SEMICOLON);

		exitingDEBUG("readStatement");
		return new ReadStatement(ids);
	}

	private WriteStatement writeStatement() {
		enteringDEBUG("writeStatement");
		accept(TCtoken.Tokens.WRITE);
		accept(TCtoken.Tokens.LPAREN);
		List<Expression> ap = actualParameters(new ArrayList<Expression>());
		accept(TCtoken.Tokens.RPAREN);
		accept(TCtoken.Tokens.SEMICOLON);
		exitingDEBUG("writeStatement");
		return new WriteStatement(ap);
	}

	private ReturnStatement returnStatement() {
		Expression expr = null;
		enteringDEBUG("returnStatement");
		accept(TCtoken.Tokens.RETURN);
		if (!buff.getTokenType().equals(TCtoken.Tokens.SEMICOLON))
			expr = expression();
		accept(TCtoken.Tokens.SEMICOLON);
		exitingDEBUG("returnStatement");
		return new ReturnStatement(expr);
	}

	private Operator operator(TCtoken.Tokens t) {
		enteringDEBUG("operator");
		exitingDEBUG("operator");
		return new Operator(acceptSave(t).getLexeme());
	}

	private ExpressionStatement expressionStatement() {
		enteringDEBUG("expressionStatement");
		Expression expr = expression();
		accept(TCtoken.Tokens.SEMICOLON);

		exitingDEBUG("expressionStatement");
		return new ExpressionStatement(expr);
	}

	private Expression expression() {
		enteringDEBUG("expression");
		Expression left = relopExpression();
		if (buff.getTokenType().equals(TCtoken.Tokens.ASSIGNOP)) {
			Operator op = operator(TCtoken.Tokens.ASSIGNOP);
			Expression right = expression();
			exitingDEBUG("expression");
			return new AssignExpression(op, left, right);
		} else {
			exitingDEBUG("expression");
			return left;
		}
	}

	private Expression relopExpression() {
		enteringDEBUG("relopExpression");
		Expression left = simpleExpression();
		if (buff.getTokenType().equals(TCtoken.Tokens.RELOP)) {
			List<Operator> op = new ArrayList<>();
			List<Expression> expr = new ArrayList<>();
			expr.add(left);
			while (buff.getTokenType().equals(TCtoken.Tokens.RELOP)) {
				op.add(operator(TCtoken.Tokens.RELOP));
				expr.add(simpleExpression());
			}
			exitingDEBUG("relopExpression");
			return new SimpleExpression(op, expr);
		} else {
			exitingDEBUG("relopExpression");
			return left;
		}
	}

	private Expression simpleExpression() {
		Expression left = term();
		enteringDEBUG("simpleExpression");
		if (buff.getTokenType().equals(TCtoken.Tokens.ADDOP)) {
			List<Operator> op = new ArrayList<>();
			List<Expression> expr = new ArrayList<>();
			expr.add(left);
			while (buff.getTokenType().equals(TCtoken.Tokens.ADDOP)) {
				op.add(operator(TCtoken.Tokens.ADDOP));
				expr.add(term());
			}
			exitingDEBUG("simpleExpression");
			return new SimpleExpression(op, expr);
		} else {
			exitingDEBUG("simpleExpression");
			return left;
		}
	}

	private Expression term() {
		enteringDEBUG("term");
		Expression left = primary();
		if (buff.getTokenType().equals(TCtoken.Tokens.MULOP)) {
			List<Operator> op = new ArrayList<>();
			List<Expression> expr = new ArrayList<>();
			expr.add(left);
			while (buff.getTokenType().equals(TCtoken.Tokens.MULOP)) {
				op.add(operator(TCtoken.Tokens.MULOP));
				expr.add(term());
			}
			exitingDEBUG("term");
			return new SimpleExpression(op, expr);
		} else {
			exitingDEBUG("term");
			return left;
		}
	}

	private Expression primary() {
		enteringDEBUG("primary");
		Token sw = buff;
		buff = scanner.getToken();

		switch ((TCtoken.Tokens) sw.getTokenType()) {
		case ID:
			Identifier id = new Identifier(sw.getLexeme());
			if (buff.getTokenType().equals(TCtoken.Tokens.LPAREN)) {
				List<Expression> asp = functionCall();
				exitingDEBUG("primary");
				return new FunctionCall(id, asp);
			} else {
				exitingDEBUG("primary");
				return new Identifier(sw.getLexeme());
			}
		case NUMBER:
			exitingDEBUG("primary");
			return new Number(sw.getLexeme());
		case STRING:
			exitingDEBUG("primary");
			return new StringLiteral(sw.getLexeme());
		case CHAR:
			exitingDEBUG("primary");
			return new CharLiteral(sw.getLexeme());
		case LPAREN:
			Expression expr = expression();
			accept(TCtoken.Tokens.RPAREN);
			exitingDEBUG("primary");
			return expr;
		case NOT:
			exitingDEBUG("primary");
			return new Not(expression());
		case ADDOP:
			Expression p = primary();
			if (sw.getLexeme().equals("-")) {
				exitingDEBUG("primary");
				return new Minus(expression());
			}
			exitingDEBUG("primary");
			return p;
		default:
			TCoutput.reportSYNTAX_ERROR(scanner, "unrecognized primary");
			return null;
		}
	}

	private List<Expression> functionCall() {
		enteringDEBUG("functionCall");
		accept(TCtoken.Tokens.LPAREN);
		List<Expression> ap = actualParameters(new ArrayList<Expression>());
		accept(TCtoken.Tokens.RPAREN);
		exitingDEBUG("functionCall");
		return ap;
	}

	private List<Expression> actualParameters(List<Expression> params) {
		enteringDEBUG("actualParameters");
		params.add(expression());

		if (buff.getTokenType().equals(TCtoken.Tokens.COMMA)) {
			accept(TCtoken.Tokens.COMMA);
			actualParameters(params);
		}

		exitingDEBUG("actualParameters");
		return params;
	}
}