package com.github.nicoraynaud.batch.domain;

public class JobExecutionParameter {

    private String name;
    private String type;
    private String value;
    private String identifyCharacter;

    public JobExecutionParameter() {
    }

    public JobExecutionParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIdentifyCharacter() {
        return identifyCharacter;
    }

    public void setIdentifyCharacter(String identifyCharacter) {
        this.identifyCharacter = identifyCharacter;
    }
}
