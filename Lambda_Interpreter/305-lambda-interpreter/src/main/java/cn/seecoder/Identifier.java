package cn.seecoder;

public class Identifier extends AST {

    private String name; //名字
    private String value;//De Bruijn index值

    public Identifier(String n,String v){
        name = n;
        value = v;
    }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    public int getDBindex() {
        return Integer.parseInt(value);
    }

    public String toString(){
        return value;
    }
}
