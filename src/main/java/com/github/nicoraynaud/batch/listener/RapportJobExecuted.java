package com.github.nicoraynaud.batch.listener;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RapportJobExecuted {

    @JsonProperty
    private int nbRead;
    @JsonProperty
    private int nbWrite;
    @JsonProperty
    private int nbSkip;
    @JsonProperty
    private long executionTime;

    public int getNbRead() {
        return nbRead;
    }

    /* package */ void setNbRead(int read) {
        nbRead = read;
    }

    public int getNbSkip() {
        return nbSkip;
    }

    /* package */ void setNbSkip(int nbSkip) {
        this.nbSkip = nbSkip;
    }

    public int getNbWrite() {
        return nbWrite;
    }

    /* package */ void setNbWrite(int nbWrite) {
        this.nbWrite = nbWrite;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    /* package */ void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
