package com.jetbrains;

/**
 * Variables and methods used by any Ejet project.
 */
public class Shape {
    /**
     * Mandatory starting code for Ejet.
     */
    public static final String INITIALIZE = "PSOOUTPUT X CONTROL 0 1\n" +
            "PSOCONTROL X ON\n" +
            "DWELL 0.01\n";
    /**
     * Mandatory finishing code for Ejet
     */
    public static final String END = "PSOCONTROL X OFF";


    /**
     * Ejet tip size you are going to print with
     */
    private double tip;
    public double getTip() {
        return tip;
    }
    public void setTip(double tip) {
        this.tip = tip;
    }

    /**
     * Determines how close one line is printed to the next.
     * Increasing tipScalar increases the distance between lines.
     * A tipScalar of 1 means that only the tip size controls how the lines are printed.
     * A tip scalar of less than one is more likely to cause overlap.
     * A scalar of greater than one is more likely to leave empty spaces.
     */
    private double tipScalar;
    public double getTipScalar() {
        return tipScalar;
    }
    public void setTipScalar(double tipScalar) {
        this.tipScalar = tipScalar;
    }


    /**
     * Speed at which you print horizontally and vertically.
     */
    private double speed;
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Speed at which you raise and lower the tip.
     */
    private double zSpeed;
    public double getzSpeed() {
        return zSpeed;
    }
    public void setzSpeed(double zSpeed) {
        this.zSpeed = zSpeed;
    }

    /**
     * The distance away from printing distance at which ink will not flow.
     */
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
    public String moveUpLeft(final double distanceHo, final double distanceVert) {
        return "G01 X-" + String.format("%.6f", distanceVert)
                + " Y-" + String.format("%.6f", distanceHo)
                + " F" + String.format("%.6f", getSpeed()) + "\n";
    }
    public String moveUpRight(final double distanceHo, final double distanceVert) {
        return "G01 X-" + String.format("%.6f", distanceVert)
                + " Y" + String.format("%.6f", distanceHo)
                + " F" + String.format("%.6f", getSpeed()) + "\n";
    }
    public String moveDownLeft(final double distanceHo, final double distanceVert) {
        return "G01 X" + String.format("%.6f", distanceVert)
                + " Y-" + String.format("%.6f", distanceHo)
                + " F" + String.format("%.6f", getSpeed()) + "\n";
    }
    public String moveDownRight(final double distanceHo, final double distanceVert) {
        return "G01 X" + String.format("%.6f", distanceVert)
                + " Y" + String.format("%.6f", distanceHo)
                + " F" + String.format("%.6f", getSpeed()) + "\n";
    }
}
