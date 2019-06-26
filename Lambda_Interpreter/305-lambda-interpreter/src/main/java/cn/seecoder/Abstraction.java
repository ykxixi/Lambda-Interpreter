package cn.seecoder;

public class Abstraction extends AST {
    private Identifier param;//变量
    private AST body;//表达式

    Abstraction(Identifier p, AST b){
        param = p;
        body = b;
    }

    public AST getBody() { return body; }

    public void setBody(AST body) { this.body = body; }

    public Identifier getParam() { return param; }

    public void setParam(Identifier param) { this.param = param; }

    public String toString(){
        return "\\"+"."+body.toString();
    }

}
