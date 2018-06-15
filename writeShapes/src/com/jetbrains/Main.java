package com.jetbrains;

public class Main {
    public static void main(String[] args) {

        Rectangle rect = new Rectangle(0.2, 0.4, 0.005, 2, 0.07);
        System.out.println(rect.fillOutToIn());
    }
}
