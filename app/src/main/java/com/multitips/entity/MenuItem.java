package com.multitips.entity;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    private String menuName;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    private int menuId;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;

    public MenuItem(String menuName, int menuId, String icon) {
        this.menuName = menuName;
        this.menuId = menuId;
        this.icon = icon;
    }

    public static List<MenuItem> GetDataSource(){

        List<MenuItem> dataSource = new ArrayList<>();

        dataSource.add(new MenuItem("Special VIP",0,"icon_special_vip"));
        dataSource.add(new MenuItem("Fixed VIP",1,"icon_fixed_vip"));
        dataSource.add(new MenuItem("Correct Score VIP",2,"icon_correct_score_vip"));
        dataSource.add(new MenuItem("HT-FT VIP",3,"icon_ht_ft_vip"));
        dataSource.add(new MenuItem("Multi 50+ Odds VIP",4,"icon_multi50_vip"));
        dataSource.add(new MenuItem("Mixed VIP",5,"icon_mixed_vip"));

        dataSource.add(new MenuItem("Daily Sure Tips",6,"icon_daily_sure"));
        dataSource.add(new MenuItem("Football Tips",7,"icon_football"));
        dataSource.add(new MenuItem("Over-Under",8,"icon_over_under"));
        dataSource.add(new MenuItem("Single Tips",9,"icon_single"));
        dataSource.add(new MenuItem("Daily 20+ Odds",10,"icon_daily_20"));
        dataSource.add(new MenuItem("Bonus-Surprise",11,"icon_bonus_surprise"));
        dataSource.add(new MenuItem("Basketball",12,"icon_basketball"));
        dataSource.add(new MenuItem("Tennis",13,"icon_tennis"));

        return dataSource;
    }

    public static MenuItem GetByMenuId(int index){
        List<MenuItem> dataSource = MenuItem.GetDataSource();
        for (MenuItem menu: dataSource ) {
            if (menu.getMenuId() == index)
                return menu;
        }
        return null;
    }
}
