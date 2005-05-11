/*
 * MoveFactory.java
 *
 * Created on 23. April 2005, 10:28
 */

package de.jflyingcubes.player.cubes.plugins;

import java.lang.NumberFormatException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author dm
 */
public class MoveFactory {
    
    /** Creates a new instance of MoveFactory */
    public MoveFactory() {
        
    }
    public static List<MoveData> getMoveDatas(int startX, int startY, int endX, int endY) {
        return getMoveDatas(startX, startY, endX, endY, 1);
    }
    public static List<MoveData> getMoveDatas(int startX, int startY, int endX, int endY, int pixel) {
        List<MoveData> retValue = new LinkedList<MoveData>();

        int widthX = 0;
        if (startX > endX)
            widthX = startX - endX;
        else
            widthX = endX - startX;
        
        int widthY = 0;
        if (startY > endY)
            widthY = startY - endY;
        else
            widthY = endY - startY;
        boolean mem = false;
        while (startX <= endX && startY <= endY) {
            retValue.add(new MoveData(startX, startY));
            double x = pixel;
            double y = pixel;
            
            if (widthX > widthY) {
                x = ((double)widthX / (double)widthY) * pixel;
                int afterdigits = getAfterDigits(x);
                if ( afterdigits > 0 && mem ) {
                    x += 1;
                    mem = false;
                } else if (afterdigits > 0)
                    mem = true;
            } else if (widthX < widthY) {
                y = ((double) widthY / (double)widthX) * pixel;
                int afterdigits = getAfterDigits(y);
                if (afterdigits > 0 && mem) {
                    y += 1;
                    mem = false;
                } else if (afterdigits > 0)
                    mem = true;
            }
            startX += (int)x;
            startY += (int)y;
        }
        if (startX > endX && startY > endY) {
            retValue.add(new MoveData(endX, endY));
        }
        return retValue;
    }
    

    private static int getAfterDigits(double d) {
        String str = String.valueOf(d);
        int afterdigits = 0;
        try {
            afterdigits = Integer.parseInt(str.substring(str.indexOf(".") + 1));
        } catch(NumberFormatException e) {}
        return afterdigits;
    }
    
}
