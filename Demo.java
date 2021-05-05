/**
 * An example showing the use of the LexicalAnalyzer.
 */
public final class Demo {    

    // We make the constructor private, because
    // there is no point in instantiating this class.
    private Demo() {
    }
    
    /**
     * A method showing how to get 
     * from a source code String
     * to a Program that can be executed.
     */
    public static void compileDemoExpression() {
        //final String sourceCode = "-2+(4+x)/y-z";
        final String sourceCode = "12-2";

        final Parser parser = new DemoParser();
        final Node root = parser.parse(sourceCode);

        System.out.println("Unparsed expression:");
        System.out.println(root.toString());

        final Program program = new Program();
        root.compile(program);

        System.out.println("Resulting program:");
        System.out.println(program);

        final VariableTable variables = new VariableTable();
        variables.set("x", 6);
        variables.set("y", 2);
        variables.set("z", 1);

        final int resultOfExecution = program.execute(variables);
        System.out.println("Result of executing the program:");
        System.out.println(resultOfExecution);
    }

    /**
     * A method demonstrating how to create a LexicalAnalyzer,
     * and how to use it to break a text into tokens.
     */
    public static void lexicallyAnalyzeDemoSourceCode() {
        final String sourceCode = "(((height+2)-width)/(4*depth))";
        System.out.println("Source code:\n" + sourceCode);
        final LexicalAnalyzer lexer = new LexicalAnalyzer(sourceCode);

        // fetch first token
        lexer.fetchNextToken();

        // as long as current token is NOT the special END_OF_FILE token, continue
        while (lexer.getCurrentToken().getType() != TokenType.END_OF_FILE) {
            // retrieve the lexer's current token
            final Token token = lexer.getCurrentToken();
            // do something with the current token
            // unlike this demo code here, a Parser won't just print the token
            System.out.println(token.getText());
            // fetch next token (there will be a next token
            lexer.fetchNextToken();
        }
    }

}
