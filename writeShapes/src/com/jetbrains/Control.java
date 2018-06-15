package com.jetbrains;

public class Control {
    public static String moveIn(final double distance) {
       return "G01 Z" + String.format("%.6f", distance) + "\n";
    }
    public static String moveOut(final double distance) {
        return "G01 Z-" + String.format("%.6f", distance) + "\n";
    }
    public static String moveRight(final double distance) {
        return "G01 Y" + String.format("%.6f", distance) + "\n";
    }
    public static String moveLeft(final double distance) {
        return "G01 Y-" + String.format("%.6f", distance) + "\n";
    }
    public static String moveUp(final double distance) {
        return "G01 X-" + String.format("%.6f", distance) + "\n";
    }
    public static String moveDown(final double distance) {
        return "G01 X" + String.format("%.6f", distance) + "\n";
    }
}
