package com.example.mathsolver.Mathsolver.validator;

public class Token {
    public static final int EPSILON = 0;
    public static final int PLUSMINUS = 4;
    public static final int MULTDIV = 5;
    public static final int RAISED = 8;
    public static final int FUNCTION = 1;
    public static final int OPEN_BRACKET = 2;
    public static final int CLOSE_BRACKET = 3;
    public static final int NUMBER = 6;
    public static final int VARIABLE = 7;
    public static final int EQUAL = 9;

    public final int token;
    public final String sequence;
    public final int position;

    public Token(int token, String sequence, int position)
    {
        super();
        this.token = token;
        this.sequence = sequence;
        this.position = position;

    }

}