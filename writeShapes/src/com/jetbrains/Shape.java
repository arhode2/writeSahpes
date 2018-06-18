package com.jetbrains;

public class Shape {
    public static final String INITIALIZE = "PSOOUTPUT X CONTROL 0 1\n" +
            "PSOCONTROL X ON\n" +
            "DWELL 0.01\n";
    public static final String END = "PSOCONTROL X OFF";

    private double safeZ;
    public double getSafeZ() {
        return safeZ;
    }

    public void setSafeZ(double safeZ) {
        this.safeZ = safeZ;
    }

    private double tipScalar;
    public double getTipScalar() {
        return tipScalar;
    }

    public void setTipScalar(double tipScalar) {
        this.tipScalar = tipScalar;
    }

    private double tip;
    public double getTip() {
        return tip;
    }
    public void setTip(double tip) {
        this.tip = tip;
    }

}
