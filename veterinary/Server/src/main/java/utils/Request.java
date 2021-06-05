package utils;

import java.io.Serializable;

public class Request implements Serializable {

    private String action;
    private String senderType = "user";
    private String jsonData;

    public Request() {
    }

    public Request(String action, String jsonData) {
        this.action = action;
        this.jsonData = jsonData;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    @Override
    public String toString() {
        return "utils.Request{" +
                "action='" + action + '\'' +
                ", jsonData='" + jsonData + '\'' +
                '}';
    }
}