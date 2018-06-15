package com.jetbrains;

public class Rectangle extends Shape {

    Rectangle(final double newBase, final double newHeight, final double newTip, final double newTipScalar,
              final double newSafeZ) {
        this.setBase(newBase);
        this.setHeight(newHeight);
        this.setTip(newTip);
        this.setTipScalar(newTipScalar);
        this.setSafeZ(newSafeZ);
    }

    private double base;
    public double getBase() {
        return base;
    }
    public void setBase(double base) {
        this.base = base;
    }

    private double height;
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    /**
     * A function that prints the gcode for a rectangle of the given parameters.
     * Units must be in mm for Ejet.
     * Draws outline then draws smaller outlines until it reaches the center.
     */
    public String fillOutToIn() {
        String gCode = "";
        double nBase = getBase() - getTip();
        double nHeight = getHeight() - getTip();
        System.out.println("Length is: " + getBase() + "mm");
        System.out.println("Width is: " + getHeight() + "mm");
        System.out.println("Tip is: " + getTip() + "mm");
        System.out.println("tipScalar is: " + getTipScalar());
        System.out.println("safe Z distance to move is: " + getSafeZ());
        while (nBase > 0 && nHeight > 0) {
            gCode = gCode.concat(Control.moveRight(nBase));
            gCode = gCode.concat(Control.moveUp(nHeight));
            gCode = gCode.concat(Control.moveLeft(nBase));
            gCode = gCode.concat(Control.moveDown(nHeight));
            gCode = gCode.concat(Control.moveOut(getSafeZ()));
            gCode = gCode.concat(Control.moveRight(getTip() * getTipScalar()));
            gCode = gCode.concat(Control.moveUp(getTip() * getTipScalar()));
            gCode = gCode.concat(Control.moveIn(getSafeZ()));
            nBase -= getTip() * getTipScalar();
            nHeight -= getTip() * getTipScalar();
        }
        gCode = gCode.concat(Control.moveOut(getSafeZ()));
        return gCode;
    }
    public static void rectInToOut(final double base, final double height, final double tip,
                                   final double tipScalar, final double safeZ) {

    }
    public static void rectCenter(final double base, final double height) {
    }
}
