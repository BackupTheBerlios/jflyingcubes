/*
 * MoveDownPlugin.java
 *
 * Created on 23. April 2005, 00:55
 */

package de.jflyingcubes.player.cubes.plugins;

import de.jflyingcubes.player.util.ParseUtil;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.jflyingcubes.player.cubes.Cubes;
import de.jflyingcubes.player.cubes.Plugin;

/**
 *
 * @author dm
 */
public class MovePlugin extends Plugin {

    private int movePosition = 0;
    private int pixel = 1;
    private int toX = 0;
    private int toY = 0;
    private List<MoveData> moves = new LinkedList<MoveData>();

    public MovePlugin() {
        super();
    }
    
    public MovePlugin(List<MoveData> moves) {
        this();
        setMoves(moves);
    }

    public MovePlugin(Cubes element, List<MoveData> moves) {
        this(moves);
        setElement(element);
    }
    
    public void setMoves(List<MoveData> moves) {
        this.moves.clear();
        this.moves.addAll(moves);
    }

    private void moveRight(int pixel) {
        getElement().setX(getElement().getX() + pixel);
    }

    private void moveLeft(int pixel) {
        getElement().setX(getElement().getX() - pixel);
    }

    private void moveUp(int pixel) {
        getElement().setY(getElement().getY() - pixel);
    }
    
    private void moveDown(int pixel) {
        getElement().setY(getElement().getY() + pixel);
    }
    
    private void moveTo(int x, int y) {
        getElement().setX(x);
        getElement().setY(y);
    }
    
    private void moveTo(MoveData data) {
        moveTo(data.getX(), data.getY());
    }
    
    public void start() {
        setMoves(MoveFactory.getMoveDatas(getElement().getX(), getElement().getY(), toX, toY, pixel));
        super.start();
    }
    
    public void restart() {
        movePosition = 0;
        super.restart();
    }
    
    public void setProperties(String name, String value) {
        if (name.equalsIgnoreCase("move")) {
            int[] dim = ParseUtil.parseDimension(value);
            toX = dim[0];
            toY = dim[1];
        }
        if (name.equalsIgnoreCase("pixel"))
            pixel = ParseUtil.parseInteger(value);
    }
    
    public void timerActionPerformed(ActionEvent evt) {
        if (getElement() == null)
            throw new NullPointerException("Element in MovePlugin doesnt null");

        if (movePosition >= moves.size()) {
            stop();
            return;
        }
        moveTo(moves.get(movePosition));
        movePosition++;
    }
}
