package com.jetbrains;

public class Main {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(0.1, 0.1, 0.005, 2, 0.007);
        System.out.println(rect.fillInToOut());
    }
}
