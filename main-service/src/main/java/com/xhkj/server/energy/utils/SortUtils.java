package com.xhkj.server.energy.utils;

import com.xhkj.server.energy.page.Page;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SortUtils {

    public static List<Map<String, Object>> sortListMap(List list, Page page) {
        if (page.getSort() == null) {
            return list;
        }
        list.sort((Comparator<Map<String, String>>) (o1, o2) -> {
            if ("asc".equals(page.getSortOrder())) {
                return o2.get(page.getSort()).compareTo(o1.get(page.getSort()));
            } else {
                return o1.get(page.getSort()).compareTo(o2.get(page.getSort()));
            }

        });
        return list;
    }

}