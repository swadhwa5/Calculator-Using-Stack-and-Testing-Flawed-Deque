package hw4;

import java.util.Scanner;
import java.util.Stack;


/**
 * A program for an RPN calculator that uses a stack.
 */
public final class Calc {
  /**
   * The main function.
   *
   * @param args Not used.
   */
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Stack<Integer> numbers = new Stack<>();
    String curr;
    while (input.hasNext()) {
      curr = input.next();
      if (("!".equals(curr))) {
        break;
      } else {
        try {
          int i = Integer.parseInt(curr);
          numbers.push(i);
        } catch (NumberFormatException e) {
          doCalc(curr, numbers);
        }
      }
    }
  }


  private static void doCalc(String curr, Stack<Integer> numbers) {
    switch (curr) {
      case "+":
      case "-":
      case "*":
      case "/":
      case "%":
        op(curr, numbers);
        break;
      case ".":
        top(numbers);
        break;
      case "?":
        System.out.println(numbers.toString());
        break;
      default:
        System.err.println("ERROR: bad token");
        break;
    }
  }


  private static boolean hasEnoughArguments(Stack<Integer> numbers) {
    if (numbers.empty()) {
      System.err.println("ERROR: Not enough arguments");
      return false;
    }
    int i = numbers.pop();
    if (numbers.empty()) {
      System.err.println("ERROR: Not enough arguments");
      numbers.push(i);
      return false;
    }
    numbers.push(i);
    return true;
  }

  private static void doOp(Stack<Integer> numbers,
                           int op1,
                           int op2,
                           String op) {
    switch (op) {
      case "+": numbers.push(op1 + op2);
        break;
      case "-": numbers.push(op1 - op2);
        break;
      case "*": numbers.push(op1 * op2);
        break;
      case "/": division(numbers, op1, op2);
        break;
      case "%": remainder(numbers, op1, op2);
        break;
      default: break;
    }
  }

  private static void op(String op, Stack<Integer> numbers) {
    Integer op1;
    Integer op2;
    if (hasEnoughArguments(numbers)) {
      op2 = numbers.pop();
      op1 = numbers.pop();
      doOp(numbers, op1, op2, op);
    }
  }

  private static void division(Stack<Integer> numbers, int op1, int op2) {
    if (op2 == 0) {
      System.err.println("ERROR: Division by Zero");
      numbers.push(op1);
      numbers.push(op2);
    } else {
      numbers.push(op1 / op2);
    }
  }

  private static void remainder(Stack<Integer> numbers, int op1, int op2) {
    if (op2 == 0) {
      System.err.println("ERROR: Division by Zero");
      numbers.push(op1);
      numbers.push(op2);
    } else {
      numbers.push(op1 % op2);
    }
  }

  private static void top(Stack<Integer> numbers) {
    if (numbers.empty()) {
      System.err.println("ERROR: Empty Stack");
    } else {
      System.out.println(numbers.peek());
    }
  }
}
