import api.ApiRequest;
import aquality.selenium.core.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.ConfigUtil;

public class UnionUIDbTest extends BaseTest {
    private final String variant = ConfigUtil.getTestData("variant");
    private final SoftAssert softAssert = new SoftAssert();
    @Test
    public void uiDbTest() {
        Logger.getInstance().info("Getting variant token");
        String token = ApiRequest.getToken(variant);
        softAssert.assertNotNull(token,"Failed to get token");

        Assert.assertTrue();

    }

}
