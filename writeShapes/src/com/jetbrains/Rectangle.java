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
        String gCode = "";
        double nBase = getBase() - getTip();
        double nHeight = getHeight() - getTip();
        while (nBase > 0 && nHeight > 0) {
            gCode = drawBox(gCode, nBase, nHeight);
            gCode = gCode.concat(Control.moveRight(getTip() * getTipScalar()));
            gCode = gCode.concat(Control.moveUp(getTip() * getTipScalar()));
            nBase -= 2 * getTip() * getTipScalar();
            nHeight -= 2 * getTip() * getTipScalar();
        }
        gCode = gCode.concat(Control.moveOut(getSafeZ()));
        gCode = cleanSpeeds(gCode);
        return gCode;
    }

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
            gCode = gCode.concat(Control.moveLeft(getTip() * getTipScalar()));
            gCode = gCode.concat(Control.moveDown(getTip() * getTipScalar()));
            nBase += (getBase() / getHeight()) * (2 * (getTip() * getTipScalar()));
            nHeight += 2 * (getTip() * getTipScalar());
        }
        //I think the problem was having the ratio in both nBase and nHeight.
        gCode = cleanSpeeds(gCode);
        return gCode;
    }

    public String fillIntegral() {
        String gCode = "";
        double smallWidth = getBase() - (2 * (getTip() * getTipScalar()));
        gCode = drawBox(gCode, getBase(), getHeight());
        gCode = gCode.concat(Control.moveRight(getTip() * getTipScalar()));
        gCode = gCode.concat(Control.moveUp(getTip() * getTipScalar()));
        for (double i = getTip() * getTipScalar(); i < getHeight(); i += getTip() * getTipScalar()) {
            gCode = gCode.concat(Control.moveIn(getSafeZ()));
            gCode = gCode.concat(Control.moveRight(smallWidth));
            gCode = gCode.concat(Control.moveOut(getSafeZ()));
            gCode = gCode.concat(Control.moveUp(getTip() * getTipScalar()));
            gCode = gCode.concat(Control.moveLeft(smallWidth));
        }
        gCode = cleanSpeeds(gCode);
        return gCode;
    }

    public String fillInkScape() {
        String gCode = "";
        gCode = drawBox(gCode, getBase(), getHeight());
        gCode = cleanSpeeds(gCode);
        gCode = gCode.concat(fillInToOut());
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
    public String drawBox(String inputgCode, final double ho, final double vert) {
        inputgCode = inputgCode.concat(Control.moveIn(getSafeZ()));
        inputgCode = inputgCode.concat(Control.moveRight(ho));
        inputgCode = inputgCode.concat(Control.moveUp(vert));
        inputgCode = inputgCode.concat(Control.moveLeft(ho));
        inputgCode = inputgCode.concat(Control.moveDown(vert));
        inputgCode = inputgCode.concat(Control.moveOut(getSafeZ()));
        return inputgCode;
    }

    public String drawArray(final int hoNum, final int vertNum, final double separation) {
        String gCode = Shape.INITIALIZE;
        for (int i = 0; i < vertNum; i++) {
            for (int j = 0; j < hoNum; j++) {
                 gCode = gCode.concat(fillInToOut());
                 gCode = gCode.concat(Control.moveRight(getBase() + separation));
            }
            gCode = gCode.concat(Control.moveLeft(2 * (separation + getBase())));
            gCode = gCode.concat(Control.moveUp(separation));
        }
        return "";
    }
}
