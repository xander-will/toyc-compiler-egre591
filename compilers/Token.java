package compilers;

public interface Token {
    public Enum getTokenType();
    public String getLexeme();

    public boolean equals(Object t);
    public String toString();
}
