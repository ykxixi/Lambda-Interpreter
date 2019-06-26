package cn.seecoder;

import java.util.ArrayList;

public class Parser {
    Lexer lexer;

    public Parser(Lexer l) {
        lexer = l;
    }

    public AST parse() {
        AST res = term(new ArrayList<>());
        lexer.next(TokenType.EOF);
        return res;
//        AST ast = term(new ArrayList<>());
//        System.out.println(lexer.match(TokenType.EOF));
//        return ast;
    }



    // term ::= LAMBDA LCID DOT term
    //        | application
    /**
    *@param ctx 上下文
     */
    private AST term(ArrayList<String> ctx) {
        // write your code here
        if (lexer.skip(TokenType.LAMBDA)) {
            String id = lexer.token(TokenType.LCID);
            lexer.match(TokenType.DOT);
            ctx.add(0,id);
            AST term = term(ctx);
            //add(String e)方法返回boolean
            ctx.remove(id);
            return new Abstraction( new Identifier(id,String.valueOf(ctx.indexOf(id))) , term);
        } else {
            return application(ctx);
        }
    }

    // application ::= atom application'
    private AST application(ArrayList<String> ctx) {
        // write your code her
       AST lhs = atom(ctx);
       while (true){
           AST rhs = atom(ctx);
           if(rhs != null){
               lhs = new Application(lhs, rhs);
           } else {
               return lhs;
           }
       }
    }

    // atom ::= LPAREN term RPAREN
    //        | LCID
    private AST atom(ArrayList<String> ctx) {
        // write your code here
        if (lexer.skip(TokenType.LPAREN)) {
            AST term = term(ctx);
            lexer.match(TokenType.RPAREN);
            return term;
        }else if (lexer.next(TokenType.LCID)) {
            String id = lexer.token(TokenType.LCID);
            return new Identifier( id, String.valueOf(ctx.indexOf(id)));
        }
        return null;
    }

    public static void main(String[] args){
        Parser parser = new Parser(new Lexer("(\\m.\\n.((m (\\n.\\f.\\x.f (n f x))) m))"));
        System.out.println(parser.parse());
        // {}        {m}          \\n.((m (\n.\f.\x.f (n f x))) n)          |
        // {m}       {n,m}         ((m (\n.\f.\x.f (n f x))) n)             |
        // {n,m}                   (m (\n.\f.\x.f (n f x)))                 |
        // {n,m}                   (\n.\f.\x.f (n f x)))                    |
        // {n,m}      {n,n,m}      \f.\x.f (n f x)                          |
        // {n,n,m}    {f,n,n,m}    \x.f (n f x)                             |      \.f (n f x)
        // {f,n,n,m}  {x,f,n,n,m}   f (n f x)                               |       1 (2 1 0)
        // {x,f,n,n,m}              (n f x)                                 |        (2 1 0)

    }

}
