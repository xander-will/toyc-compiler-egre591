package symTable;

import compilers.Symbol;
import compilers.SymbolTable;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.EnumMap;

public class TCsymTable implements SymbolTable{

    private List<Symbol> st;

    public TCsymTable() { st = new ArrayList<Symbol>(); }

    public Symbol add(String id){
        TCsymbol sym;
        if ((sym=(TCsymbol)find(id)) == null) {
            st.add(sym=new TCsymbol(id));
            sym.attribs = new EnumMap<TCsymbol.Attributes,Object>(TCsymbol.Attributes.class);
        }
        return sym;
    }

    public Symbol find(String id) {
        Iterator<Symbol> itr = st.iterator();
        TCsymbol sym;
        while(itr.hasNext())
            if (((sym=(TCsymbol)itr.next())).getId().equals(id))
                return sym;
        return null;
    }

    public String toString() { return st+"\n"; }

}
