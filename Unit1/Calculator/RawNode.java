package Calculator;

public class RawNode {

    // fields
    private String _rawContent;
    protected RawNode _prev;
    protected RawNode _next;

    // constructor

    public RawNode(String rawContent) {
        _rawContent = rawContent;
    }

    public RawNode(double har) {
        _rawContent = Double.toString(har);
    }

    // getter methods

    public String getRawContent() {
        return _rawContent;
    }

    public RawNode getNext() {
        return _next;
    }

    public RawNode getPrev() {
        return _prev;
    }

    // setter methods

    public void addRawContent(String s) {
        this._rawContent += s;
    }

    public void setPrev(RawNode other) {
        this._prev = other;
    }

    public void setNext(RawNode other) {
        this._next = other;
    }

    // overwrites currents next;
    public RawNode addNext(RawNode other) {
        RawNode prevNext = _next;
        _next = other;
        other._prev = this;
        return prevNext;
    }

    // insert the next, no overwriting.
    public void insertNext(RawNode other) {
        other._prev = this;
        other._next = _next;
        if (_next != null) {
            this._next._prev = other;
        }
        _next = other;
    }

    public void addTail(RawNode other) {
        RawNode current = this;
        while (current._next != null) {
            current = current._next;
        }
        current.addNext(other);
    }

    public String toString() {
        return _rawContent;
    }

    public String toString(String delimeter) {
        if (_next == null) {
            return _rawContent;
        } else {
            return _rawContent + delimeter + _next.toString(delimeter);
        }
    }

}
