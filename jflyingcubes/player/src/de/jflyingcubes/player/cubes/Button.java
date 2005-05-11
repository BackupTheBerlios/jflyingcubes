/*
 * ButtonElement.java
 *
 * Created on 23. April 2005, 13:18
 */

package de.jflyingcubes.player.cubes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.UIManager;

/**
 *
 * @author dm
 */
public class Button extends Cubes {
    
    private List<ActionListener> actions = new LinkedList<ActionListener>();
    
    private Font font = UIManager.getFont("label.font");
    private String text = "";
    private Color rolloverColor = Color.BLUE;
    /** Creates a new instance of ButtonElement */
    public Button(String text) {
        super();
        setText(text);
    }
    
    public Button(String text, int x, int y) {
        super(x, y, 100, 20); // calc width & height from String 
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
        return this.text;
    }
    
    public void setRolloverColor(Color rollover) {
        if (rollover == this.rolloverColor)
            return;
        this.rolloverColor = rollover;
    }
    public Color getRolloverColor() {
        return this.rolloverColor;
    }
    
    public void setProperties(String name, String value) {
        if (name == null)
            return;
        if (name.equalsIgnoreCase("text"))
            setText(value);
//        if (name.equalsIgnoreCase("image"))
//            setImage();
    }
    
    public void addActionListener(ActionListener listener) {
        actions.add(listener);
    }
    
    public void mouseEntered(MouseEvent evt) {
        evt.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(getRolloverColor(), getArcWidth());
    }
    
    public void mouseExited(MouseEvent evt) {
        evt.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        setBorder(getBackground(), getArcWidth());
    }
    
    public void mouseClicked(MouseEvent evt) {
        performAction(evt, "clicked");
    }
    
    private void performAction(MouseEvent evt, String pressed) {
        String command = "Button_" + pressed;
        
        for(ActionListener listener: actions)
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command ));
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Font oldFont = g.getFont();
        int y = getY();
        g.setFont(font);
        if(getHeight() >= 0)
            y += (getHeight() / 2) + (g.getFontMetrics().getHeight() / 2);

        g.setColor(getForeground());
        if (text != null)
            g.drawString(text, getX(), y);
        g.setFont(oldFont);
    }
    
}
