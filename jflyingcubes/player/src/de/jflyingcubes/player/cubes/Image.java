/*
 * AnimatedImageElement.java
 *
 * Created on 23. April 2005, 13:05
 */

package de.jflyingcubes.player.cubes;

import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author dm
 */
public class Image extends Cubes {
    
    private ImageIcon icon;
    /** Creates a new instance of AnimatedImageElement */
    public Image() {
        super();
    }
    
    public Image(ImageIcon icon) {
        this();
        this.icon = icon;
    }
    
    public Image(ImageIcon icon, int width, int height) {
        this(icon);
        setSize(width, height);
    }
    
    public void setImage(ImageIcon icon) {
        this.icon = icon;
    }
    
    public void setProperties(String name, String value) {
        if (name == null)
            return;
        if (name.equalsIgnoreCase("image") && value != null);
            setImage(new ImageIcon(getClass().getResource(value)));
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        if (icon != null)
            g.drawImage(icon.getImage(), 0, 0, getWidth(),  getHeight(), null); 
    }
}
