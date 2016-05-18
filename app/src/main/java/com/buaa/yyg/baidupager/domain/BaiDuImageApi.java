
package com.buaa.yyg.baidupager.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class BaiDuImageApi {

    @SerializedName("col")
    @Expose
    private String col;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("tag3")
    @Expose
    private String tag3;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("totalNum")
    @Expose
    private int totalNum;
    @SerializedName("startIndex")
    @Expose
    private int startIndex;
    @SerializedName("returnNumber")
    @Expose
    private int returnNumber;
    @SerializedName("imgs")
    @Expose
    private List<Img> imgs = new ArrayList<Img>();

    /**
     * 
     * @return
     *     The col
     */
    public String getCol() {
        return col;
    }

    /**
     * 
     * @param col
     *     The col
     */
    public void setCol(String col) {
        this.col = col;
    }

    /**
     * 
     * @return
     *     The tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * 
     * @param tag
     *     The tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 
     * @return
     *     The tag3
     */
    public String getTag3() {
        return tag3;
    }

    /**
     * 
     * @param tag3
     *     The tag3
     */
    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    /**
     * 
     * @return
     *     The sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * 
     * @param sort
     *     The sort
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * 
     * @return
     *     The totalNum
     */
    public int getTotalNum() {
        return totalNum;
    }

    /**
     * 
     * @param totalNum
     *     The totalNum
     */
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * 
     * @return
     *     The startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * 
     * @param startIndex
     *     The startIndex
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * 
     * @return
     *     The returnNumber
     */
    public int getReturnNumber() {
        return returnNumber;
    }

    /**
     * 
     * @param returnNumber
     *     The returnNumber
     */
    public void setReturnNumber(int returnNumber) {
        this.returnNumber = returnNumber;
    }

    /**
     * 
     * @return
     *     The imgs
     */
    public List<Img> getImgs() {
        return imgs;
    }

    /**
     * 
     * @param imgs
     *     The imgs
     */
    public void setImgs(List<Img> imgs) {
        this.imgs = imgs;
    }

}
