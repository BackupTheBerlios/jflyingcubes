/*
 * Page.java
 *
 * Created on 23. April 2005, 19:17
 */

package de.jflyingcubes.player.page;

import de.jflyingcubes.player.cubes.Cubes;
import de.jflyingcubes.player.cubes.CubesCollection;
import de.jflyingcubes.player.paint.CubesPaint;
import de.jflyingcubes.player.paint.Gradient;
import de.jflyingcubes.player.paint.Rendering;
import de.jflyingcubes.player.sound.Player;
import de.jflyingcubes.player.util.Screen;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author dm
 */
public class Page implements Serializable, PropertyChangeListener {
    
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    private boolean automatic = false;
    private int delay = 0;
    private Color background = Color.BLUE;
    private String soundFile = null;
    private boolean loop = false;
    private Player player = null;
    private CubesCollection cubes = new CubesCollection();
    private List<CubesPaint> paints = new LinkedList<CubesPaint>();
    private String name = "";
    private final BufferedImage img;
    
    private int width  = 600;
    private int height = 400;

    /** Creates a new instance of Page */
    public Page() {
        img = new BufferedImage(Screen.getScreenWidth(), Screen.getScreenHeight(), BufferedImage.TYPE_INT_RGB);
        cubes.addPropertyChangeListener(this);
    }
    
    public Page(boolean automatic) {
        this();
        this.automatic = automatic;
    }
    
    public Page(int delay, boolean automatic) {
        this();
        this.delay = delay;
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Image getImage() {
        paint(img.createGraphics());
        return img;
    }
    
    public void setBackground(Color bgColor) {
        this.background = bgColor;
    }
    
    public Color getBackground() {
        return this.background;
    }
    
    public CubesPaint addPaint(int x1, int y1, int x2, int y2) {
        CubesPaint cp = new CubesPaint(x1, y1, x2, y2);
        paints.add(cp);
        return cp;
    }
    public void addPaint(CubesPaint cp) {
        paints.add(cp);
    }
    
    public List<CubesPaint> getPaints() {
        return paints;
    }
    public CubesPaint getCubesPaintAt(int index) {
        return paints.get(index);
    }
    
    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }
    
    public boolean isAutomatic() {
        return this.automatic;
    }
    
    public void setSoundFile(String file) {
        this.soundFile = file;
    }
    
    public String getSoundFile() {
        return this.soundFile;
    }
    
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void start() {
        for (Cubes el :cubes.getElements())
            el.start();
        if (soundFile != null) {
            player = new Player(soundFile);
            player.play(loop);
        }
    }
    
    public void stop() {
        for (Cubes el :cubes.getElements())
             el.start();
        if(player != null)
            player.stop();
    }
    
    public void addElement(Cubes element) {
        cubes.addElement(element);
    }
    
    public List<Cubes> getCubes() {
        return cubes.getElements();
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
    
    public void firePropertyChange(String name, int oldValue, int newValue) {
        support.firePropertyChange(name, oldValue, newValue);
    }

    public void firePropertyChange(String name, String oldValue, String newValue) {
        support.firePropertyChange(name, oldValue, newValue);
    }

    public void paint(Graphics g) {
        g.clearRect(0,0, Screen.getScreenWidth(), Screen.getScreenHeight());
        ((Graphics2D)g).scale(Screen.getScaleWidth(width), Screen.getScaleHeight(height));
        ((Graphics2D)g).setRenderingHints(Rendering.getRenderingHints());
        for (CubesPaint p : getPaints()) {
            ((Graphics2D)g).setPaint(p.getPaint());
            g.fillRect(0, 0, width, height );
        }
        for(Cubes c : getCubes()) {
            g.drawImage(c.getImage(), c.getX(), c.getY(), c.getWidth(), c.getHeight(), null);
        }
    }
}
