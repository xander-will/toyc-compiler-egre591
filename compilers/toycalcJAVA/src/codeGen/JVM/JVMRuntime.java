package codeGen.JVM;

import java.util.HashMap;

public class JVMRuntime {

	private static final String runtimeFunctions =  ".method private static toyCAnd(II)I\n" + 
		"\t.limit stack 2\n" + 
		"\t.limit locals 2\n" + 
		"\tiload_0 \n" + 
		"\tifeq TCA_Zero\n" + 
		"\tiload_1\n" + 
		"\tifeq TCA_Zero\n" + 
		"\ticonst_1\n" + 
		"\tgoto TCA_End\n" + 
        "TCA_Zero:\n" + 
        "\ticonst_0\n" +
        "TCA_End:\n" + 
        "\tireturn\n" + 
		".end method\n\n" + 
        ".method private static toyCEquals(II)I\n" +
		"\t.limit stack 2\n" +
		"\t.limit locals 2\n" +
		"\tiload_0\n" +
		"\tiload_1\n" +
		"\tif_icmpeq TCE_True\n" +
		"\ticonst_0\n" +
		"\tgoto TCE_End\n" +
        "TCE_True:\n" +
		"\ticonst_1\n" +
        "TCE_End:\n" +
		"\tireturn\n" +
        ".end method\n\n" +
        ".method private static toyCGreaterThan(II)I\n" +
		"\t.limit stack 2\n" +
		"\t.limit locals 2\n" +
		"\tiload_0\n" +
		"\tiload_1\n" +
		"\tif_icmpgt TCGT_True\n" +
		"\ticonst_0\n" +
		"\tgoto TCGT_End\n" +
        "TCGT_True:\n" +
		"\ticonst_1\n" +
        "TCGT_End:\n" +
		"\tireturn\n" +
		".end method\n\n" +
		".method private static toyCLessThan(II)I\n" +
		"\t.limit stack 2\n" +
		"\t.limit locals 2\n" +
		"\tiload_0\n" +
		"\tiload_1\n" +
		"\tif_icmplt TCLT_True\n" +
		"\ticonst_0\n" +
		"\tgoto TCLT_End\n" +
        "TCLT_True:\n" +
		"\ticonst_1\n" +
        "TCLT_End:\n" +
		"\tireturn\n" +
        ".end method\n\n" + 
		".method private static toyCNot(I)I\n" +
		"\t.limit stack 2\n" +
		"\t.limit locals 1\n" +
		"\tiload_0\n" +
		"\tifeq TCN_ZeroIn\n" +
		"\ticonst_0\n" +
		"\tgoto TCN_End\n" +
        "TCN_ZeroIn:\n" +
		"\ticonst_1\n" +
        "TCN_End:\n" +
		"\tireturn\n" +
		".end method\n\n" +
		".method private static toyCOr(II)I\n" +
		"\t.limit stack 2\n" +
        "\t.limit locals 2\n" +
		"\tiload_0\n" +
		"\tifne TCO_One\n" +
		"\tiload_1\n" +
		"\tifne TCO_One\n" +
		"\ticonst_0\n" +
		"\tgoto TCO_End\n" +
        "TCO_One:\n" +
        "\ticonst_1\n" +
        "TCO_End:\n" +
        "\tireturn\n" +
        ".end method\n\n" +
		".method private static toyCRead()I\n" +
		"\t.limit stack 4\n" +
		"\t.limit locals 4\n" +
		"\tnew java/util/Scanner\n" +
		"\tdup\n" +
		"\tgetstatic java/lang/System.in Ljava/io/InputStream;\n" +
		"\tinvokespecial java/util/Scanner.<init>(Ljava/io/InputStream;)V\n" +
		"\tinvokevirtual java/util/Scanner.nextInt()I\n" +
		"\tireturn\n" +
        ".end method\n\n" +
		".method private static toyCWrite(I)V\n" +
		"\t.limit stack 2\n" +
		"\t.limit locals 1\n" +
		"\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n" +
		"\tiload_0\n" +
		"\tinvokevirtual java/io/PrintStream/println(I)V\n" +
		"\treturn\n" +
        ".end method\n\n" +
        ".method private static toyCWrite(Ljava/lang/String;)V\n" +
		"\t.limit stack 2\n" +
		"\t.limit locals 1\n" +
		"\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n" +
		"\taload_0\n" +
		"\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n" +
		"\treturn\n" +
		".end method\n\n";
		
    public String getRuntimeFunctions() {
		return runtimeFunctions;
	}
	
}