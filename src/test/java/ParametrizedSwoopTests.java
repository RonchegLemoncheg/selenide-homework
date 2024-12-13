import com.codeborne.selenide.*;
import ge.tbcitacademy.data.Constants;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;
import ge.tbcitacademy.data.DataSupplier;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
public class ParametrizedSwoopTests extends BaseTest{

    @Test(dataProvider = "dataSupplier", dataProviderClass = DataSupplier.class)
    public void checkSaleValuesTest(String offerName, int offerPrice){
        open(Constants.SWOOPSPORTSLINK);
//        amas didixani undeboda chemi interneti
//        open("https://swoop.ge");
//        $x("//a[p[text()='სპორტი ']]").click();
        SelenideElement mainOffer = $x(String.format("//h4[text()='%s']", offerName)).parent().parent();
        double fullPrice = Double.parseDouble(mainOffer.$x(".//h4[@type='h4' and @weight='regular']").getText().replace("₾",""));
        double percentage = Double.parseDouble(mainOffer.$x(".//p[@weight='bold']").getText().replace("-","").replace("%",""));
        double tempOfferPrice = fullPrice*(1-(percentage/100));
        Assert.assertEquals(Math.round(tempOfferPrice),offerPrice);
    }


    @Test(dataProvider = "dataSupplier", dataProviderClass = DataSupplier.class)
    public void validateCartBehavior(String offerName,int temp){
        open(Constants.SWOOPSPORTSLINK);
        $x(String.format("//h4[text()='%s']", offerName)).scrollIntoView("{block: 'center'}").click();
        $x("//p[text()='კალათაში დამატება']").scrollIntoView("{block: 'center'}").click();
        $x("//img[@src='/icons/basket-black.svg']").should(exist).scrollIntoView("{block: 'center'}").click();
        SelenideElement mainDiv = $x("//div[normalize-space(@class)='flex justify-start items-start gap-6 flex-col']");
        mainDiv.shouldHave(Condition.text(offerName));
    }
}
