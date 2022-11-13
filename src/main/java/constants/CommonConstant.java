package constants;

import utils.ConfigUtil;

public final class CommonConstant {
    public static final int RANDOM_OBJ_LENGTH = 10;
    public static final String TOKEN_COOKIE_PARAM = "token";
    public static final String TEST_SCREENSHOT_PATH = "target/screenshots/screenshot.png";
    public static final String TEST_LOG_PATH = "src/main/resources/log.txt";
    public static final String IMAGE_HREF_REPLACEMENT = "data:image/png;base64,";
    public static final String IMAGE_PATH = "src/main/resources";
    public static final String START_TIME_UI_TEXT_REPLACEMENT = "Start time: ";
    public static final String END_TIME_UI_TEXT_REPLACEMENT = "End time: ";
    public static final String ADD_PROJECT_IFRAME_ID = "addProjectFrame";
    public static final String PROJECT_TESTS_WITHOUT_ID_URL = String.format(ConfigUtil.getSettingsData("uiUrl"), ConfigUtil.getConfidentialData("uiLogin"), ConfigUtil.getConfidentialData("uiPassword")) + "allTests?projectId=";
}

