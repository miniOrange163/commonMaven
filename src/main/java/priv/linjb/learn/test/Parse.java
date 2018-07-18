package priv.linjb.learn.test;

import java.io.IOException;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/6/14
 *  
 * @name: 简单的语法制导翻译器，中缀表达式翻译为后缀表达式
 *
 * @Description: 
 */
public class Parse {

    static int lookahead;

    public Parse() throws IOException {
        lookahead = System.in.read();
    }

    void expr() throws IOException {
        term();
        while (true) {
            if (lookahead == '+') {
                match('+');term();System.out.write('+');
            } else if (lookahead == '-') {
                match('-');term();System.out.write('-');
            }
            else return;
        }
    }

    void term() throws IOException {
        if (Character.isDigit((char) lookahead)) {
            System.out.write((char)lookahead);
            match(lookahead);
        }
        else{
            throw new Error();
        }
    }

    void match(int t) throws IOException {
        if(lookahead == t) lookahead = System.in.read();
        else throw new Error("syntax error");
    }

    public static void main(String[] args) throws IOException {
        // 例：9-5+2
        Parse parse = new Parse();
        parse.expr();System.out.write('\n');
    }

}
