/*
 * ParseUtil.java
 *
 * Created on 25. April 2005, 20:55
 */

package de.jflyingcubes.player.util;

import java.awt.Color;

/**
 *
 * @author dm
 */
public class ParseUtil {
    
    /** Creates a new instance of ParseUtil */
    public ParseUtil() {
    }
    
    public static boolean parseBoolean(String s) {
        return Boolean.parseBoolean(s);
    }
    
    public static int[] parseDimension(String s) {
        int[] ret = new int[2];
        ret[0] = parseInteger(s.substring(0, s.indexOf(",")).trim());
        ret[1] = parseInteger(s.substring(s.indexOf(",") + 1).trim());
        return ret;
    }
    
    public static int parseInteger(String s) {
        if (s.indexOf("A") >= 0 || s.indexOf("B") >= 0 ||
            s.indexOf("C") >= 0 || s.indexOf("D") >= 0 ||
            s.indexOf("E") >= 0 || s.indexOf("F") >= 0)
            return parseInteger(s, 16);
        else
            return parseInteger(s, 10);
    }
    
    public static int parseInteger(String s, int radix) {
        try {
            return Integer.parseInt(s, radix);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static Color parseColor(String s) {
        if (s.equalsIgnoreCase("null"))
            return null;
        if (s.startsWith("#"))
            s = s.substring(1);
        return new Color(parseInteger(s));
    }
}
