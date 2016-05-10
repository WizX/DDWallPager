
package com.buaa.yyg.baidupager.domain;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ShowapiResBody {

    private int currentPage;
    private Pagebean pagebean;
    private int retCode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
