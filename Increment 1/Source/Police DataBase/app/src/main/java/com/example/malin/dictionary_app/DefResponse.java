package com.example.malin.dictionary_app;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anitha on 21/9/16.
 */
public class DefResponse {

    @SerializedName("word")
    private String word;
    @SerializedName("definitions")
    private List<Definitions> definitions;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Definitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definitions> definitions) {
        this.definitions = definitions;
    }
}
