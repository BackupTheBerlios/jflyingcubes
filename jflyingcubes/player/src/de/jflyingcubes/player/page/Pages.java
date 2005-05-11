/*
 * Pages.java
 *
 * Created on 23. April 2005, 19:14
 */

package de.jflyingcubes.player.page;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author dm
 */
public class Pages {
    
    private List<Page> pages = new LinkedList<Page>();
    /** Creates a new instance of Pages */
    public Pages() {
    }
    
    public void addPage(Page page) {
        pages.add(page);
    }
    
    public Page getPageAt(int index) {
        return pages.get(index);
    }
    
    public int size() {
        return pages.size();
    }
}
