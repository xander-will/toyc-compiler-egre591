package compilers;

import compilers.AttributesInterface;
import java.util.ArrayList;
import java.util.List;

public interface SymbolTable {
    public void add(String name, String data_type, String sem_type);

    public void add(String name, String data_type, String sem_type, int arg_num);

    public boolean containsID(String id);

    public AttributesInterface get(String id);

    public ArrayList<String> getGlobals();
}
