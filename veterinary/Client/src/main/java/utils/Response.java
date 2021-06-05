package utils;


import java.io.Serializable;

public class Response implements Serializable {

    private String action;
    private String status;
    private String jsonData;


    public Response() {
    }

    public Response(String action, String jsonData) {
        this.action = action;
        this.jsonData = jsonData;
    }

    public Response(String action, String status, String jsonData) {
        this.action = action;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "utils.Request{" +
                "action='" + action + '\'' +
                ", jsonData='" + jsonData + '\'' +
                '}';
    }

}
