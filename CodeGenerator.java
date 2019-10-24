package compilers;

public interface CodeGenerator {
    public TargetCode generateCode(AbstractSyntax ast);
}