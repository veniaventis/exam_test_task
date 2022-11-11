package api;

import constants.ApiConstant;
import utils.ApiUtils;
import utils.ConfigUtil;

public class ApiRequest {

    public static String getToken(String variant) {
        return ApiUtils.post(String.format(ConfigUtil.getSettingsData("apiUrl"), ApiConstant.GET_VARIANT_TOKEN, variant)).getBody();
    }
}
