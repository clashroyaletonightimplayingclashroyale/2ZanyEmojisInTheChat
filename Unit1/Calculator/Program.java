package Calculator;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        System.out.print("Expression?> ");
        String expression = console.nextLine();
        NumCalc calc = new NumCalc();

        while (!expression.equalsIgnoreCase("quit") && !expression.equalsIgnoreCase("exit")) {

            try {
                String result = calc.evaluate(expression);
                System.out.println(result);
                String trace = calc.toString();
                System.out.println(trace);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
            System.out.print("Expression?> ");
            expression = console.nextLine();
        }

    }
}
