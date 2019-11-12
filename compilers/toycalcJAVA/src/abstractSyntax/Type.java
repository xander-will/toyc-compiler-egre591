package abstractSyntax;

public class Type {

	private String type;
	
	public Type(String ty) {
		this.type = ty;
	}

	public String toString() {
		return "type(" + type + ")\n";
	}
}
