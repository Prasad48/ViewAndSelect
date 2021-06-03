package com.bhavaniprasad.viewandselect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class features implements Serializable {

    @SerializedName("feature_id")
    private String feature_id;

    @SerializedName("name")
    private String name;

    @SerializedName("features")
    private List<features> features;

    public List<com.bhavaniprasad.viewandselect.model.features> getFeatures() {
        return features;
    }

    @SerializedName("options")
    private List<OptionsList> options;

    @SerializedName("exclusions")
    private List<List<exclusionsList>> exclusions;

    public List<List<exclusionsList>> getExclusions() {
        return exclusions;
    }

    public String getFeature_id() {
        return feature_id;
    }

    public String getName() {
        return name;
    }



    public List<OptionsList> getOptions() {
        return options;
    }


}


