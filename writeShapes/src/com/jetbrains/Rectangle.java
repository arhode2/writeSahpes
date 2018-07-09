package com.jetbrains;

public class Rectangle extends Shape {
    /**
     * Total constructor for a rectangle
     * @param newBase horizontal dimension
     * @param newHeight vertical dimension
     * @param newTip tip diameter
     * @param newTipScalar scalar
     * @param newSpeed speed for X and Y movements
     * @param newzSpeed speed for Z movements
     * @param newSafeZ height above the printing height for non-printing movements.
     */
    Rectangle(final double newBase, final double newHeight, final double newTip, final double newTipScalar,
              final double newSpeed, final double newzSpeed, final double newSafeZ) {
        this.setBase(newBase);
        this.setHeight(newHeight);
        this.setTip(newTip);
        this.setTipScalar(newTipScalar);
        this.setSpeed(newSpeed);
        this.setzSpeed(newzSpeed);
        this.setSafeZ(newSafeZ);
    }

    /**
     * Horizontal dimmension of the rectangle.
     */
    private double base;
    public double getBase() {
        return base;
    }
    public void setBase(double base) {
        this.base = base;
    }

    /**
     * Vertical dimmension of the rectangle.
     */
    private double height;
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     *  A function that prints the gcode for a rectangle of the given parameters.
     * Units must be in mm for Ejet.
     * Draws outline then draws smaller outlines until it reaches the center.
     * This function, and all of my fill functions will begin in the bottom left corner of the shape.
     * Easy to write and understand, but doesn't print very nicely.
     * @return the gCode for the rectangle
     */
    public String fillOutToIn() {
        String gCode = "";
        double nBase = getBase() - getTip();
        double nHeight = getHeight() - getTip();
        while (nBase > 0 && nHeight > 0) {
            gCode = drawBox(gCode, nBase, nHeight);
            gCode = gCode.concat(moveRight(getTip() * getTipScalar()));
            gCode = gCode.concat(moveUp(getTip() * getTipScalar()));
            nBase -= 2 * getTip() * getTipScalar();
            nHeight -= 2 * getTip() * getTipScalar();
        }
        gCode = gCode.concat(moveOut(getSafeZ()));
        gCode = cleanSpeeds(gCode);
        return gCode;
    }

    /**
     * Prints a rectangle.
     * Starts in the bottom left, moves to the center, then prints larger concentric
     * rectangles from the inside to outside.
     * Overfills the first few inner rectangles boxes by trying to print sides smaller than
     * the tip, but it seems worth it to preserve the side ratios.
     * @return the gCode for a rectangle.
     */
    public String fillInToOut() {
        String gCode = "";
        //I know it doesn't make sense to declare a base or height smaller than the tip width,
        //But I think overfilling in the middle is ok, and it's worth it to preserve the base to height ratio in the
        //first few boxes.
        double nBase = getTip() * (getBase() / getHeight());
        double nHeight = getTip() * (getHeight() / getBase());
        gCode = gCode.concat(center());
        while (nBase < getBase() && nHeight < getHeight()) {
            gCode = drawBox(gCode, nBase, nHeight);
            gCode = gCode.concat(moveLeft(getTip() * getTipScalar()));
            gCode = gCode.concat(moveDown(getTip() * getTipScalar()));
            nBase += (getBase() / getHeight()) * (2 * (getTip() * getTipScalar()));
            nHeight += 2 * (getTip() * getTipScalar());
        }
        gCode = cleanSpeeds(gCode);
        return gCode;
    }

