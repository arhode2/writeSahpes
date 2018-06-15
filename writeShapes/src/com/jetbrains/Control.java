package com.jetbrains;

public class Control {

    public static final double speed = 1.0;
    public static final double zSpeed = 3.0;

    public static String moveIn(final double distance) {
       return "G01 Z" + String.format("%.6f", distance) + " F" + String.format("%.2f", zSpeed) + "\n";
    }
    public static String moveOut(final double distance) {
        return "G01 Z-" + String.format("%.6f", distance) + " F" + String.format("%.2f", zSpeed) + "\n";
    }
    public static String moveRight(final double distance) {
        return "G01 Y" + String.format("%.6f", distance) + " F" + String.format("%.2f", speed) + "\n";
    }
    public static String moveLeft(final double distance) {
        return "G01 Y-" + String.format("%.6f", distance) + " F" + String.format("%.2f", speed) + "\n";
    }
    public static String moveUp(final double distance) {
        return "G01 X-" + String.format("%.6f", distance) + " F" + String.format("%.2f", speed) + "\n";
    }
    public static String moveDown(final double distance) {
        return "G01 X" + String.format("%.6f", distance) + " F" + String.format("%.2f", speed) + "\n";
    }
}
