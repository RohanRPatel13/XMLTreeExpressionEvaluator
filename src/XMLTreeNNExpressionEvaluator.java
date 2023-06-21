import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Put your name here
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        NaturalNumber numL = new NaturalNumber1L();
        NaturalNumber numR = new NaturalNumber1L();
        NaturalNumber ans = new NaturalNumber1L();
        if (exp.isTag() && exp.numberOfChildren() > 1) {
            if (exp.child(0).hasAttribute("value")) {
                numL = new NaturalNumber1L(
                        Integer.parseInt(exp.child(0).attributeValue("value")));
            } else {
                numL = new NaturalNumber1L(evaluate(exp.child(0)));
            }

            if (exp.child(1).hasAttribute("value")) {
                numR = new NaturalNumber1L(
                        Integer.parseInt(exp.child(1).attributeValue("value")));
            } else {
                numR = new NaturalNumber1L(evaluate(exp.child(1)));
            }

            ans.copyFrom(numL);
            if (exp.label().equals("divide")) {
                try {
                    ans.divide(numR);
                } catch (StackOverflowError e) {
                    Reporter.fatalErrorToConsole("Divide by zero error");
                }
            } else if (exp.label().equals("times")) {
                ans.multiply(numR);
            } else if (exp.label().equals("plus")) {
                ans.add(numR);
            } else if (exp.label().equals("minus")) {
                try {
                    ans.subtract(numR);
                } catch (StackOverflowError e) {
                    Reporter.fatalErrorToConsole(
                            "Natural Numbers can't be negative.");
                }
            }
        } else {
            ans = new NaturalNumber1L(
                    Integer.parseInt(exp.attributeValue("value")));
        }

        return ans;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            NaturalNumber num = new NaturalNumber1L(evaluate(exp.child(0)));
            out.println(num);
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}