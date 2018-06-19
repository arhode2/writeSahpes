package com.jetbrains;

public class Main {
    public static void main(String[] args) {

        Rectangle rect = new Rectangle(0.3, 0.3, 0.005, 0.5, 0.07);
        System.out.println(rect.fillInToOut());
    }
}
