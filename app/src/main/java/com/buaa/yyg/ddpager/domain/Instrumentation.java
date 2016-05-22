
package com.buaa.yyg.ddpager.domain;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Instrumentation {

    @SerializedName("pageLoadPingUrl")
    @Expose
    private String pageLoadPingUrl;

    /**
     * 
     * @return
     *     The pageLoadPingUrl
     */
    public String getPageLoadPingUrl() {
        return pageLoadPingUrl;
    }

    /**
     * 
     * @param pageLoadPingUrl
     *     The pageLoadPingUrl
     */
    public void setPageLoadPingUrl(String pageLoadPingUrl) {
        this.pageLoadPingUrl = pageLoadPingUrl;
    }

}
