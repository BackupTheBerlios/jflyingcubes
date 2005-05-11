/*
 * MoveData.java
 *
 * Created on 23. April 2005, 10:21
 */

package de.jflyingcubes.player.cubes.plugins;

import java.io.Serializable;


/**
 *
 * @author dm
 */
public class MoveData implements Serializable {
    
    private final int x;
    private final int y;
    /** Creates a new instance of MoveData */
    public MoveData(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
