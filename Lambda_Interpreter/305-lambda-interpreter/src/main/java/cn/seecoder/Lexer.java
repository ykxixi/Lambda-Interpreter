package cn.seecoder;
//GIT测试
//GIT测试2

public class Lexer{

    //更改过，封装
    private String source;
    private int index;
    private TokenType token;
    private String tokenValue;

    public Lexer(String s){
        index = 0;
        source = s;
        nextToken();
    }
    //get next token
    private void nextToken(){
        //write your code here

        char c=nextChar();

        switch (c){
            case '\\':
                this.token=TokenType.LAMBDA;
                break;
            case '(':
                this.token=TokenType.LPAREN;
                break;
            case ')':
                this.token=TokenType.RPAREN;
                break;
            case '.':
                this.token=TokenType.DOT;
                break;
            case '\0':
                this.token=TokenType.EOF;
                break;
            default:
//                System.out.println("go lcid"+index);
                if(String.valueOf(c).matches("[a-z]")){
                    StringBuilder str = new StringBuilder();
                    str.append(c);
                    while(index<=source.length()-1 && String.valueOf(source.charAt(index)).matches("[a-zA-Z]")){
                        str.append(source.charAt(index++));
                    }
                    this.token=TokenType.LCID;
                    this.tokenValue=str.toString();
                } else{
                    throw new RuntimeException("invalid lcid");
                }
//                System.out.println("out lcid"+index);
        }
        System.out.println(token);
    }

    // get next char
    private char nextChar(){
        char c;
        if(index>=source.length()) {
            return '\0';
        }
        while (String.valueOf(c = source.charAt(index++)).matches("\\s")){
        }
        return c;
    }


    //check token == t
    public boolean next(TokenType t){
        //write your code here
        return token==t;
    }

    //assert matching the token type, and move next token
    public void match(TokenType t){
        //write your code here
        if(next(t)){
            nextToken();
        } else {
            throw new RuntimeException("Parser error");
        }
    }

    //skip token  and move next token
    public boolean skip(TokenType t){
        //write your code here
        if(next(t)){
            nextToken();
            return true;
        } else{
            return false;
        }
    }
    public String token(TokenType type){
        if(type==null){
            return tokenValue;
        }
        String value = tokenValue;
        match(type);
        return value;
    }
    public static void main(String[] args){
        Lexer lexer = new Lexer("(");

//        TokenType tt;
//        while(!(tt = lexer.nextToken()).equals(TokenType.EOF));
        System.out.println(TokenType.LAMBDA==TokenType.LAMBDA);
        if(lexer.next(TokenType.LPAREN))
            System.out.println("yes1");
        if(lexer.skip(TokenType.LPAREN))
            System.out.println("yes");
    }

}
