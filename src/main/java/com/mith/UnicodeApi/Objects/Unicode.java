package com.mith.UnicodeApi.Objects;

public class Unicode {

    private final String unicodeCharacter;
    private final String name;
    private final Enum<UnicodeType> type;
    private Integer customModel;
    private final boolean restricted;

    public Unicode(String unicodeCharacter, UnicodeType type, String name, boolean restricted) {
        this.unicodeCharacter = unicodeCharacter;
        this.name = name;
        this.type = type;
        this.restricted = restricted;
    }
    public boolean getRestricted(){return this.restricted;}
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
