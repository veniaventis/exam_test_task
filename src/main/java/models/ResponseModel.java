package models;

import com.mashape.unirest.http.JsonNode;
import lombok.Data;

@Data
public class ResponseModel {
        private int statusCode;
        private String body;

        public ResponseModel(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }
}