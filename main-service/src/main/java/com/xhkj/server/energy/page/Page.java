package com.xhkj.server.energy.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对分页的基本数据进行一个简单的封装
 */
public class Page<T> {

    private int limit = 20;// 每页显示的记录数，默认是20
    private int offset = 0;// 请求数据开始的位置，默认是0
    private int total = 0;// 总记录数
    private String sort;//排序字段
    private String sortOrder;//desc asc
    private List<T> rows;// 对应当前页返回Map 后端排序用这个
    private List<T> data;// 对应当前页返回Map 前端排序用这个
    private Map<String, Object> params = new HashMap<>();// 其他的参数我们把它分装成一个Map对象
    private T entity;

    public int getLimit() {
        return limit;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public T getEntity() {
        return (T) entity;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Page() {
        super();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

}
