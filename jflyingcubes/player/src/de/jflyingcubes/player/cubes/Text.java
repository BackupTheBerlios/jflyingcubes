/*
 * AnimatedTextElement.java
 *
 * Created on 24. April 2005, 22:59
 */

package de.jflyingcubes.player.cubes;

import de.jflyingcubes.player.util.ParseUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import de.jflyingcubes.player.cubes.plugins.TypedTextPlugin;

/**
 *
 * @author dm
 */
public class Text extends Cubes {
    
    private Font font = UIManager.getFont("label.font");
    private String text = null;
    private boolean isTyped = false;
    private int charCount = 1;

    /** Creates a new instance of AnimatedTextElement */
    public Text() {
        super();
    }

    public Text(String text) {
        this();
        setText(text);
    }
    
    public void setText(String text) {
        if (text == this.text)
            return;
        String old = this.text;
        this.text = text;
        firePropertyChange("text", old, text);
    }
        
    public String getText() {
        return text;
    }
        
    public void setCharCount(int charCount) {
        if (this.charCount == charCount)
            return;
        int old = this.charCount;
        this.charCount = charCount;
        firePropertyChange("charcount", old, charCount);
    }
    
    public void setProperties(String name, String value) {
        if (name == null)
            return;
        if (name.equalsIgnoreCase("text"))
            setText(value);
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Font oldFont = g.getFont();
        Color oldColor = g.getColor();
        int y = 0;
        g.setFont(font);
        if(getHeight() >= 0)
            y += (getHeight() / 2)  + (g.getFontMetrics().getHeight() / 2); // + font.getLineMetrics(text,((Graphics2D)g).getFontRenderContext()).getHeight();

        if (text != null) {
            g.setColor(getForeground());
            g.drawString(text.substring(0, charCount), 0, y);
        }
        g.setFont(oldFont);
        g.setColor(oldColor);
    }
}