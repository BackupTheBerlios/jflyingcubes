/*
 * Gradient.java
 *
 * Created on 30. April 2005, 15:27
 */

package de.jflyingcubes.player.paint;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.Serializable;

/**
 *
 * @author dm
 */
public class Gradient extends CubesPaint {
    private GradientPaint paint = null;
    public Gradient() {
        
    }
    
    /** Creates a new instance of Gradient */
    public Gradient(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }
        
    public Paint getPaint() {
        if (getColors().size() >= 2) {
            if (paint == null)
                paint = new GradientPaint((float)getX1(), (float)getY1(), getColors().get(0), (float)getX2(), (float)getY2(), getColors().get(1));
            return paint;
        } else
            return super.getPaint();
    }
}
