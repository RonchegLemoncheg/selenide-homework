import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbcitacademy.data.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTests2 extends BaseTest {

    @Test
    public void validateDemosDesign() {
        open(Constants.TELERIKLINK);
        SelenideElement mainDiv = $x("//h2[text()='Web']/following::div[contains(@class, 'row u-mb8')]");
        ElementsCollection columns = mainDiv.$$x(".//div[img]");
        columns.forEach(e -> {
            e.$x(".//img").scrollIntoView("{block: 'center'}").hover();
            if (e.$x("following-sibling::h3[text()='Kendo UI']").exists()) {
                e.$$x(".//a").findBy(Condition.text("UI for Vue Demos")).should(exist);
            }
            String color = e.getCssValue("background-color");
            Assert.assertEquals(color, Constants.PURPLECOLOR);
        });
        SelenideElement desktopDiv = $x("//h2[text()='Desktop']/following::div[contains(@class, 'row u-mb8')]");
        ElementsCollection desktopColumns = desktopDiv.$$x(".//div[div//img]");
        List<SelenideElement> filteredDesktopColumns = desktopColumns.stream().filter(e -> e.$x(".//img[@title='Get It from Microsoft']").exists()).toList();

        SelenideElement mobileDiv = $x("//h2[text()='Mobile']/following::div[contains(@class, 'row u-mb8')]");
        SelenideElement telerik = mobileDiv.$x(".//div[h3[text()='Telerik UI for Xamarin']]");
        telerik.$x(".//img[@title='Download on the App Store']").should(exist);
        telerik.$x(".//img[@title='Get it on Google Play']").should(exist);
        telerik.$x(".//img[@title='Get it from Microsoft']").should(exist);

        String posBeforeScrolling = ($("nav.NavAlt.NavAlt--blue.u-bn.u-tal.u-b0.is-fixed").getCssValue("position"));
        $x("//h2[text()='Web Content Management']").scrollTo();
        String posAfterScrolling = ($("nav.NavAlt.NavAlt--blue.u-bn.u-tal.u-b0.is-fixed").getCssValue("position"));
        Assert.assertEquals(posAfterScrolling, posBeforeScrolling);


        ElementsCollection sectionLinks1 = $$x("//div[@class='container']//a");

        // shemedzlo bolo testi amastan gameertebina magram isedac sakmaod didia da calke davwere
        sectionLinks1.forEach(element -> {
            element.shouldNotHave(cssClass("is-active"));
            element.shouldBe(visible);
            String beforeScrolling = element.getCssValue("background-color");
            String sectionName = element.getText();
            if (!sectionName.equals("Sitefinity CMS")) $x("//h2[text()='" + sectionName + "']").scrollTo();
            else $x("//h2[text()='Web Content Management']").scrollTo();
            // aq vamowmeb rom roca mag seqciaze gadadis, feri icvleba da klass is-active emateba
            element.shouldHave(cssClass("is-active"));
            element.shouldNotHave(cssValue("background-color", "rgba(0, 0, 0, 0)"));
            String afterScrolling = element.shouldNotHave(attribute("style", "background-color: rgba(0, 0, 0, 0);"))
                    .getCssValue("background-color");
            Assert.assertNotEquals(beforeScrolling, afterScrolling);
        });

        executeJavaScript("window.scrollTo(0, 0);");
        sectionLinks1.forEach(element -> {
            String sectionName;
            if (!element.getText().equals("Sitefinity CMS")) sectionName=element.getText();
            else sectionName="Web Content Management";
            element.scrollIntoView("{block: 'center'}").click();
            Assert.assertTrue($x("//h2[text()='"+sectionName+"']").isDisplayed());
        });
    }

    @Test
    public void validateOrderMechanics(){
        open(Constants.TELERIKLINK);
        $x("//a[text()='Pricing']").parent().shouldBe(visible).click();
        SelenideElement button = $x("//th[contains(@class, 'Ultimate') and @colspan='1']//a[text()='Buy now']");
        String price = button.$x("preceding::th[@class='Ultimate']//h3").getText().replace(" ", "")+".00";
        button.scrollIntoView("{block: 'center'}").click();
        $x("//h6[text()='Sign up or Log in ']").parent().$x("i").should(exist).shouldBe(visible).click();
        String newPrice = $("span.td-cell span").getText();
        Assert.assertEquals(newPrice,price);
        float priceOfOne = Float.parseFloat(price.replace("$", "").replace(",", ""));



        // amaze sakmaod bevri viwvale
        SelenideElement dropdown = $("kendo-dropdownlist");
        dropdown.click();
        $(".k-list-content").$$("li").find(Condition.text("2")).click();
        double priceOfTwo = Double.parseDouble($("span.td-cell span")
                .shouldNotHave(text(price))
                .getText().replace("$", "").replace(",", ""))*2;
        double totalPrice = Double.parseDouble($("div.e2e-cart-item-subtotal")
                .shouldNotHave(text(newPrice))
                .getText().replace("$", "").replace(",", ""));

        // es meore taskia, Increase the term, validate that the price is added correctly according to its percentage.
        // 5 procentit unda daiklos, magito vamravleb 0.95ze oris fass
        Assert.assertEquals(Math.round(totalPrice), Math.round((priceOfOne*0.95)*2));

        //Increase the Quantity and validate that the saving is displayed correctly according to the discount.
        float priceOfOneDiscount = Float.parseFloat($("span.td-cell div").getText().replace("Save $", ""));
        Assert.assertEquals(Math.round(priceOfOneDiscount),Math.round(priceOfOne*0.05));

        //Validate SubTotal value.
        Assert.assertEquals(priceOfTwo,totalPrice);

        //5) Validate Total Discounts - hover over the question mark and validate that each discount is displayed correctly.
        $("div.e2e-total-discounts-label").scrollIntoView("{block: 'center'}").hover();
        double discount = Double.parseDouble($("div.tooltip-info.td-tooltip")
                .shouldBe(visible).$("div.u-fr").getText()
                .replace("- $", ""));
        double discountOther = Double.parseDouble($("span.e2e-total-discounts-price")
                .shouldBe(visible).getText()
                .replace("- $", ""));
        Assert.assertEquals(discountOther,discount);

        double totalPriceOther = Double.parseDouble($("h4.e2e-total-price")
                .shouldBe(visible).getText()
                .replace("US $", "").replace("," , ""));

        //5) Validate Total value
        Assert.assertEquals(totalPriceOther,totalPrice);
        $("button.loader-button").scrollIntoView("{block: 'center'}").click();

        $("#biFirstName").setValue(Constants.USERNAME);
        $("#biLastName").setValue(Constants.LASTNAME);
        $("#biEmail").setValue(Constants.USEREMAIL);
        $("#biCompany").setValue(Constants.COMPANYNAME);
        $("#biPhone").setValue(Constants.PHONENUMBER);
        $("#biAddress").setValue(Constants.CURRENTADDRESS);
        $("input.k-input-inner").setValue(Constants.COUNTRY);
        $("#biCity").setValue(Constants.CITY);
        $("#biZipCode").setValue(Constants.ZIPCODE);
        $("a.loader-button").scrollIntoView("{block: 'center'}").click();
        $("button.loader-button").scrollIntoView("{block: 'center'}").click();

        Assert.assertEquals($("#biFirstName").getValue(), Constants.EMPTYSTRING);
        Assert.assertEquals($("#biLastName").getValue(), Constants.EMPTYSTRING);
        Assert.assertEquals($("#biEmail").getValue(), Constants.EMPTYSTRING);
        Assert.assertEquals($("#biCompany").getValue(), Constants.EMPTYSTRING);
        Assert.assertEquals($("#biPhone").getValue(), Constants.EMPTYSTRING);
        Assert.assertEquals($("#biAddress").getValue(), Constants.EMPTYSTRING);
        Assert.assertEquals($("input.k-input-inner").getValue(), Constants.EMPTYSTRING);
        Assert.assertEquals($("#biCity").getValue(), Constants.EMPTYSTRING);
        Assert.assertEquals($("#biZipCode").getValue(), Constants.EMPTYSTRING);
    }

    @Test
    public void chainedLocatorsTest(){
        open(Constants.BOOKSLINK);
        ElementsCollection books = $$("div.rt-tr-group")
                .filter(text("O'Reilly Media"))
                .filter(text("Javascript"));
        books.forEach(book ->
            book.$("img").shouldHave(attribute("src"))
        );
    }


    @Test
    public void softAssertTest(){
        open(Constants.BOOKSLINK);
        ElementsCollection books = $$("div.rt-tr-group")
                .filter(text("O'Reilly Media"))
                .filter(text("Javascript"));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(books.size(), 10);
        softAssert.assertEquals(books.get(0).$("span.mr-2").getText(),Constants.BOOKNAME);
        softAssert.assertAll();

    }
}
