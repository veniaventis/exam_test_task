package queries;

import constants.ApiConstant;
import models.ResponseModel;
import utils.ApiUtils;
import utils.ConfigUtil;

public class ApiRequest {
    public static ResponseModel RESPONSE_JSON = null;

    public static String getToken(String variant) {
        RESPONSE_JSON = ApiUtils.post(String.format(ConfigUtil.getSettingsData("apiUrl"), String.format(ApiConstant.GET_VARIANT_TOKEN, variant)));
        return RESPONSE_JSON.getBody();
    }
}
