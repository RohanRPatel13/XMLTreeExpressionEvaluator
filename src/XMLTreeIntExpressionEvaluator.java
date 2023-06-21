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
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
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
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        int numL = 0;
        int numR = 0;
        int ans = 0;
        if (exp.isTag() && exp.numberOfChildren() > 1) {
            if (exp.child(0).hasAttribute("value")) {
                numL = Integer.parseInt(exp.child(0).attributeValue("value"));
            } else {
                numL = evaluate(exp.child(0));
            }

            if (exp.child(1).hasAttribute("value")) {
                numR = Integer.parseInt(exp.child(1).attributeValue("value"));
            } else {
                numR = evaluate(exp.child(1));
            }

            if (exp.label().equals("divide")) {
                try {
                    ans = numL / numR;
                } catch (Exception e) {
                    Reporter.fatalErrorToConsole("Divide by zero error");
                }
            } else if (exp.label().equals("times")) {
                ans = numL * numR;
            } else if (exp.label().equals("plus")) {
                ans = numL + numR;
            } else if (exp.label().equals("minus")) {
                ans = numL - numR;
            }
        } else {
            ans = Integer.parseInt(exp.attributeValue("value"));
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
            int num = evaluate(exp.child(0));
            out.println(num);
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}