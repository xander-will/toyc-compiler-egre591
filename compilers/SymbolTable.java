package compilers;

import compilers.AttributesInterface;

public interface SymbolTable {
    public void add(String name, String type);
    public AttributesInterface get(String id);
}
