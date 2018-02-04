package com.itibo.database;


public class TrackerInfo {
    private Integer idTrackers;
    private String name;

    public Integer getIdTrackers() {
        return idTrackers;
    }

    public void setIdTrackers(Integer idTrackers) {
        this.idTrackers = idTrackers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
