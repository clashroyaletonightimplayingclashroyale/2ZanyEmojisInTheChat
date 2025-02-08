package Calculator;

public class NumNode extends RawNode {

    private double _numValue;

    public NumNode(String rawContent) {
        super(rawContent);
        _numValue = Double.parseDouble(rawContent);
    }

    public NumNode(double har) {
        super(har);
        _numValue = har;
    }

    // methods
    public double getNumValue() {
        return _numValue;
    }

}
