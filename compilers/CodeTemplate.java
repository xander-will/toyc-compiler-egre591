package compilers;

public interface CodeTemplate {

    public String directive(String dir, String arg);
    public String init();
    public String function(String name, String args, String body);

}