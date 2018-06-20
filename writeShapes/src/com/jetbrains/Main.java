package com.jetbrains;

public class Main {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(0.3, 0.3, 0.005, 0.5, 1.0, 3.0, 0.07);
        System.out.println(Shape.INITIALIZE);
        System.out.println(rect.fillInToOut());
        System.out.println(Shape.END);
    }
}
