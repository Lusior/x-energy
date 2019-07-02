package com.xhkj.server.energy.entity;

import com.xhkj.server.energy.dao.mybatis.vo.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuDto {

    private String id;
    private String name;
    private String url;
    private String icon;
    private boolean leaf;
    private List<MenuDto> subMenus;

    public MenuDto(Menu menu, boolean isLeaf) {
        this.id = menu.getMenuId().toString();
        this.name = menu.getMenuName();
        this.url = menu.getMenuUrl();
        this.leaf = isLeaf;
        this.icon = menu.getIcon();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void addSubMenu(MenuDto menuDto) {
        if (subMenus == null) {
            subMenus = new ArrayList<>();
        }
        subMenus.add(menuDto);
    }

    public List<MenuDto> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<MenuDto> subMenus) {
        this.subMenus = subMenus;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "MenuDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", leaf=" + leaf +
                ", subMenus=" + subMenus +
                '}';
    }
}