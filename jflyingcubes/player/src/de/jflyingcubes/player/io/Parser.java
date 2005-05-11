/*
 * Parser.java
 *
 * Created on 23. April 2005, 19:03
 */

package de.jflyingcubes.player.io;

import de.jflyingcubes.player.cubes.Cubes;
import de.jflyingcubes.player.cubes.Image;
import de.jflyingcubes.player.cubes.Plugin;
import de.jflyingcubes.player.cubes.Text;
import de.jflyingcubes.player.cubes.plugins.MoveFactory;
import de.jflyingcubes.player.cubes.plugins.MovePlugin;
import de.jflyingcubes.player.page.Page;
import de.jflyingcubes.player.page.Pages;
import de.jflyingcubes.player.paint.CubesPaint;
import de.jflyingcubes.player.util.ParseUtil;
import java.awt.Color;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author dm
 */
public class Parser extends DefaultHandler {

    private static String TAG_PAGE        = "page";
    private static String TAG_CUBES       = "cubes";
    
    private static String TAG_PAINT       = "paint";
    private static String TAG_COLOR       = "color";
    private static String TAG_PLUGIN      = "plugin";
    private static String TAG_PROPERTY    = "property";
    
    //Page attributes
    private static String ATT_AUTO        = "auto";
    private static String ATT_DELAY       = "delay";
    private static String ATT_SOUND       = "sound";
    private static String ATT_LOOP        = "loop";
    private static String ATT_BORDERCOLOR = "bordercolor";
    private static String ATT_FOREGROUND  = "foreground";

    // paint attributes
    private static String ATT_X1          = "x1";
    private static String ATT_X2          = "x2";
    private static String ATT_Y1          = "y1";
    private static String ATT_Y2          = "y2";
    
    // element attributes
    private static String ATT_X           = "x";
    private static String ATT_Y           = "y";
    private static String ATT_POSITION    = "position";
    private static String ATT_WIDTH       = "width";
    private static String ATT_HEIGHT      = "height";
    private static String ATT_SIZE        = "size";
    
    // plugin attributes
    private static String ATT_CLASS       = "class";
    
    // property attributes
    private static String ATT_NAME        = "name";
    private static String ATT_VALUE       = "value";
    
    private Pages pages;
    
    // temp
    private Page page = null;
    private Cubes cubes = null;
    private Plugin plugin = null;
    private CubesPaint paint = null;
    
    /** Creates a new instance of Parser */
    public Parser(Pages pages) {
        this.pages = pages;
    }
    
