/*
 * Defaults.java
 *
 * Created on 4. Mai 2005, 00:53
 */

package de.jflyingcubes.player.paint;

import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dm
 */
public class Rendering {
    
    /** Creates a new instance of Defaults */
    public Rendering() {
    }
    
    public static Map<Object, Object> getRenderingHints() {
        Map<Object, Object> m = new HashMap<Object, Object>();
        m.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return m;
    }

}
