package com.lakala.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenbo on 2014/12/1.
 */
public class Pagination {
    private int currentPage = 1;
    public int next = 0;
    public int previous = 0;
    private int rows = 0;
    private List list = null;
    public final int defaultRows = 10;
    private int totalPages = 0;

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setCurrentPage(int paramInt) {
        this.currentPage = paramInt;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getNext() {
        return this.next;
    }

    public int getPrevious() {
        return this.previous;
    }

    public void setRows(int paramInt) {
        this.rows = paramInt;
    }

    private int getRows() {
        return this.rows;
    }

    public void setList(List paramList) {
        this.list = paramList;
    }

    public void setUseDefaultRows() {
        this.rows = 10;
    }

    public Pagination() {
        this.rows = 10;
    }

    public Pagination(int paramInt1, int paramInt2, List paramList) {
        this.currentPage = paramInt1;
        this.rows = paramInt2;
        this.list = paramList;
    }

    public List doPagination() {
        if (this.list == null) {
            throw new NullPointerException("未传入必须的List对象.");
        }
        ArrayList localArrayList = new ArrayList();
        int i = this.list.size();
        int j = getPages(i);
        int k = 0;
        int m = 0;
        this.totalPages = j;
        if (this.currentPage >= j) {
            this.currentPage = j;
            k = (j - 1) * this.rows;
            m = i;
            this.previous = (j - 1);
            this.next = j;
        } else if (this.currentPage < j) {
            k = (this.currentPage - 1) * this.rows;
            m = this.currentPage * this.rows;
            this.next = (j + 1);
            this.previous = (j - 1);
        }
        for (int n = k; n < m; n++) {
            localArrayList.add(this.list.get(n));
        }
        return localArrayList;
    }

    private int getPages(int paramInt) {
        int i = 0;
        if ((paramInt % getRows() == 0) && (paramInt != 0)) {
            i = paramInt / getRows();
        } else {
            i = paramInt / getRows() + 1;
        }
        return i;
    }
}
