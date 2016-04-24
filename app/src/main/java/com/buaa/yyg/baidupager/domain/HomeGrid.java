package com.buaa.yyg.baidupager.domain;

/**
 * GridView数据实体类
 * Created by yyg on 2016/4/24.
 */
public class HomeGrid {

    /**
     * 当我们开发的时候，这里应该是个接口，那应该是String类型
     * 我们现在模拟的是本地的图片，所以是int
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
