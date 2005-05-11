/*
 * Paint.java
 *
 * Created on 30. April 2005, 15:49
 */

package de.jflyingcubes.player.paint;

import java.awt.Color;
import java.awt.Paint;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author dm
 */
public class CubesPaint implements Serializable{

    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;
    
    private List<Color> colors = new LinkedList<Color>();
    
    public CubesPaint() {
        
    }
    
    public CubesPaint(int x1, int y1, int x2, int y2) {
    }
    
    public void setStart(int x1, int y1) {
        setX1(x1);
        setY1(y1);
    }

    public void setEnd(int x2, int y2) {
        setX2(x2);
        setY2(y2);
    }
    
    public void setX1(int x1) {
        this.x1 = x1;
    }
    public int getX1() {
        return x1;
    }
    
    public void setY1(int y1) {
        this.y1 = y1;
    }
    public int getY1() {
        return y1;
    }
    
    public void setX2(int x2) {
        this.x2 = x2;
    }
    public int getX2() {
        return x2;
    }
    
    public void setY2(int y2) {
        this.y2 = y2;
    }
    public int getY2() {
        return y2;
    }
    
    public void addColor(Color color) {
        colors.add(color);
    }
    
    public List<Color> getColors() {
        return colors;
    }

    public Paint getPaint() {
        if (colors.size() >= 1)
            return colors.get(0);
        else
            return Color.BLACK;
    }
}
