package com.jetbrains;

import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(0.2, 0.3, 0.005, 0.7, 0.9, 3.0, 0.07);
        Shape.toFile(Shape.INITIALIZE + rect.fillInToOut() + Shape.END, rect.toString() + ".txt");
    }
}
