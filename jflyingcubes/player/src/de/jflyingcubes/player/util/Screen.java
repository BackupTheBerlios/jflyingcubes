/*
 * Screen.java
 *
 * Created on 3. Mai 2005, 13:59
 */

package de.jflyingcubes.player.util;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author dm
 */
public class Screen {
    
    /** Creates a new instance of Screen */
    public Screen() {
    }
    
    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    public static int getScreenWidth() {
        return getScreenSize().width;
    }

    public static int getScreenHeight() {
        return getScreenSize().height;
    }
    
    public static double getScaleHeight(int height) {
        return ((double)getScreenHeight() / (double)height);
    }

    public static double getScaleWidth(int width) {
        return ((double)getScreenWidth()/ (double)width);
    }
}
