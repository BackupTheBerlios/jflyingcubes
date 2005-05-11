/*
 * Viewer.java
 *
 * Created on 22. April 2005, 19:07
 */

package de.jflyingcubes.player;

import de.jflyingcubes.player.cubes.CubesCollection;
import de.jflyingcubes.player.cubes.Cubes;
import de.jflyingcubes.player.page.Page;
import de.jflyingcubes.player.page.Pages;
import de.jflyingcubes.player.paint.CubesPaint;
import de.jflyingcubes.player.paint.Rendering;
import de.jflyingcubes.player.util.Screen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author dm
 */
public class ViewerPanel extends JPanel implements PropertyChangeListener, MouseListener, MouseMotionListener{
    
    private final Pages pages;
    
    private int pageIndex = -1;
    private int mouseX = -1;
    private int mouseY = -1;
    
    private Timer timer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            updatePage();
        }
    });
    
    /** Creates a new instance of Viewer */
    public ViewerPanel(Pages pages) {
        this.pages = pages;
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void start() { // ????
        timer.start();
    }
    
    public void updatePage() {
        if (pages != null && pages.size() > pageIndex + 1) {
            timer.stop();
            if (pageIndex >= 0) {
                pages.getPageAt(pageIndex).removePropertyChangeListener(this);
                pages.getPageAt(pageIndex).stop();
            }
            pageIndex++;
            
            Page p = pages.getPageAt(pageIndex);
            
            p.addPropertyChangeListener(this);
            if (p.isAutomatic()) {
                timer.setDelay(p.getDelay());
                timer.setInitialDelay(p.getDelay());
                timer.start();
            }
            p.start();
            updateUI();
        }
    }
    
    // MouseEvents ...
    public void mouseMoved(MouseEvent evt) {
        if (pages == null || pageIndex < 0)
            return;
        for (Cubes e : pages.getPageAt(pageIndex).getCubes()) {
            if (isMouseEnteredElement(e, evt))
                e.mouseEntered(evt);
            else if (isMouseExitedElement(e, evt))
                e.mouseExited(evt);
        }
        this.mouseX = evt.getX();
        this.mouseY = evt.getY();
    }
    
    private boolean isMouseEnteredElement(Cubes e, MouseEvent evt) {
        if ((evt.getX() >= e.getX() && evt.getX() <= e.getX() + e.getWidth()) &&
                (evt.getY() >= e.getY() && evt.getY() <= e.getY() + e.getHeight()) &&
                (mouseX < e.getX() || mouseX > e.getX() + e.getWidth() ||
                mouseY < e.getY() || mouseY > e.getY() + e.getHeight()))
            return true;
        return false;
    }
    
    private boolean isMouseExitedElement(Cubes e, MouseEvent evt) {
        if (((evt.getX() < e.getX() || evt.getX() > e.getX() + e.getWidth()) ||
                (evt.getY() < e.getY() || evt.getY() > e.getY() + e.getHeight())) &&
                (mouseX >= e.getX() || mouseX <= e.getX() + e.getWidth() ||
                mouseY >= e.getY() || mouseY <= e.getY() + e.getHeight()))
            return true;
        return false;
    }
    
    public void mouseDragged(MouseEvent evt) {}
    
    public void mouseExited(MouseEvent evt) {
    }
    
    public void mouseEntered(MouseEvent evt) {
    }
    
    public void mouseReleased(MouseEvent evt) {
        if (pages == null || pageIndex < 0)
            return;
        for (Cubes e : pages.getPageAt(pageIndex).getCubes())
            if ((evt.getX() >= e.getX() && evt.getX() <= e.getX() + e.getWidth()) &&
                (evt.getY() >= e.getY() && evt.getY() <= e.getY() + e.getHeight()))
                e.mouseReleased(evt);
        
    }
    
    public void mousePressed(MouseEvent evt) {
        if (pages == null || pages.size() >= pageIndex)
            return;
        for (Cubes e : pages.getPageAt(pageIndex).getCubes())
            if ((evt.getX() >= e.getX() && evt.getX() <= e.getX() + e.getWidth()) &&
                (evt.getY() >= e.getY() && evt.getY() <= e.getY() + e.getHeight()))
                e.mousePressed(evt);
        
    }
    
    public void mouseClicked(MouseEvent evt) {
        if (pages == null || pageIndex < 0)
            return;
        boolean isElement = false;
        for (Cubes e : pages.getPageAt(pageIndex).getCubes())
            if ((evt.getX() >= e.getX() && evt.getX() <= e.getX() + e.getWidth()) &&
                (evt.getY() >= e.getY() && evt.getY() <= e.getY() + e.getHeight())) {
            e.mouseClicked(evt);
            isElement = true;
            }
        if (!isElement && evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2)
            updatePage();
        
    }

    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D)g).setRenderingHints(Rendering.getRenderingHints());
        if (pages != null && pageIndex >= 0) {
            Page p = pages.getPageAt(pageIndex);
            Image img = p.getImage();
            g.drawImage(img, 0, 0, Screen.getScreenWidth(), Screen.getScreenHeight(), null);
        }
    }
    
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        updateUI();
    }
}
