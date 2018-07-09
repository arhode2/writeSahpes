package com.jetbrains;

import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(0.3, 0.5, 0.005, 0.7, 0.75, 3.0, 0.07);
        System.out.println(Shape.INITIALIZE);
        System.out.print(rect.moveUpLeft(0.1, 1));
        System.out.print(rect.moveUpRight(1, -.3));
        System.out.print(rect.moveDownLeft(0, 1));
        System.out.print(rect.moveDownRight(1, 1));
        System.out.println(Shape.END);
    }
}