    /**
     *
     * Draws a rectangle by stacking horizontal lines on top of each other.
     * Easy to generalize to any shape, but doesn't print nicely.
     * @return the gCode for a rectangle.
     */
    public String fillIntegral() {
        String gCode = "";
        double smallWidth = getBase() - (2 * (getTip() * getTipScalar()));
        gCode = drawBox(gCode, getBase(), getHeight());
        gCode = gCode.concat(moveRight(getTip() * getTipScalar()));
        gCode = gCode.concat(moveUp(getTip() * getTipScalar()));
        for (double i = getTip() * getTipScalar(); i < getHeight(); i += getTip() * getTipScalar()) {
            gCode = gCode.concat(moveIn(getSafeZ()));
            gCode = gCode.concat(moveRight(smallWidth));
            gCode = gCode.concat(moveOut(getSafeZ()));
            gCode = gCode.concat(moveUp(getTip() * getTipScalar()));
            gCode = gCode.concat(moveLeft(smallWidth));
        }
        gCode = cleanSpeeds(gCode);
        return gCode;
    }
    /**
     * First draws the outline, then fills with concentric rectangles from inside to outside.
     * @return gCode for a rectangle.
     */
    public String fillInkScape() {
        String gCode = "";
        gCode = drawBox(gCode, getBase(), getHeight());
        gCode = cleanSpeeds(gCode);
        gCode = gCode.concat(fillInToOut());
        return gCode;
    }

    /**
     * moves the tip from the bottom left to the center of a rectangle.
     * @return gCode to center the tip.
     */
    public String center() {
        String centerCode = "";
        centerCode = centerCode.concat(moveRight(getBase() / 2));
        centerCode = centerCode.concat(moveUp(getHeight() / 2));
        return centerCode;
    }

    /**
     * Removes redundant F######## from gCode to avoid CNC compile errors.
     * @param inputgCode your current gCode string to clean
     * @return a cleaned gCode string.
     */
    public String cleanSpeeds(final String inputgCode) {
        String[] gCodeArray = inputgCode.split("\n");
        boolean lastMoveZ = gCodeArray[0].contains("Z");
        for (int i = 1; i < gCodeArray.length; i++) {
            if (!lastMoveZ) {
                gCodeArray[i] = gCodeArray[i].replace("F" + String.format("%.6f", getSpeed()), "");
            } else {
                gCodeArray[i] = gCodeArray[i].replace("F" + String.format("%.6f", getzSpeed()), "");
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

    /**
     * draws a rectangle with the given parameters ho and vert.
     * Adds that gCode to the input String of gCode
     * @param inputgCode String to be added to.
     * @param ho horizontal dimmension of the new rectangle.
     * @param vert vertical dimmension of the new rectangle.
     * @return the input gCode with the rectangle concatenated to the end.
     */
    public String drawBox(String inputgCode, final double ho, final double vert) {
        inputgCode = inputgCode.concat(moveIn(getSafeZ()));
        inputgCode = inputgCode.concat(moveRight(ho));
        inputgCode = inputgCode.concat(moveUp(vert));
        inputgCode = inputgCode.concat(moveLeft(ho));
        inputgCode = inputgCode.concat(moveDown(vert));
        inputgCode = inputgCode.concat(moveOut(getSafeZ()));
        return inputgCode;
    }

    /**
     * Function makes gCode 10x larger so it can be tested in CAMotics.
     * <\p>
     *     First breaks the input gCode into a 2D array of each individual command.
     *     Next multiplies each X or Y shift by 10.
     *     Reassembles the array into a single string, which it returns.
     * </\p>
     * Does not yet account for the coordinate change from general xyz to Ejet xyz.
     * @param inputFill the gCode that you want to test
     * @return an upscaled version of the gCode that can be seen in CAMotics
     */
    public String toTestCode(String inputFill) {
        String testCode = "G91 \n";
        String[] gArray = inputFill.split("\n");
        String[][] g2Array = new String[gArray.length][];
        for (int i = 0; i < gArray.length; i++) {
            g2Array[i] = gArray[i].split(" ");
        }
        for (int i = 0; i < g2Array.length; i++) {
            for (int j = 0; j < g2Array[i].length; j++) {
                if (g2Array[i][j].contains("X") || g2Array[i][j].contains("Y")) {
                    g2Array[i][j] = g2Array[i][j].charAt(0) + (Double.toString(Double.parseDouble(g2Array[i][j].substring(1)) * 10));
                }
            }
        }
        for (int i = 0; i < g2Array.length; i++) {
            for (int j = 0; j < g2Array[i].length; j++) {
                testCode = testCode.concat(g2Array[i][j] + " ");
            }
            testCode = testCode.concat("\n");
        }
        return testCode;
    }
}
