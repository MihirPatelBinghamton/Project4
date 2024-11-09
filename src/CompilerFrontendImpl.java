public class CompilerFrontendImpl extends CompilerFrontend {
    public CompilerFrontendImpl() {
        super();
    }

    public CompilerFrontendImpl(boolean debug_) {
        super(debug_);
    }


    @Override
    protected void init_lexer() {
        LexerImpl lexer = new LexerImpl();

        // NUM: [0-9]*\.[0-9]+
        Automaton numAutomaton = new AutomatonImpl();
        numAutomaton.addState(0, true, false); // Initial state
        numAutomaton.addState(1, false, false); // For digits before decimal
        numAutomaton.addState(2, false, false); // Decimal point state
        numAutomaton.addState(3, false, true);  // Accepting state for digits after decimal
        for (char c = '0'; c <= '9'; c++) {
            numAutomaton.addTransition(0, c, 1); // Initial state to digits before decimal
            numAutomaton.addTransition(1, c, 1); // Loop for digits before decimal
            numAutomaton.addTransition(3, c, 3); // Loop for digits after decimal
        }
        numAutomaton.addTransition(1, '.', 2); // Transition from digits to decimal
        for (char c = '0'; c <= '9'; c++) {
            numAutomaton.addTransition(2, c, 3); // Transition from decimal to digits after
        }
        lexer.add_automaton(TokenType.NUM, numAutomaton);

        // PLUS: +
        Automaton plusAutomaton = new AutomatonImpl();
        plusAutomaton.addState(0, true, false);
        plusAutomaton.addState(1, false, true);
        plusAutomaton.addTransition(0, '+', 1);
        lexer.add_automaton(TokenType.PLUS, plusAutomaton);

        // MINUS: -
        Automaton minusAutomaton = new AutomatonImpl();
        minusAutomaton.addState(0, true, false);
        minusAutomaton.addState(1, false, true);
        minusAutomaton.addTransition(0, '-', 1);
        lexer.add_automaton(TokenType.MINUS, minusAutomaton);

        // TIMES: *
        Automaton timesAutomaton = new AutomatonImpl();
        timesAutomaton.addState(0, true, false);
        timesAutomaton.addState(1, false, true);
        timesAutomaton.addTransition(0, '*', 1);
        lexer.add_automaton(TokenType.TIMES, timesAutomaton);

        // DIV: /
        Automaton divAutomaton = new AutomatonImpl();
        divAutomaton.addState(0, true, false);
        divAutomaton.addState(1, false, true);
        divAutomaton.addTransition(0, '/', 1);
        lexer.add_automaton(TokenType.DIV, divAutomaton);

        // LPAREN: (
        Automaton lparenAutomaton = new AutomatonImpl();
        lparenAutomaton.addState(0, true, false);
        lparenAutomaton.addState(1, false, true);
        lparenAutomaton.addTransition(0, '(', 1);
        lexer.add_automaton(TokenType.LPAREN, lparenAutomaton);

        // RPAREN: )
        Automaton rparenAutomaton = new AutomatonImpl();
        rparenAutomaton.addState(0, true, false);
        rparenAutomaton.addState(1, false, true);
        rparenAutomaton.addTransition(0, ')', 1);
        lexer.add_automaton(TokenType.RPAREN, rparenAutomaton);

        // WHITE_SPACE: (' '|\n|\r|\t)*
        Automaton whitespaceAutomaton = new AutomatonImpl();
        whitespaceAutomaton.addState(0, true, true);
        whitespaceAutomaton.addState(1, false, true);
        whitespaceAutomaton.addTransition(0, ' ', 1);
        whitespaceAutomaton.addTransition(0, '\n', 1);
        whitespaceAutomaton.addTransition(0, '\r', 1);
        whitespaceAutomaton.addTransition(0, '\t', 1);
        whitespaceAutomaton.addTransition(1, ' ', 1);
        whitespaceAutomaton.addTransition(1, '\n', 1);
        whitespaceAutomaton.addTransition(1, '\r', 1);
        whitespaceAutomaton.addTransition(1, '\t', 1);
        lexer.add_automaton(TokenType.WHITE_SPACE, whitespaceAutomaton);

        // Assign lexer to the class field
        this.lex = lexer;
        }

}
