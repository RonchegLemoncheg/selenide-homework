import Util.Util;
import com.codeborne.selenide.SelenideElement;
import ge.tbcitacademy.data.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedSwoopTests2 extends BaseTest {
    private final String category;

    public ParametrizedSwoopTests2(String category) {
        this.category = category;
    }


    @Test
    public void rangeTest() {
        open(Constants.SWOOPLINK);
        $x(String.format("//a[p[text()='%s']]", category)).click();
        SelenideElement priceButton = $(".text-primary_green-100-value.bg-primary_green-10-value").scrollIntoView("{block: 'center'}");
        SelenideElement dan = $x("//p[text()='დან']/following-sibling::input");
        SelenideElement mde = $x("//p[text()='მდე']/following-sibling::input");
        dan.setValue(Constants.PRICEMIN);
        mde.setValue(Constants.PRICEMAX);
        priceButton.click();
        $x("//h2[text()='შეთავაზება არ მოიძებნა']").shouldNot(exist);
        List<Double> offersList = Util.getEveryOffer();
        List<Double> streamedList = offersList.stream().
                filter(offer -> offer >= Integer.parseInt(Constants.PRICEMIN) &&
                        offer <= Integer.parseInt(Constants.PRICEMAX)).toList();
        Assert.assertEquals(offersList.size(), streamedList.size());
    }

    @Test
    public void filterTest(){
        open(Constants.SWOOPLINK);
        $x(String.format("//a[p[text()='%s']]", category)).click();
        $x("//input[@id='radio-გადახდის ტიპი-1']").scrollIntoView("{block: 'center'}").click();
        SelenideElement mainDiv = $("div.grid-flow-row.gap-x-4.gap-y-8");
        mainDiv.$x("preceding::div//p[text()='სრული გადახდა']").should(exist);
        List<Double> offersList = Util.getEveryOffer();
        offersList.sort(Comparator.naturalOrder());
        Double leastExpensiveOfferPrice = Util.getLeastExpensiveOffer();
        Assert.assertEquals(offersList.get(0), leastExpensiveOfferPrice);
    }

}
