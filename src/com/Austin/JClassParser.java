package com.Austin;
import java.io.IOException;
import java.util.*;

/**
 *
 *The tokens also include: C D I S P V 0 1 2 3 J K M N ( ) + * < = > !
 *
 *  Nonterminals are shown as lowercase words.
 *  The following characters are NOT tokens (they are EBNF metasymbols): | { } [ ]
 *  Note that parentheses are TOKENS, not EBNF metasymbols in this particular grammar.
 *
 *  1. Draw Syntax Diagrams for the above grammar.
 *  2. Show that the grammar satisfies the two requirements for predictive parsing.
 *      (it does, you just need to prove it).
 *  3. Implement a recursive-descent recognizer:
 *      - Prompt the user for an input stream.
 *      - The user enters an input stream of legal tokens, followed by a $.
 *      - You can assume:
 *      the user enters no whitespace,
 *      the user only enters legal tokens listed above,
 *      the user enters one and only one $, at the end.
 *      - The start symbol is <javaclass> (as defined above)
 *      - Your program should output "legal" or "errors found" (not both!).
 *      You can report additional information as well, if you want.
 *      For example, knowing where your program finds an error could
 *      be helpful for me to assign partial credit if it's wrong.
 *      - Assume the input stream is the TOKEN stream. Assume that any
 *      whitespace has already been stripped out by the lexical scanner.
 *      (i.e., each token is one character - lexical scanning has been completed)
 *      - Since the incoming token stream is terminated with a $,
 *      you will need to add the $ to the grammar and incorporate it
 *      in your answers to questions #1 and #2 above, where appropriate.
 *      - Use Java, C, or C++, or ask your instructor if you wish
 *      to use another language.
 *      - Limit your source code to ONE file.
 *      - Make sure your program works on ATHENA before submitting it.
 *      - INCLUDE INSTRUCTIONS FOR COMPILING AND RUNNING YOUR PROGRAM ON ATHENA
 *      IN A COMMENT BLOCK AT THE TOP OF YOUR PROGRAM. Also, explain any
 *      input formatting that your program requires of the user entry.
 *  4. Collect the following submission materials into ONE folder:
 *      - your source code file
 *      - syntax diagrams (may be handwritten/scanned, or computer drawn)
 *      - proof of predictive parsing (may be handwritten/scanned, or computer drawn)
 *  5. ZIP the one folder into a single .zip file.
 *
 *
 *
 *
 *
 *
 */

/**
//Java Class <jClass > ::=  <className>  B  < varList> {<method>}   E
 //calculate the first(<method>) to get the condition of the while loop....
 //first(<method>) = {'P'}



//Class Name <className>  ::= C|D



// Variable List<varList>::=  <varDef>{, <varDef>};
 //first(, <varDef>) = {','}
 // at the end check that the next token is in follow of varList = {;, )}



// Variable Declaration<varDef>::=<type> <varName>

 //Type <type> ::= I | S

// Variable Names<varName>::= V|Z

// Class Method<method> ::= P <type> <methodName> (<varDef>{,<varDef>}) B <stmnt> <returnstmnt>E
 //first(,varDef) = {','}


 //Method Name<methodName>::=  M|N

// Statement<stmnt>::= <ifstmnt>|<assignstmnt>|<whilestmnt>

// If statement<ifstmnt>::= F <cond>T B {<stmnt>} E [L B { <stmnt>} E]
 //first(stmnt) = {F, Y, Z, J, K, W}

// Assignment Statement<assignstmnt>::= <varName>= <digit>;

// While Statement<whilestmnt>::= W <cond>T B <stmnt>{<stmnt>} E

// Condition<cond>::= (<varName>== <digit>)

 // Digit<digit> ::= 0|1|2|3|4|5|6|7|8|9

// Return Statement<returnstmnt>::= R <varName>;//
*/
public class JClassParser {
    static String inputString;
    int index = 0;
    int errorFlag;
    //private methods
    private char token() {
        return inputString.charAt(index);
    }
    public void advancePtr() {
        if(index < inputString.length() -1)
            index++;
    }
    private void match(char T) {
        if (T == token()) {

            // put a println here!
            System.out.println(token());
            advancePtr();
        }
        else error();
    }
    private void error() {
        System.out.println("error at index: " + index + " " + token());
        errorFlag = 1;
        advancePtr();
    }
    //Java Class <jClass > ::=  <className>  B  < varList> {<method>}   E
    //calculate the first(<method>) to get the condition of the while loop....
    //first(<method>) = {'P'}
    private void jClass() {
        className();
        if(token() == 'X'){
            match('X');
            className();
        }
        match('B');
        varList();
        match(';');
        while(token() == 'P'|| token() == 'V') {
            method();
        }
        match('E');
    }
    private void className() {
        if(token() == 'C')
            match('C');
        else if (token() == 'D')
            match('D');
        else error();
    }
    private void varList() {
        varDef();
        while(token() == ',') {
            match(',');
            varDef();
        }
    }
    //<varDef> ::= <type> <varname> | <classname> <varRef>
    private void varDef() {
        if(token() == 'I' || token() == 'S'){
            type();
            varName();
        }
        else if (token() == 'C' || token() == 'D'){
            className();
            varRef();
        }
    }
    private void type() {
        if(token() == 'I')
            match('I');
        else if(token() == 'S')
            match('S');
        else error();
    }
    private void varName() {
        if(token()=='V' || token()=='S' )
            match(token());
//        if(token() == 'V')
//            match('V');
//        else if(token() == 'S')
//            match('S');
        else error();
    }
    private void letter() {
        if(token() == 'Y')
            match('Y');
        else if(token() == 'Z')
            match('Z');
        else error();
    }
    private void ch() {
        if(token() == 'Y' || token() == 'Z')
            letter();
        else if(token() == '0' ||token() == '1' ||token() == '2' || token() == '3')
            digit();
        else error();
    }

