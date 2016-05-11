
package com.buaa.yyg.baidupager.domain;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ShowapiResBody {

    @SerializedName("currentPage")
    @Expose
    private int currentPage;
    @SerializedName("pagebean")
    @Expose
    private Pagebean pagebean;
    @SerializedName("ret_code")
    @Expose
    private int retCode;

    /**
     * 
     * @return
     *     The currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 
     * @param currentPage
     *     The currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 
     * @return
     *     The pagebean
     */
    public Pagebean getPagebean() {
        return pagebean;
    }

    /**
     * 
     * @param pagebean
     *     The pagebean
     */
    public void setPagebean(Pagebean pagebean) {
        this.pagebean = pagebean;
    }

    /**
     * 
     * @return
     *     The retCode
     */
    public int getRetCode() {
        return retCode;
    }

    /**
     * 
     * @param retCode
     *     The ret_code
     */
    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

}
