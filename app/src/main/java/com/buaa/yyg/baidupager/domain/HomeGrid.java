package com.buaa.yyg.baidupager.domain;

/**
 * GridView数据实体类
 * Created by yyg on 2016/4/24.
 */
public class HomeGrid {

    /**
     * 设置首页图片和文字，使用本地图片较好，或者使用一条简易json
     */

    public HomeGrid(){
        super();
    }

    public HomeGrid(String type, int img) {
        this.type = type;
        this.img = img;
    }

    private int img;
    //描述
    private String type;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HomeGrid{" +
                "img=" + img +
                ", type='" + type + '\'' +
                '}';
    }

}
