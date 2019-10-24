// D. Resler 4/2010

package symTable;

import compilers.Symbol;
import java.util.EnumMap;

public class TCsymbol implements Symbol{
    private String id;
    public static enum Attributes { type,  // variable or label 
                                    offset // for code generation
                                  };

    EnumMap<Attributes,Object> attribs;
    private static int nextOffset=1; // start at 1 instead of 0

    public TCsymbol(String name) { 
        id = name; 
        attribs = null; // is set by TCsymTable add routine
    }

    public String getId() { return id; }

    public EnumMap<Attributes,Object> getAttributes() { return attribs; }
    public void setAttribute(Attributes key, Object value) { attribs.put(key,value); }

    public static int getNextOffset() { return nextOffset++; }

    public String toString(){ 
        return "("+id+","+attribs.toString()+")";
    }

}
