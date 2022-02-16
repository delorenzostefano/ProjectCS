package com.gmail.stefanodl818;

public class Log {

    public Log(String id, State state, int timestamp){
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public State getState() {
        return state;
    }

    public String getId() {
        return id;
    }

    public enum State{
        STARTED,
        FINISHED
    }

    private String id;
    private State state;
    private long timestamp;
}
