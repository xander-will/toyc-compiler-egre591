.source Test.j
.class public Test
.super java/lang/Object

	.method public <init>()V
	   aload 0
	   invokespecial java/lang/Object/<init>()V
	   return
	.end method
	
	.method public static toyCEquals(II)I
		.limit stack 2
		.limit locals 2
		
		iload_0
		iload_1
		
		if_icmpeq TCE_True
		iconst_0
		goto TCE_End
		
	TCE_True:
		iconst_1
	
	TCE_End:
		ireturn
		
	.end method

	.method public static toyCGreaterThan(II)I
		.limit stack 2
		.limit locals 2
		
		iload_0
		iload_1
		
		if_icmpgt TCGT_True
		iconst_0
		goto TCGT_End
		
	TCGT_True:
		iconst_1
		
	TCGT_End:
		ireturn
		
	.end method
	
	.method public static toyCLessThan(II)I
		.limit stack 2
		.limit locals 2
		
		iload_0
		iload_1
		
		if_icmplt TCLT_True
		iconst_0
		goto TCLT_End
		
	TCLT_True:
		iconst_1
		
	TCLT_End:
		ireturn
		
	.end method
	
	.method public static toyCAnd(II)I
		.limit stack 2
		.limit locals 2
		
		iload_0 
		ifeq TCA_Zero
		iload_1
		ifeq TCA_Zero
		iconst_1
		goto TCA_End
		
	TCA_Zero:
		iconst_0
		
	TCA_End:
		ireturn
		
	.end method
	
	.method public static toyCOr(II)I
		.limit stack 2
		.limit locals 2
		
		; Goofy
		;
		; iload_0
		; invokestatic Test.toyCNot(I)I
		; iload_1
		; invokestatic Test.toyCNot(I)I
		; invokestatic Test.toyCAnd(II)I
		; invokestatic Test.toyCNot(I)I
		
		iload_0
		ifne TCO_One
		iload_1
		ifne TCO_One
		iconst_0
		goto TCO_End
		
	TCO_One:
		iconst_1
	
	TCO_End:
		ireturn
		
		ireturn
	.end method

	.method public static toyCNot(I)I
		.limit stack 1
		.limit locals 1
		
		iload_0
		
		ifeq TCN_ZeroIn
		iconst_0
		goto TCN_End
		
	TCN_ZeroIn:
		iconst_1
	
	TCN_End:
		ireturn
		
	.end method


	.method public static main([Ljava/lang/String;)V
       .limit stack 10   ; up to two items can be pushed

       ; push System.out onto the stack
       getstatic java/lang/System/out Ljava/io/PrintStream;

		bipush 1
		bipush 0
		invokestatic Test.toyCOr(II)I

       ; call the PrintStream.println() method.
       invokevirtual java/io/PrintStream/println(I)V

       ; done
       return
    .end method