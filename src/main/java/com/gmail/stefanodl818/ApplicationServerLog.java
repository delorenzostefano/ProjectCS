package com.gmail.stefanodl818;

public class ApplicationServerLog extends Log{

    public ApplicationServerLog(String id, State state, int timestamp, String type, String host){
        super(id, state, timestamp);
        this.type=type;
        this.host=host;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    private String type;
    private String host;
}
