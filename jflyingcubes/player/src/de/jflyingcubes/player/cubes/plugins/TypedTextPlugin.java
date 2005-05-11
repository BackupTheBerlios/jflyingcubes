/*
 * TypedTextPlugin.java
 *
 * Created on 25. April 2005, 09:28
 */

package de.jflyingcubes.player.cubes.plugins;

import de.jflyingcubes.player.util.ParseUtil;
import java.awt.event.ActionEvent;

import de.jflyingcubes.player.cubes.Plugin;
import de.jflyingcubes.player.cubes.Text;

/**
 *
 * @author dm
 */
public class TypedTextPlugin extends Plugin {
    
    private int counter = 0;
    private boolean isTyped = false;
    /** Creates a new instance of TypedTextPlugin */
    public TypedTextPlugin() {
        super();
    }
    
    public void restart() {
        counter = 1;
        super.restart();
    }
    
    public void setProperties(String name, String value) {
        if (name.equalsIgnoreCase("typed"))
            isTyped = ParseUtil.parseBoolean(value);
    }
    
    public void timerActionPerformed(ActionEvent evt) {
        if (getElement() == null)
            throw new NullPointerException("Element in MovePlugin cant null");
        counter += 1;
        if (getElement() instanceof Text) {
            String text = ((Text)getElement()).getText();
            if (text != null && text.length() >= counter && isTyped) {
                ((Text)getElement()).setCharCount(counter);
            } else
                ((Text)getElement()).stop();
        }
    }
}
