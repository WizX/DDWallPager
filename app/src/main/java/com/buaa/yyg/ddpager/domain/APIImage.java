
package com.buaa.yyg.ddpager.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class APIImage {

    @SerializedName("_type")
    @Expose
    private String Type;
    @SerializedName("instrumentation")
    @Expose
    private Instrumentation instrumentation;
    @SerializedName("webSearchUrl")
    @Expose
    private String webSearchUrl;
    @SerializedName("totalEstimatedMatches")
    @Expose
    private int totalEstimatedMatches;
    @SerializedName("value")
    @Expose
    private List<Value> value = new ArrayList<Value>();
    @SerializedName("nextOffsetAddCount")
    @Expose
    private int nextOffsetAddCount;
    @SerializedName("displayShoppingSourcesBadges")
    @Expose
    private boolean displayShoppingSourcesBadges;
    @SerializedName("displayRecipeSourcesBadges")
    @Expose
    private boolean displayRecipeSourcesBadges;

    /**
     * 
     * @return
     *     The Type
     */
    public String getType() {
        return Type;
    }

    /**
     * 
     * @param Type
     *     The _type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * 
     * @return
     *     The instrumentation
     */
    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

    /**
     * 
     * @param instrumentation
     *     The instrumentation
     */
    public void setInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    /**
     * 
     * @return
     *     The webSearchUrl
     */
    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    /**
     * 
     * @param webSearchUrl
     *     The webSearchUrl
     */
    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

    /**
     * 
     * @return
     *     The totalEstimatedMatches
     */
    public int getTotalEstimatedMatches() {
        return totalEstimatedMatches;
    }

    /**
     * 
     * @param totalEstimatedMatches
     *     The totalEstimatedMatches
     */
    public void setTotalEstimatedMatches(int totalEstimatedMatches) {
        this.totalEstimatedMatches = totalEstimatedMatches;
    }

    /**
     * 
     * @return
     *     The value
     */
    public List<Value> getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(List<Value> value) {
        this.value = value;
    }

    /**
     * 
     * @return
     *     The nextOffsetAddCount
     */
    public int getNextOffsetAddCount() {
        return nextOffsetAddCount;
    }

    /**
     * 
     * @param nextOffsetAddCount
     *     The nextOffsetAddCount
     */
    public void setNextOffsetAddCount(int nextOffsetAddCount) {
        this.nextOffsetAddCount = nextOffsetAddCount;
    }

    /**
     * 
     * @return
     *     The displayShoppingSourcesBadges
     */
    public boolean isDisplayShoppingSourcesBadges() {
        return displayShoppingSourcesBadges;
    }

    /**
     * 
     * @param displayShoppingSourcesBadges
     *     The displayShoppingSourcesBadges
     */
    public void setDisplayShoppingSourcesBadges(boolean displayShoppingSourcesBadges) {
        this.displayShoppingSourcesBadges = displayShoppingSourcesBadges;
    }

    /**
     * 
     * @return
     *     The displayRecipeSourcesBadges
     */
    public boolean isDisplayRecipeSourcesBadges() {
        return displayRecipeSourcesBadges;
    }

    /**
     * 
     * @param displayRecipeSourcesBadges
     *     The displayRecipeSourcesBadges
     */
    public void setDisplayRecipeSourcesBadges(boolean displayRecipeSourcesBadges) {
        this.displayRecipeSourcesBadges = displayRecipeSourcesBadges;
    }

}
