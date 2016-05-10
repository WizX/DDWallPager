
package com.buaa.yyg.baidupager.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Pagebean {

    private List<Contentlist> contentlist = new ArrayList<Contentlist>();
    private String maxResult;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
