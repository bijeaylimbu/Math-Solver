package com.example.mathsolver.Mathsolver.validator;

import java.util.LinkedList;

public class Parser {

    LinkedList<Token> tokens;
    Token lookahead;
    boolean flag=false;
    @SuppressWarnings("unchecked")
    public void parse(LinkedList<Token> tokens)
    {
        this.tokens = (LinkedList<Token>) tokens.clone();
        lookahead = this.tokens.getFirst();

        expression();

        if (lookahead.token != Token.EPSILON)
            throw new ParserException("Unexpected symbol '%s' found in position "+lookahead.position+".", lookahead);
    }


    private void nextToken()
    {
        tokens.pop();
        // at the end of input we return an epsilon token
        if (tokens.isEmpty())
            lookahead = new Token(Token.EPSILON, "", -1);
        else
            lookahead = tokens.getFirst();
    }

    private void expression()
    {
        // expression -> signed_term sum_op
        signedTerm();
        sumOp();
    }

    private void sumOp()
    {
        if (lookahead.token == Token.PLUSMINUS)
        {
            // sum_op -> PLUSMINUS term sum_op
            nextToken();
            term();
            sumOp();
        }
        else
        {
            // sum_op -> EPSILON
        }
    }

    private void signedTerm()
    {
        if (lookahead.token == Token.PLUSMINUS)
        {
            // signed_term -> PLUSMINUS term
            nextToken();
            term();
        }
        else
        {
            // signed_term -> term
            term();
        }
    }

    private void term()
    {
        // term -> factor term_op
        factor();
        termOp();
    }

    private void termOp()
    {
        if (lookahead.token == Token.MULTDIV)
        {
            // term_op -> MULTDIV factor term_op
            nextToken();
            signedFactor();
            termOp();
        }
        else
        {
            // term_op -> EPSILON
        }
    }

    private void signedFactor()
    {
        if (lookahead.token == Token.PLUSMINUS)
        {
            // signed_factor -> PLUSMINUS factor
            nextToken();
            factor();
        }
        else
        {
            // signed_factor -> factor
            factor();
        }
    }

    private void factor()
    {
        // factor -> argument factor_op
        argument();
        factorOp();
    }

    private void factorOp()
    {
        if (lookahead.token == Token.RAISED)
        {
            // factor_op -> RAISED expression
            nextToken();
            signedFactor();
        }
        else if (lookahead.token == Token.EQUAL){

            if(!flag){
                flag = true;
            }
            else{
                throw new ParserException("Unexpected symbol '%s' found in position "+lookahead.position+".", lookahead);
            }

            // factor_op -> EQUAL signedFactor
            nextToken();
            expression();

            if (lookahead.token != Token.EPSILON)
                throw new ParserException("Unexpected symbol '%s' found in position "+lookahead.position+".", lookahead);

        }
        else
        {
            // factor_op -> EPSILON
        }
    }


    private void argument()
    {
        if (lookahead.token == Token.FUNCTION)
        {
            // argument -> FUNCTION argument
            nextToken();
            argument();
        }
        else if (lookahead.token == Token.OPEN_BRACKET)
        {
            // argument -> OPEN_BRACKET sum CLOSE_BRACKET
            nextToken();
            expression();

            if (lookahead.token != Token.CLOSE_BRACKET)
                throw new ParserException("Closing brackets expected.");

            nextToken();
        }
        else
        {
            // argument -> value
            value();

        }
    }

    private void value()
    {
        if (lookahead.token == Token.NUMBER)
        {
            // argument -> NUMBER
            nextToken();
            nextvalue();

        }
        else if (lookahead.token == Token.VARIABLE)
        {
            // argument -> VARIABLE
            nextToken();
            nextvariable();
        }
        else
        {
            if (lookahead.token == Token.EPSILON)
                throw new ParserException("Unexpected end of input.");
            else
                throw new ParserException("Unexpected symbol '%s' found at position "+lookahead.position+".", lookahead);
        }
    }

    private void nextvalue()
    {
        if (lookahead.token == Token.FUNCTION)
        {

            nextToken();
            argument();

        }
        else if (lookahead.token == Token.VARIABLE)
        {
            // argument -> VARIABLE
            nextToken();

        }
        else if (lookahead.token == Token.OPEN_BRACKET)
        {

            nextToken();
            expression();

            if (lookahead.token != Token.CLOSE_BRACKET)
                throw new ParserException("Closing brackets expected. ");

            nextToken();

        }
        else
        {

        }


    }

    private void nextvariable()
    {
        if (lookahead.token == Token.FUNCTION)
        {

            nextToken();
            argument();

        }
        else if (lookahead.token == Token.OPEN_BRACKET)
        {

            nextToken();
            expression();

            if (lookahead.token != Token.CLOSE_BRACKET)
                throw new ParserException("Closing brackets expected.");

            nextToken();

        }
        else
        {

        }
    }

}
