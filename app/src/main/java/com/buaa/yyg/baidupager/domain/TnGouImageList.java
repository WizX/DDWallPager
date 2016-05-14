
package com.buaa.yyg.baidupager.domain;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TnGouImageList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("tngou")
    @Expose
    private List<Tngou> tngou = new ArrayList<Tngou>();

    /**
     * 
     * @return
     *     The status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The total
     */
    public int getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The tngou
     */
    public List<Tngou> getTngou() {
        return tngou;
    }

    /**
     * 
     * @param tngou
     *     The tngou
     */
    public void setTngou(List<Tngou> tngou) {
        this.tngou = tngou;
    }

}
