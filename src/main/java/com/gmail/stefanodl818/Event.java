package com.gmail.stefanodl818;


public class Event {

    public Event(String id, int duration, String host, String type, Boolean alert){
        this.id=id;
        this.duration=duration;
        this.host=host;
        this.type=type;
        this.alert=false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    private String id;
    private int duration;
    private String host;
    private String type;
    private Boolean alert;
}
