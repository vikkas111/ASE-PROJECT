package com.example.malin.dictionary_app;

import com.google.gson.annotations.SerializedName;

public class Definitions {
    @SerializedName("definition")
    private String definition;
    @SerializedName("partOfSpeech")
    private String partOfSpeech;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}
