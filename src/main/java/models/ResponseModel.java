package models;

import lombok.Data;

@Data
public class ResponseModel {
    private int statusCode;
    private String body;

    public ResponseModel(int status, String body) {
        this.statusCode = status;
        this.body = body;
    }
}