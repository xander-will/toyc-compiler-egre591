package compilers;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public interface TargetCode {

    public void add(LineOfCode l);

    public List<LineOfCode> getCode();
    public void writeCode(String fileName) throws IOException;

    public String toString();
}
