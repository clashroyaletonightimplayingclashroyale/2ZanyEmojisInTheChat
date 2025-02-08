package InheritenceImplementation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TestProgram {
    private static final int N = 100000;

    public static void main(String[] args) {
        testStack(new ArrayList<>(), "ArrayList");
        testStack(new LinkedList<>(), "LinkedList");
        testQueue(new ArrayList<>(), "ArrayList");
        testQueue(new LinkedList<>(), "LinkedList");
    }

    private static void testStack(List<Integer> list, String type) {
        IntStack stack = new IntStack(list);
        Random random = new Random();

        long startTime = System.nanoTime();
        for (int i = 0; i < N; i++) {
            stack.push(random.nextInt());
        }
        for (int i = 0; i < N; i++) {
            stack.pop();
        }
        long endTime = System.nanoTime();

        System.out.println("Stack using " + type + " took " + (endTime - startTime) + " ns");
    }

    private static void testQueue(List<Integer> list, String type) {
        IntQueue queue = new IntQueue(list);
        Random random = new Random();

        long startTime = System.nanoTime();
        for (int i = 0; i < N; i++) {
            queue.add(random.nextInt());
        }
        for (int i = 0; i < N; i++) {
            queue.remove();
        }
        long endTime = System.nanoTime();

        System.out.println("Queue using " + type + " took " + (endTime - startTime) + " ns");
    }
}