package com.jetbrains;

public class Shape {
    public static final String INITIALIZE = "PSOOUTPUT X Shape 0 1\n" +
            "PSOShape X ON\n" +
            "DWELL 0.01\n";
    public static final String END = "PSOShape X OFF";

    private double tip;
    public double getTip() {
        return tip;
    }
    public void setTip(double tip) {
        this.tip = tip;
    }

    private double tipScalar;
    public double getTipScalar() {
        return tipScalar;
    }
    public void setTipScalar(double tipScalar) {
        this.tipScalar = tipScalar;
    }



    private double speed;
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private double zSpeed;
    public double getzSpeed() {
        return zSpeed;
    }
    public void setzSpeed(double zSpeed) {
        this.zSpeed = zSpeed;
    }

    private double safeZ;
    public double getSafeZ() {
        return safeZ;
    }
    public void setSafeZ(double safeZ) {
        this.safeZ = safeZ;
    }






    public String moveIn(final double distance) {
        return "G01 Z" + String.format("%.6f", distance) + " F" + String.format("%.6f", getzSpeed()) + "\n";
    }
    public String moveOut(final double distance) {
        return "G01 Z-" + String.format("%.6f", distance) + " F" + String.format("%.6f", getzSpeed()) + "\n";
    }
    public String moveRight(final double distance) {
        return "G01 Y" + String.format("%.6f", distance) + " F" + String.format("%.6f", getSpeed()) + "\n";
    }
    public String moveLeft(final double distance) {
        return "G01 Y-" + String.format("%.6f", distance) + " F" + String.format("%.6f", getSpeed()) + "\n";
    }
    public String moveUp(final double distance) {
        return "G01 X-" + String.format("%.6f", distance) + " F" + String.format("%.6f", getSpeed()) + "\n";
    }
    public String moveDown(final double distance) {
        return "G01 X" + String.format("%.6f", distance) + " F" + String.format("%.6f", getSpeed()) + "\n";
    }
}