    public static void parse(Pages pages, InputStream is) {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(is, new Parser(pages));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attr) {
        if (qName.equalsIgnoreCase(TAG_PAGE))
            readPage(attr);
        if (qName.equalsIgnoreCase(TAG_CUBES))
            readCubes(attr);
        if (qName.equalsIgnoreCase(TAG_PLUGIN))
            readPlugin(attr);
        if (qName.equalsIgnoreCase(TAG_PAINT))
            readPaint(attr);
        if (qName.equalsIgnoreCase(TAG_PROPERTY))
            readProperty(attr);
        if (qName.equalsIgnoreCase(TAG_COLOR)) {
            if (paint != null)
                paint.addColor(ParseUtil.parseColor(attr.getValue(ATT_VALUE)));
        }
            
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase(TAG_PAGE))
            page = null;
        if (qName.equalsIgnoreCase(TAG_CUBES))
            cubes = null;
        if (qName.equalsIgnoreCase(TAG_PAINT))
            paint = null;
        if (qName.equalsIgnoreCase(TAG_PLUGIN))
            plugin = null;             
    }

    private void readPage(Attributes attr) {
        page = new Page();
        pages.addPage(page);
        
        if(attr.getValue(ATT_AUTO) != null)
            page.setAutomatic(ParseUtil.parseBoolean(attr.getValue(ATT_AUTO)));
        
        if(attr.getValue(ATT_DELAY) != null)
            page.setDelay(ParseUtil.parseInteger(attr.getValue(ATT_DELAY)));

        if (attr.getValue(ATT_SOUND) != null)
            page.setSoundFile(attr.getValue(ATT_SOUND));

        if (attr.getValue(ATT_NAME) != null)
            page.setName(attr.getValue(ATT_NAME));

        if (attr.getValue(ATT_LOOP) != null)
            page.setLoop(ParseUtil.parseBoolean(attr.getValue(ATT_LOOP)));
    }
    
    private void readPaint(Attributes attr) {
        if (attr.getValue(ATT_CLASS) != null) {
            Object o = createInstance(attr.getValue(ATT_CLASS));
            paint = (CubesPaint)o;
            if (attr.getValue(ATT_X1) != null)
                paint.setX1(ParseUtil.parseInteger(attr.getValue(ATT_X1)));
            if (attr.getValue(ATT_Y1) != null)
                paint.setY1(ParseUtil.parseInteger(attr.getValue(ATT_Y1)));
            if (attr.getValue(ATT_X2) != null)
                paint.setX2(ParseUtil.parseInteger(attr.getValue(ATT_X2)));
            if (attr.getValue(ATT_Y2) != null)
                paint.setY2(ParseUtil.parseInteger(attr.getValue(ATT_Y2)));
            if (page != null && cubes == null)
                page.addPaint(paint);
            else if (cubes != null)
                cubes.addCubesPaint(paint);
        }
    }
    
    private void readCubes(Attributes attr) {
        if (attr.getValue(ATT_CLASS) != null) {
            Object o = createInstance(attr.getValue(ATT_CLASS));
            cubes = (Cubes) o;
            readAttributesOfElements(cubes, attr);
            page.addElement(cubes);
        }
    }
    
    private void readPlugin(Attributes attr) {
        if (attr.getValue(ATT_CLASS) != null) {
            Object o = createInstance(attr.getValue(ATT_CLASS));
            plugin = (Plugin)o;
            if (attr.getValue(ATT_DELAY) != null)
                plugin.setDelay(ParseUtil.parseInteger(attr.getValue(ATT_DELAY)));
                plugin.setElement(cubes);
        }
        if (plugin != null && cubes != null)
            cubes.addPlugin(plugin);
    }

    private void readProperty(Attributes attr) {
        if (plugin == null && cubes != null)
            cubes.setProperties(attr.getValue(ATT_NAME), attr.getValue(ATT_VALUE));
        else if (plugin != null)
            plugin.setProperties(attr.getValue(ATT_NAME), attr.getValue(ATT_VALUE));
    }
    
    private Object createInstance(String str) {
        try {
            Class c = Class.forName(str);
            return c.newInstance();
        } catch (Exception e) {
            System.out.println("ERROR: Class not found '" + str + "'");
        }
        return null;
    }
    
    private static void readAttributesOfElements(Cubes el, Attributes attr) {
        if (attr.getValue(ATT_BORDERCOLOR) != null)
            el.setBorder(ParseUtil.parseColor(attr.getValue(ATT_BORDERCOLOR)), 0);
        if (attr.getValue(ATT_FOREGROUND) != null)
            el.setForeground(ParseUtil.parseColor(attr.getValue(ATT_FOREGROUND)));
        if (attr.getValue(ATT_X) != null)
            el.setX(ParseUtil.parseInteger(attr.getValue(ATT_X)));        
        if (attr.getValue(ATT_Y) != null)
            el.setY(ParseUtil.parseInteger(attr.getValue(ATT_Y)));
        if (attr.getValue(ATT_POSITION) != null)
            el.setPosition(ParseUtil.parseDimension(attr.getValue(ATT_POSITION)));
        if (attr.getValue(ATT_WIDTH) != null)
            el.setWidth(ParseUtil.parseInteger(attr.getValue(ATT_WIDTH)));
        if (attr.getValue(ATT_HEIGHT) != null)
            el.setHeight(ParseUtil.parseInteger(attr.getValue(ATT_HEIGHT)));
        if (attr.getValue(ATT_SIZE) != null)
            el.setSize(ParseUtil.parseDimension(attr.getValue(ATT_SIZE)));
    }
}
