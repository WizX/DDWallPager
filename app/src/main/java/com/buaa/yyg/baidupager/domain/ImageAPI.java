
package com.buaa.yyg.baidupager.domain;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ImageAPI {

    private int showapiResCode;
    private String showapiResError;
    private ShowapiResBody showapiResBody;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The showapiResCode
     */
    public int getShowapiResCode() {
        return showapiResCode;
    }

    /**
     * 
     * @param showapiResCode
     *     The showapi_res_code
     */
    public void setShowapiResCode(int showapiResCode) {
        this.showapiResCode = showapiResCode;
    }

    /**
     * 
     * @return
     *     The showapiResError
     */
    public String getShowapiResError() {
        return showapiResError;
    }

    /**
     * 
     * @param showapiResError
     *     The showapi_res_error
     */
    public void setShowapiResError(String showapiResError) {
        this.showapiResError = showapiResError;
    }

    /**
     * 
     * @return
     *     The showapiResBody
     */
    public ShowapiResBody getShowapiResBody() {
        return showapiResBody;
    }

    /**
     * 
     * @param showapiResBody
     *     The showapi_res_body
     */
    public void setShowapiResBody(ShowapiResBody showapiResBody) {
        this.showapiResBody = showapiResBody;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
