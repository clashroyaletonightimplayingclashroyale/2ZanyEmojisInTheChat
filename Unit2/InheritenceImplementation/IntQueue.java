package InheritenceImplementation;
import java.util.List;

public class IntQueue {
    private List<Integer> list;

    public IntQueue(List<Integer> list) {
        this.list = list;
    }

    public void add(int value) {
        list.add(value);
    }

    public int remove() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return list.remove(0);
    }

    public int peek() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}