    private void digit() {
        switch(token()){
            case '0':
                match('0');
                break;
            case '1':
                match('1');
                break;
            case '2':
                match('2');
                break;
            case '3':
                match('3');
                break;
            default:
                error();
                break;
        }


    }

    private void integer() {
        digit();
        while(token() == '0' || token() == '1' || token() == '2' || token() == '3'){
            digit();
        }

    }
    private void varRef() {
        if(token() == 'J')
            match('J');
        else if(token() == 'K')
            match('K');
        else error();
    }
    private void method() {
        if(token() == 'P')
            match('P');
        else error();
        type();
        methodName();
        if(token() == '(')
            match('(');
        else error();
        varDef();
        while(token() == ',') {
            varDef();
        }
        if(token() == ')')
            match(')');
        else error();
        if(token() == 'B')
            match('B');
        else error();
        statemt();
        returnstatemt();
        if(token() == 'E')
            match('E');
        else error();
    }
    private void accessor() {
        if(token() == 'P')
            match('P');
        else if(token() == 'V')
            match('V');
        else error();
    }
    private void methodName() {
        if(token() == 'M')
            match('M');
        else if (token() == 'N')
            match('N');
        else error();
    }
    private void statemt() {
        ifStatemt();
        assignStatemt();
        whilestatemt();
    }
    //<ifstmnt>::= F <cond>T B {<stmnt>} E [L B { <stmnt>} E]
    //first(stmnt) = {F, Y, Z, J, K, W}
    private void ifStatemt() {
        //TODO
        // F
        if(token() == 'F')
            match('F');
        else error();
        // cond
        cond();
        // T
        if(token() == 'T')
            match('T');
        else error();
        // B
        if(token() == 'B')
            match('B');
        else error();
        // {<statemt>}
        while(token() == 'F' || token() == 'Y' || token() == 'Z' || token() == 'J' || token() == 'K' || token() == 'W') {
            statemt();
        }
        // E
        if(token() == 'E')
            match('E');
        else error();
        // [ L B {stmt} E ]
        // so we need first ([]) == 'L'
        if(token() == 'L') {
            match('L');
            if (token() == 'B')
                match('B');
            else error();
            // {<statemt>} so the condition is first(statemt)
            while(token() == 'F' || token() == 'Y' || token() == 'Z' || token() == 'J' || token() == 'K' || token() == 'W') {
                statemt();
            }
            if(token() == 'E')
                match('E');
        }//no else because its an optional loop...
        // possibly check follow of ifStatemt = {R, E}
        else if(token() != 'R' && token() != 'E')
            error();
    }
    private void assignStatemt() {
        varName();
        if(token() == '=')
            match('=');
        else error();
        digit();
    }
    private void mathExpr() {
        factor();
        while(token() == '+'){
            factor();
        }
    }
    private void factor() {
        oprnd();
        while(token() == '*'){
            oprnd();
        }
    }
    private void oprnd() {
        if(token() == '0' || token() == '1' || token() == '2' || token() == '3'){
            integer();
        }
        else if(token() == 'Y' || token() == 'Z')
            varName();
        else if(token() == '(') {
            match('(');
            mathExpr();
            if (token() == ')')
                match(')');
            else error();
        }
        else if (token() == 'J' || token() == 'K')
            methodCall();
        else error();

    }

    private void getVarRef() {
        if(token() == 'O') {
            match('O');
            className();
            match('(');
            match(')');
        }
        else if(token() == 'J' || token() == 'K')
            methodCall();
        else error();
    }
    // While Statement<whilestmnt>::= W <cond>T B <stmnt>{<stmnt>} E
    // first(stmnt) = {F, Y, Z, J, K, W}
    private void whilestatemt() {
        if(token() == 'W')
            match('W');
        else error();


        cond();


        if(token() == 'T')
            match('T');
        else error();


        if(token() == 'B')
            match('B');
        else error();


        statemt();
        while(token() == 'F' || token() == 'Y' || token() == 'Z' || token() == 'J' || token() == 'K' || token() == 'W') {
            statemt();
        }
        if(token() == 'E')
            match('E');
        else error();
    }



    // this here is a test subject har har har
    private void cond() {
        match('(');
        oprnd();
        operator();
        oprnd();
        match(')');
    }
    private void operator() {
        //TODO
        switch(token()){
            case '<':
                match('<');
                break;
            case '>':
                match('>');
                break;
            case '=':
                match('=');
                break;
            case '!':
                match('!');
                break;
            default:
                error();
        }
    }

    private void returnstatemt() {
        if(token() == 'R')
            match('R');
        else error();
        varName();
        match(';');
    }
    private void methodCall() {
        //TODO
        varRef();
        match('.');
        methodName();
        match('(');
        if(token() == 'I' || token() == 'S' || token() == 'C' || token() == 'D'){
            varList();
        }
        match(')');
    }


    public void start() {
        jClass();
        match('$');
        if(errorFlag == 0)
            System.out.println("legal.\n");
        else
            System.out.println("errors found.\n");
    }






    public static void main(String[] args) throws IOException {


        JClassParser parser = new JClassParser();
        Scanner kb = new Scanner(System.in);


        // prompt user for a string
        System.out.print("\nenter a string of stuff: ");
        inputString = kb.next();
        System.out.println(inputString);
        parser.start();
    }

}
