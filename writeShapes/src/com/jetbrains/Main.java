package com.jetbrains;

public class Main {
    public static void main(String[] args) {

        Rectangle rect = new Rectangle(0.3, 0.3, 0.005, 2, 0.07);
        System.out.println(String.format("%.2f", Control.zSpeed));
        System.out.println(String.format("%.2f", Control.speed));
        System.out.println(rect.fillOutToIn());
    }
}
