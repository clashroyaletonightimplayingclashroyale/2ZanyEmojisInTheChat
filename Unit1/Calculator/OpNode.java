package Calculator;

public class OpNode extends RawNode {

    // OpCode Enumerator
    public enum OpCode {
        UNKNOWN,
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
        MODULO,
        POWER
    }

    // fields
    private OpCode _opCode;
    private String _opCodeString;

    public OpNode(String rawContent) {
        super(rawContent);
        _opCode = OpCode.UNKNOWN;
        switch (rawContent) {
            case "+":
                _opCode = OpCode.ADDITION;
                _opCodeString = "+";
                break;
            case "-":
                _opCode = OpCode.SUBTRACTION;
                _opCodeString = "-";
                break;
            case "/":
                _opCode = OpCode.DIVISION;
                _opCodeString = "/";
                break;
            case "*":
                _opCode = OpCode.MULTIPLICATION;
                _opCodeString = "*";
                break;
            case "%":
                _opCode = OpCode.MODULO;
                _opCodeString = "%";
                break;
            case "^":
                _opCode = OpCode.POWER;
                _opCodeString = "^";
                break;
            default:
                throw new RuntimeException("Unable to Parse OpCode: " + rawContent);
        }
    }

    // getter
    public OpCode getOpCode() {
        return _opCode;
    }

    public String getOpString() {
        return _opCodeString;
    }

}
