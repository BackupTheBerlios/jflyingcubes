/*
 * SizePlugin.java
 *
 * Created on 25. April 2005, 23:56
 */

package de.jflyingcubes.player.cubes.plugins;

import de.jflyingcubes.player.cubes.Plugin;
import de.jflyingcubes.player.util.ParseUtil;
import java.awt.event.ActionEvent;

/**
 *
 * @author dm
 */
public class SizePlugin extends Plugin{
    
    private int pixel = 1;
    /** Creates a new instance of SizePlugin */
    public SizePlugin() {
        super();
    }
    
    public void setProperties(String name, String value) {
        if (name == null)
            return;
        
        if (name.equalsIgnoreCase("pixel"))
            pixel = ParseUtil.parseInteger(value);
        
        if (name.equalsIgnoreCase("to") && !value.equals("+"))
            pixel = ParseUtil.parseInteger(value + pixel);
            
        
    }
    
    public void timerActionPerformed(ActionEvent evt) {
        getElement().setWidth(getElement().getWidth() + pixel);
        getElement().setHeight(getElement().getHeight() + pixel);
    }
}
