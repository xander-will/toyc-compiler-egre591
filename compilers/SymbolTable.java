package compilers;

import compilers.AttributesInterface;

public interface SymbolTable {
    public void add(String name, String data_type, String sem_type);

    public AttributesInterface get(String id);
}
