import java.text.ParseException;

public class ParserImpl extends Parser {

    
    @Override
    public Expr do_parse() throws Exception {
        return parseT();
    }
     // T -> F AddOp T | F
    private Expr parseT() throws Exception {
        Expr left = parseF();
        
        if (peek(TokenType.PLUS, 0) || peek(TokenType.MINUS, 0)) {
            Token op = parseAddOp();
            Expr right = parseT();
            
            if (op.ty == TokenType.PLUS) {
                return new PlusExpr(left, right);
            } else {  // Must be MINUS
                return new MinusExpr(left, right);
            }
        }
        
        return left;  // T -> F
    }

    // F -> Lit MulOp F | Lit
    private Expr parseF() throws Exception {
        Expr left = parseLit();
        
        if (peek(TokenType.TIMES, 0) || peek(TokenType.DIV, 0)) {
            Token op = parseMulOp();
            Expr right = parseF();
            
            if (op.ty == TokenType.TIMES) {
                return new TimesExpr(left, right);
            } else {  // Must be DIV
                return new DivExpr(left, right);
            }
        }
        
        return left;  // F -> Lit
    }

    // Lit -> NUM | LPAREN T RPAREN
    private Expr parseLit() throws Exception {
        if (peek(TokenType.NUM, 0)) {
            Token num = consume(TokenType.NUM);
            return new FloatExpr(Float.parseFloat(num.lexeme));
        } else if (peek(TokenType.LPAREN, 0)) {
            consume(TokenType.LPAREN);  // consume '('
            Expr expr = parseT();
            consume(TokenType.RPAREN);  // consume ')'
            return expr;
        } else {
            throw new Exception("Expected a number or '('");
        }
    }

    // AddOp -> PLUS | MINUS
    private Token parseAddOp() throws Exception {
        if (peek(TokenType.PLUS, 0)) {
            return consume(TokenType.PLUS);
        } else if (peek(TokenType.MINUS, 0)) {
            return consume(TokenType.MINUS);
        } else {
            throw new Exception("Expected '+' or '-'");
        }
    }

    // MulOp -> TIMES | DIV
    private Token parseMulOp() throws Exception {
        if (peek(TokenType.TIMES, 0)) {
            return consume(TokenType.TIMES);
        } else if (peek(TokenType.DIV, 0)) {
            return consume(TokenType.DIV);
        } else {
            throw new Exception("Expected '*' or '/'");
        }
    }
}
