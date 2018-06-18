package com.jetbrains;

public class Rectangle extends Shape {

    /**
     * Total Constructor for the class.
     * @param newBase horizontal distance
     * @param newHeight vertical distance
     * @param newTip tip size
     * @param newTipScalar scalar for getting nicer lines
     * @param newSafeZ safe z distance for when you are not printing
     */
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
     * This function, and all of my fill functions will begin in the bottom left corner of the shape.
     */
    public String fillOutToIn() {
        String gCode = "G01 " + "Z" + String.format("%.6f", getSafeZ()) + " F" + String.format("%.6f", Control.zSpeed) + "\n";
        double nBase = getBase() - getTip();
        double nHeight = getHeight() - getTip();
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
        gCode = cleanSpeeds(gCode);
        return gCode;
    }
    public String fillInToOut() {
        String gCode = "";
        gCode = gCode.concat(center());
        gCode = gCode.concat(Control.moveIn(getSafeZ()));
        gCode = gCode.concat(Control.moveOut(getSafeZ()));
        gCode = gCode.concat(Control.moveLeft(getTip()));
        gCode = gCode.concat(Control.moveDown(getTip()));
        gCode = gCode.concat(Control.moveIn(getSafeZ()));
        double currentDistHo = getTip() * (getBase() / getHeight());
        double currentDistVert = getTip() * (getHeight() / getBase());

        return gCode;
    }
    public String center() {
        String centerCode = "";
        centerCode = centerCode.concat(Control.moveRight(getBase() / 2));
        centerCode = centerCode.concat(Control.moveUp(getHeight() / 2));
        return centerCode;
    }
    public String cleanSpeeds(final String inputgCode) {
        String[] gCodeArray = inputgCode.split("\n");
        boolean lastMoveZ = true;
        for (int i = 1; i < gCodeArray.length; i++) {
            if (!lastMoveZ) {
                gCodeArray[i] = gCodeArray[i].replace("F" + String.format("%.6f", Control.speed), "");
            } else {
                gCodeArray[i] = gCodeArray[i].replace("F" + String.format("%.6f", Control.zSpeed), "");
            }
            if (gCodeArray[i].contains("Z")) {
                lastMoveZ = true;
            } else {
                lastMoveZ = false;
            }
        }
        String newgCode = "";
        for (int i = 0; i < gCodeArray.length; i++) {
            newgCode = newgCode.concat(gCodeArray[i] + "\n");
        }
        return newgCode;
    }
    public String drawBox(final double ho, final double vert) {

    }
}
