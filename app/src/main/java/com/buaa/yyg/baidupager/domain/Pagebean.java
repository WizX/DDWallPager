
package com.buaa.yyg.baidupager.domain;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Pagebean {

    @SerializedName("contentlist")
    @Expose
    private List<Contentlist> contentlist = new ArrayList<Contentlist>();
    @SerializedName("maxResult")
    @Expose
    private String maxResult;

    /**
     * 
     * @return
     *     The contentlist
     */
    public List<Contentlist> getContentlist() {
        return contentlist;
    }

    /**
     * 
     * @param contentlist
     *     The contentlist
     */
    public void setContentlist(List<Contentlist> contentlist) {
        this.contentlist = contentlist;
    }

    /**
     * 
     * @return
     *     The maxResult
     */
    public String getMaxResult() {
        return maxResult;
    }

    /**
     * 
     * @param maxResult
     *     The maxResult
     */
    public void setMaxResult(String maxResult) {
        this.maxResult = maxResult;
    }

}
