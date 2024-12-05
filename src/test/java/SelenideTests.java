import com.codeborne.selenide.*;
import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SelenideTests extends BaseTest{


    @Test
    public void validateBundleOffers() {
        open(Constants.TELERIKLINK);
        getWebDriver().manage().window().maximize();
        $x("//a[text()='Pricing']").parent().shouldBe(visible, Duration.ofSeconds(10)).click();

        $$x("//th[contains(@class, 'UI') and @colspan='1']")
                .forEach(th -> th.shouldNotHave(Condition.text(Constants.MOCKINGTEXT)));

        $$x("//th[not(contains(@class, 'Ultimate')) and @colspan='1']")
                .forEach(th -> th.shouldNotHave(Condition.text(Constants.ESCALATIONSTRING)));

        $$x("//th[not(contains(@class, 'Ultimate')) and @colspan='1']")
                .forEach(th -> th.shouldNotHave(Condition.text(Constants.MANAGEMENTTEXT)));

        $$x("//th[not(contains(@class, 'Ultimate')) and @colspan='1']")
                .forEach(th -> th.shouldNotHave(Condition.text(Constants.TESTSTUDIOTEXT)));

        SelenideElement td = $x("//td[text()='Kendo UI for jQuery']");
        int dotCount = td.parent().$$("span.dot").size();
        Assert.assertEquals(dotCount, 3);

        SelenideElement td2 = $x("//td[p[contains(text(), 'Telerik Report Server')]]");
        SelenideElement devCraft = td2.parent().$$("td").last();
        Selenide.executeJavaScript("arguments[0].scrollIntoView({block: 'center'});", $("button.u-fs20"));
        $("button.u-fs20").click();
        devCraft.$("span").shouldHave(Condition.text(Constants.INSTANCETEXT));

        SelenideElement td3 = $x("//td[p[contains(text(), 'Telerik Reporting')]]");
        td3.parent().$$("td").last(2).forEach( e->e.$("span.dot").should(Condition.exist));

        SelenideElement td4 = $x("//td[text()='Access to on-demand videos']");
        int dotCount1 = td4.parent().$$("span.dot").size();
        Assert.assertEquals(dotCount1, 3);

        // aq tokos idea momewona, am sticky elements vnaxulob, cotas vsqrolav da vamowmeb rom pozicia igive aqvs
        SelenideElement stickyOfferDiv = $("#js-sticky-head").scrollTo();
        stickyOfferDiv.shouldBe(Condition.visible);
        stickyOfferDiv.shouldHave(cssClass("is-fixed"));
        String position = stickyOfferDiv.getAttribute("style");
        Selenide.executeJavaScript("window.scrollBy(0, 500)");
        Assert.assertEquals(position, stickyOfferDiv.getAttribute("style"));
    }


    @Test
    public void validateIndividualOffers(){
        // es mushaobs firefoxze ubralod xandaxan testngdan rom vushveb ishviatad errors agdebs tyuila
        open(Constants.TELERIKLINK);
        getWebDriver().manage().window().maximize();
        $x("//a[text()='Pricing']").parent().shouldBe(visible, Duration.ofSeconds(10)).click();
        $x("//h2[span[text()='Individual Products']]").parent().shouldBe(visible, Duration.ofSeconds(10)).click();
        $x("//h2[span[text()='Individual Products']]").parent().shouldBe(visible, Duration.ofSeconds(10)).scrollTo();

        SelenideElement firstOffer = $x("//div[@data-opti-expid='Kendo UI']");
        SelenideElement secondOffer = $x("//div[@data-opti-expid='KendoReact']");

        (firstOffer.$("h3").shouldBe(visible, Duration.ofSeconds(10))).hover();
        SelenideElement ninjaImage = $x("//img[@title='Kendo Ui Ninja']");
        ninjaImage.shouldBe(Condition.visible, Duration.ofSeconds(1));


        (secondOffer.$("h3").shouldBe(visible, Duration.ofSeconds(10))).hover();
        SelenideElement ninjaImage2 = $x("//img[@title='Kendo React Ninja']");
        ninjaImage2.shouldBe(Condition.visible, Duration.ofSeconds(1));

        SelenideElement dropdown1 = firstOffer.$("div.Dropdown");
        dropdown1.scrollTo();
        dropdown1.$("a").shouldHave(text(Constants.PRIORITYSUPPORT));

        SelenideElement dropdown2 = secondOffer.$("div.Dropdown");
        dropdown2.$("a").shouldHave(text(Constants.PRIORITYSUPPORT));

        dropdown1.parent().$("h2").$x(".//span[@class='js-price']").shouldHave(text(Constants.JSPRICE1));
        dropdown2.parent().$("h2").$x(".//span[@class='js-price']").shouldHave(text(Constants.JSPRICE2));

    }

    @Test
    public void checkBoxTest(){
        open(Constants.CHECKBOXLINK);
        getWebDriver().manage().window().maximize();

        $$("input[type='checkbox']").first().setSelected(true);
        $$("input[type='checkbox']").first().shouldBe(checked);

        $$("input[type='checkbox']").forEach(checkbox ->
            checkbox.shouldHave(Condition.attribute("type", "checkbox"))
        );
    }


    @Test
    public void dropDownTest(){
        open(Constants.DROPDOWNTESTLINK);
        getWebDriver().manage().window().maximize();
        $("#dropdown").getSelectedOption().shouldHave(text(Constants.PLEASESELECT));
        $("#dropdown").selectOption(Constants.OPTION2TEXT);
        $("#dropdown").getSelectedOption().shouldHave(text(Constants.OPTION2TEXT));
    }


    @Test
    public void collectionsTest(){
        Configuration.browser = "firefox";
        open(Constants.COLLECTIONSTESTLINK);
        getWebDriver().manage().window().maximize();
        $("#userName").setValue(Constants.USERNAME);
        $("#userEmail").setValue(Constants.USEREMAIL);
        $("#currentAddress").setValue(Constants.CURRENTADDRESS);
        $("#permanentAddress").setValue(Constants.PERMANENTADDRESS);
        $("#submit").scrollTo();
        $("#submit").click();
        ElementsCollection results = $$x("//div[@id='output']//div//p");
        results.shouldHave(texts(
                Constants.USERNAME,
                Constants.USEREMAIL,
                Constants.CURRENTADDRESS,
                Constants.PERMANENTADDRESS));

    }
}
