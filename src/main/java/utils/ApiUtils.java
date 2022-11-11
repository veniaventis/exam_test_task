package utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import lombok.SneakyThrows;
import models.ResponseModel;

public class ApiUtils {

        @SneakyThrows
        public static ResponseModel post(String requestPath) {
            HttpResponse<String> response = Unirest.post(requestPath).asString();
            Unirest.shutdown();
            return new ResponseModel(response.getStatus(),response.getBody());
        }
}
