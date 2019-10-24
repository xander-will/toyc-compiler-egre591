package compilers;

public interface SymbolTable {
    public Symbol add(String id);
    public Symbol find(String id);
}
