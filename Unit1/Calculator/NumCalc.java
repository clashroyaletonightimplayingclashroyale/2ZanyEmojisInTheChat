package Calculator;

import Calculator.OpNode.OpCode;

public class NumCalc {

    // fields
    private RawNode _head;
    private RawNode _trace;
    private String goat;

    public static final OpCode[][] OP_PRECEDENCE = {
            { OpCode.POWER },
            { OpCode.MODULO, OpCode.MULTIPLICATION, OpCode.DIVISION },
            { OpCode.ADDITION, OpCode.SUBTRACTION },
    };

    // constructors
    public NumCalc() {
        _head = null;
        _trace = null;
    }

    // methods

    /**
     * Manages evaluating an expression and returning a result
     * 
     * @param expression an expression (3.0 + 2.0 / 4.0)
     * @return the answer to expression as string
     */
    public String evaluate(String expression) {
        goat = "";
        String[] splitUp = expression.split(" ");
        this.buildExprList(splitUp);
        return evalExprList();

    }

    public void buildExprList(String[] exprStrings) {
        // operators that determines whether its going to be NumNode or OpNode
        String operators = "/*+-%^";

        // edge case for head, needs to be not null
        if (operators.contains(exprStrings[0])) {
            _head = (new OpNode(exprStrings[0]));
        } else {
            try {
                _head = (new NumNode(exprStrings[0]));
            } catch (Exception e) {
                throw new RuntimeException("##Error: Unrecognized token: " + exprStrings[0]);
            }

        }

        // build expression list through adding based on what type of node its going to
        // be
        for (int i = 1; i < exprStrings.length; i++) {
            if (operators.contains(exprStrings[i])) {
                _head.addTail(new OpNode(exprStrings[i]));
            } else {
                try {
                    _head.addTail(new NumNode(exprStrings[i]));
                } catch (Exception e) {
                    throw new RuntimeException("##Error:Unrecognized token: " + exprStrings[i]);
                }
            }
        }

        RawNode current = _head;
        // link it backwards cause its also doubly linked list;
        while (current.getNext() != null) {
            current.getNext().setPrev(current);
            current = current.getNext();
        }

        RawNode hehe = _head;
        boolean justNums = true;
        // test if it is just spaces
        while (hehe != null) {
            if (hehe instanceof OpNode) {
                justNums = false;

            }
            hehe = hehe.getNext();
        }
        if (justNums) {
            throw new RuntimeException("##Error: Expression does not contain numbers and operators");
        }

    }

    public NumNode evaluateOperation(RawNode left, RawNode right, OpCode operator, String opnodeString) {

        double har;
        double hare;
        double sigma;

        try {
            har = ((NumNode) left).getNumValue();
            hare = ((NumNode) right).getNumValue();
        } catch (Exception e) {
            throw new RuntimeException("##Error: Wrong operands for operator: " + opnodeString);
        }

        switch (operator) {
            case POWER:
                sigma = Math.pow(har, hare);
                return new NumNode(sigma);
            case MODULO:
                if (hare == 0) {
                    throw new RuntimeException("##Error: Division by zero");
                }
                sigma = har % hare;
                return new NumNode(sigma);
            case DIVISION:
                if (hare == 0) {
                    throw new RuntimeException("##Error: Division by zero");
                }
                sigma = har / hare;
                return new NumNode(sigma);
            case MULTIPLICATION:
                sigma = har * hare;
                return new NumNode(sigma);
            case SUBTRACTION:
                sigma = har - hare;
                return new NumNode(sigma);
            case ADDITION:
                sigma = har + hare;
                return new NumNode(sigma);
            default:
                throw new RuntimeException("whoops, something went wrong");
        }

    }

    public void relinkDemNodes(RawNode current, NumNode resultant) {

        // relink the NODE HAR HAR HAR HAR
        RawNode newRight = current.getNext().getNext();
        RawNode newLeft = current.getPrev().getPrev();

        // relink resultant's next to the right one, left one to the previous one

        resultant.setNext(newRight);
        resultant.setPrev(newLeft);

        // relink each of the right or left ones to my resultant

        if (newRight != null) {
            newRight.setPrev(resultant);
        }
        if (newLeft != null) {
            newLeft.setNext(resultant);
        }
    }

    public String evalExprList() {
        // for each operator in the operator precedence
        double answer = Double.parseDouble(_head.getRawContent());
        for (OpCode[] operatorPrecedence : OP_PRECEDENCE) {

            // set the current to the front
            RawNode current = _head;
            // this is for keeping track of the current place.
            RawNode yapperPointer = current;

            // as long as the current isn't null,
            while (current != null) {
                // if it is an operator, continue, otherwise move onto the next one
                if (current instanceof OpNode) {

                    // for all of the operators within that subarry of operators,
                    for (OpCode operator : operatorPrecedence) {
                        // if opnode of current matches the current operator of your opnode

                        if (((OpNode) current).getOpCode() == operator) {

                            // evaluate
                            String opNodeString = ((OpNode) current).getOpString();

                            // result = evaluating operation, then setting that to answer
                            NumNode result = evaluateOperation(current.getPrev(), current.getNext(), operator,
                                    opNodeString);

                            answer = result.getNumValue();

                            // need to relink head because head could potentially change before from the
                            // calculations.
                            yapperPointer = current;
                            while (yapperPointer.getPrev() != null) {
                                yapperPointer = yapperPointer.getPrev();
                            }
                            _head = yapperPointer;
                            addTraceFrame(_head);
                            // calculations are done, relink dem nodes
                            relinkDemNodes(current, result);

                        }
                    }
                    // move onto the next node
                    current = current.getNext();
                } else {
                    // if its not an OPNODE, move onto the next node
                    current = current.getNext();
                }
            }
        }
        goat += answer + "\n";
        return String.valueOf(answer);
    }

    public void addTraceFrame(RawNode s) {
        while (s != null) {
            goat += s.getRawContent() + " ";
            s = s.getNext();
        }
        goat += "\n";
    }

    public String toString() {
        String result = "Evaluation trace --------\n";
        result += goat;
        return result;
    }

    public void test(RawNode x) {
        System.out.println();
        RawNode har = x;
        while (har != null) {
            System.out.print(har.getRawContent());
            if (har.getNext() == null) {
                System.out.print(" -> null ");
            } else {
                System.out.print(" -> ");
            }

            har = har.getNext();
        }
    }

}
