//*******************************************************************
// Author: Archer Reilly
// Date: 21/Apr/2014
// File: JavaDocDemo.java
// Des: this file illustrate the usage of the javadoc util to gen
// documentation for your class.
//
// Produced By CSRGXTU
//*******************************************************************

import java.io.*;

/**
 * <h1>Add Two Numbers!</h1>
 * The AddNum program simply implements an application that simply
 * add two numbers and prints the output to the screen.
 * <p>
 * <b>Note:</b> Giving comments to your code makes it more user
 * friendly and it is assumed as a high quality code.
 *
 * @author Archer Reilly
 * @version 0.0.1
 * @since 2014-04-21
 */
public class JavaDocDemo {
  /**
   * This is a method used to add two integers, this is the simplest
   * form of a class method, just to show the usages of various
   * javadoc tags.
   *
   * @param numA this is the first number
   * @param numB this is the second number
   * @return int this returns the sum of numA and numB
   */
  public int addNum(int numA, int numB) {
    return numA + numB;
  }
  
  /**
   * this is the main mehtod which makes use of addNum method
   *
   * @param args Unused
   * @return nothing
   * @exception IOException On input error
   * @see IOException
   * @see #addNum
   */
  public static void main(String[] args) {
    JavaDocDemo obj = new JavaDocDemo();
    int sum = obj.addNum(10, 20);
    System.out.println(sum);
  }
}
