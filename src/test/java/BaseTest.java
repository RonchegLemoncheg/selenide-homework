import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.*;

public class BaseTest {

    @BeforeClass
    @Parameters("browserType")
    public void setup(@Optional("firefox") String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                Configuration.browser = "chrome";
                break;
            case "firefox":
                Configuration.browser = "firefox";
                break;
            default:
                throw new IllegalArgumentException(Constants.BROWSERERROR);
        }
        Configuration.timeout = 10;
    }

    @AfterClass
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
