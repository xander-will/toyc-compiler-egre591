package abstractSyntax;

import java.util.ArrayList;
import java.util.List;

import abstractSyntax.Statement;
import abstractSyntax.Identifier;

public class ReadStatement implements Statement {

	private List<Identifier> ids;
	
	public ReadStatement(ArrayList<Identifier> ids) {
		this.ids = ids;
	}

	public String toString() {
		String s = "read(";
		for (Identifier id : ids)
			s += id.toString() + ",";
		s += ")\n";

		return s;
	}

}
