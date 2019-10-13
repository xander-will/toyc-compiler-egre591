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
            FunctionDefinition fd = functiondefinition();
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

        buff = scanner.getToken().
        return new Type(ty);
    }

    private FunctionDefinition functiondefinition() {
        FunctionHeader fh = functionheader();
        FunctionBody fb = functionbody();

        return new FunctionDefinition(fh, fb);
    }

    private FunctionHeader functionheader() {
        accept(TCtokens.Tokens.LPAREN);
        FormalParamList fpl = null;
        if (isTypeToken(buff)) {
            fpl = formalparamlist();
        }
        accept(TCtokens.Tokens.RPAREN);

        return new FunctionHeader(fpl);
    }

}