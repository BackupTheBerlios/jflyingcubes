/*
 * BlendPlugin.java
 *
 * Created on 26. April 2005, 20:41
 */

package de.jflyingcubes.player.cubes.plugins;

import de.jflyingcubes.player.cubes.Plugin;
import de.jflyingcubes.player.util.ParseUtil;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author dm
 */
public class BlendPlugin extends Plugin{

    Color toColor = null;
    /** Creates a new instance of BlendPlugin */
    public BlendPlugin() {
        super();
    }
    
    public void start() {
        if (toColor == null)
            toColor = Color.RED;
        super.start();
    }
    
    public void setProperties(String name, String value) {
        if (name.equalsIgnoreCase("toColor"))
            toColor = ParseUtil.parseColor(value);
    }
    
    public void timerActionPerformed(ActionEvent evt) {
//        getElement().setPaint(new GradientPaint((float) getElement().getX(),
//                                     (float) getElement().getY(),
//                                     getElement().getBackground(),
//                                     (float) getElement().getX() + getElement().getWidth(),
//                                     (float) getElement().getY() + getElement().getHeight(),
//                                     Color.RED));
    }
}
