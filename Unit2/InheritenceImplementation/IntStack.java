package InheritenceImplementation;
import java.util.List;

public class IntStack {
    private List<Integer> list;

    public IntStack(List<Integer> list) {
        this.list = list;
    }

    public void push(int value) {
        list.add(value);
    }

    public int pop() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return list.remove(list.size() - 1);
    }

    public int peek() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return list.get(list.size() - 1);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}