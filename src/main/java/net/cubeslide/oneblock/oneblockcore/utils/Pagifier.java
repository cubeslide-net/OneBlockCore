package net.cubeslide.oneblock.oneblockcore.utils;

import java.util.*;

public class Pagifier<T> {
    // Properties
    private int pageSize;
    // List of pages
    private List<List<T>> pages;
    // Constructor
    public Pagifier(int pageSize){
        this.pageSize = pageSize;
        this.pages = new ArrayList<>();
        // Create first page
        this.pages.add(new ArrayList<T>());
    }
    /**
     * Add item to pages
     * (Creates a new page if the previous page is not existing or full)
     * @param item The item you want to add
     */
    public void addItem(T item){
        int pageNum = pages.size() - 1;
        List<T> currentPage = this.pages.get(pageNum);
        // Add page if full
        if(currentPage.size() >= this.pageSize) {
            currentPage = new ArrayList<>();
            this.pages.add(currentPage);
        }
        currentPage.add(item);
    }
    /**
     * Get the items of a page
     * @param pageNum Number of the page (beginning at 0)
     * @return List of the page's items, null if page not existing
     */
    public List<T> getPage(int pageNum){
        if(this.pages.size() == 0 || this.pages.get(pageNum) == null)
            return null;
        return this.pages.get(pageNum);
    }
    /**
     * Get all pages
     * @return List containing all pages
     */
    public List<List<T>> getPages(){
        return this.pages;
    }
    /**
     * Get the current set page size
     * @return The current page size
     */
    public int getPageSize(){
        return this.pageSize;
    }
}