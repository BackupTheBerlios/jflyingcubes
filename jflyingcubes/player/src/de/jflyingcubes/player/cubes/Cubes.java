/*
 * AnimatedElement.java
 *
 * Created on 22. April 2005, 23:56
 */

package de.jflyingcubes.player.cubes;

import de.jflyingcubes.player.paint.CubesPaint;
import de.jflyingcubes.player.paint.Rendering;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author dm
 */
public class Cubes implements Serializable {

    private List<Plugin> plugins = new LinkedList<Plugin>();
    private List<CubesPaint> paints = new LinkedList<CubesPaint>();
    private BufferedImage img;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    private int x = 0;
    private int y = 0;
    private int height = 0;
    private int width = 0;
    
    private int oldX = 0;
    private int oldY = 0;
    private int oldHeight = 0;
    private int oldWidth = 0;
    
    private Color borderColor = Color.BLACK;
    private Color backgroundColor  = Color.BLACK;
    private Color foreground  = Color.WHITE;
    private int arcWidth = 0;

    public Cubes() {
    }

    public Cubes(int x, int y) {
        this();
        setX(x);
        setY(y);
    }

    public Cubes(int x, int y, int width, int height) {
        this(x, y);
        setWidth(width);
        setHeight(height);
    }

    public void start() {
        for (Plugin plugin : plugins)
            plugin.start();
    }

    public void stop() {
        for (Plugin plugin : plugins)
            plugin.stop();
    }
    
    public void setPlugins(List<Plugin> plugins) {
        this.plugins.clear();
        this.plugins.addAll(plugins);
    }
    
    public void addPlugin(Plugin plugin) {
        plugin.setElement(this);
        plugins.add(plugin);
    }
    
    public BufferedImage getImage() {
        if (img == null)
            img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        paint(img.createGraphics());
        return img;
    }
    
    public void addCubesPaint(CubesPaint paint) {
        paints.add(paint);
    }
    public CubesPaint addCubesPaint(int x1, int y1, int x2, int y2) {
        CubesPaint cp = new CubesPaint(x1, y1, x2, y2);
        paints.add(cp);
        return cp;
    }
    
    public List<CubesPaint> getCubesPaints() {
        return paints;
    }
    
    public void setProperties(String name, String value){}

    public void setX(int x) {
        if (this.x == x)
            return;
        oldX = this.x;
        this.x = x;
        firePropertyChange("x", oldX, x);
    }
    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        if (this.y == y)
            return;
        oldY = this.y;
        this.y = y;
        firePropertyChange("y", oldY, y);
    }
    public int getY() {
        return this.y;
    }
    
    public void setWidth(int width) {
        if (this.width == width)
            return;
        oldWidth = this.width;
        this.width = width;
        firePropertyChange("width", oldWidth, width);
    }
    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        if (this.height == height)
            return;
        oldHeight = this.height;
        this.height = height;
        firePropertyChange("height", oldHeight, height);
    }
    public int getHeight() {
        return this.height;
    }
    
    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setSize(int[] dim) {
        setWidth(dim[0]);
        setHeight(dim[1]);
    }

    public void setPosition(int[] pos) {
        setX(pos[0]);
        setY(pos[1]);
    }

    public void setArcWidth(int arcWidth) {
        if (arcWidth == this.arcWidth)
            return;
        int old = this.arcWidth;
        this.arcWidth = old;
        firePropertyChange("arcWidth", old, arcWidth);
    }
    public int getArcWidth() {
        return this.arcWidth;
    }
    
    public void setBackground(Color background) {
        this.backgroundColor = background;
    }
    public Color getBackground() {
        return this.backgroundColor;
    }
    
    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }
    
    public Color getForeground() {
        return this.foreground;
    }
    
    public void setBorder(Color borderColor, int arcWidth) {
        this.borderColor = borderColor;
        this.arcWidth = arcWidth;
    }
    
    
    public void mouseExited(MouseEvent evt) {}
    
    public void mouseEntered(MouseEvent evt) {}
    
    public void mouseReleased(MouseEvent evt) {}
    
    public void mousePressed(MouseEvent evt) {}
    
    public void mouseClicked(MouseEvent evt) {}
    
    public void paint(Graphics g) {
        Color c = g.getColor();
        ((Graphics2D)g).setRenderingHints(Rendering.getRenderingHints());
        g.clearRect(oldX, oldY, oldWidth -1, oldHeight -1);
        paintBackground(g);
        paintBorder(g);        
        g.setColor(c);
    }
    
    public void paintBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        for(CubesPaint paint : getCubesPaints()) {
            g2.setPaint(paint.getPaint());
            g.fillRoundRect(0, 0, width -1, height -1, arcWidth, arcWidth);
        }
//        g.fillRoundRect(0, 0, width -1, height -1, arcWidth, arcWidth);
    }
    
    public void paintBorder(Graphics g) {
        if (borderColor != null) {
            g.setColor(borderColor);
            g.drawRoundRect(0, 0, width -1, height -1, arcWidth, arcWidth);
        }
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
    public void firePropertyChange(String name, int oldValue, int newValue) {
        support.firePropertyChange(name, oldValue, newValue);
    }

    public void firePropertyChange(String name, String oldValue, String newValue) {
        support.firePropertyChange(name, oldValue, newValue);
    }
}
