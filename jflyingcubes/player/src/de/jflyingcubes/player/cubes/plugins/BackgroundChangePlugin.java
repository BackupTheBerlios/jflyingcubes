/*
 * TurnARoundPlugin.java
 *
 * Created on 25. April 2005, 22:57
 */

package de.jflyingcubes.player.cubes.plugins;

import de.jflyingcubes.player.cubes.Plugin;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 *
 * @author dm
 */
public class BackgroundChangePlugin extends Plugin{
    
    /** Creates a new instance of TurnARoundPlugin */
    public BackgroundChangePlugin() {
        super();
    }
    
    public void setProperties(String name, String value) {
        
    }
    
    public void timerActionPerformed(ActionEvent evt) {
        Color c = getElement().getBackground();
        getElement().setBackground(new Color(c.getRGB() + 10));
//        getElement().setX(getElement().getY() - i);
        
    }
}
