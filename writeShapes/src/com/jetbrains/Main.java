package com.jetbrains;

import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(0.3, 0.5, 0.005, 0.7, 0.75, 3.0, 0.07);
        Shape.toFile("Hello World.", "testFile.txt");
    }
}
