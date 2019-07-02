package com.xhkj.server.energy.page;

import java.util.Map;

public class BootstrapTableParams {
    private int limit;
    private int offset;
    private int pageNum;
    private String sort;
    private String order;
    private String search;
    private Map<String, String> params;

    public int getLimit() {
        return limit;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "BootstrapTableParams{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", pageNum=" + pageNum +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", search='" + search + '\'' +
                ", params=" + params +
                '}';
    }
}
