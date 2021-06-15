package com.mith.UnicodeApi.Objects;

public class Unicode {

    private final String unicodeCharacter;
    private final String name;
    private final Enum<UnicodeType> type;
    private Integer customModel;

    public Unicode(String unicodeCharacter, UnicodeType type, String name) {
        this.unicodeCharacter = unicodeCharacter;
        this.name = name;
        this.type = type;
    }

    public String getUnicodeCharacter() {
        return this.unicodeCharacter;
    }
    public String getName() {
        return this.name;
    }
    public Enum<UnicodeType> getType() {
        return this.type;
    }
    public boolean hasCustomModelData() {
        return this.customModel != null;
    }
    public int getCustomModelData() {
        return this.customModel;
    }
    public void setCustomModelData(int cmd){
        this.customModel = cmd;
    }
}
