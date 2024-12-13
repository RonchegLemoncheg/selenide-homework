package Util;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.Double.NaN;

public class Util {

    public static SelenideElement getPageBar() {
        SelenideElement element = $("div.items-start.justify-center.gap-1");
        return element.exists() ? element : null;
    }


    public static Double getPrice(SelenideElement temp) {
        try {
            SelenideElement h4 = temp.$("h4.text-primary_black-100-value.text-2md");
            String text = h4.getText();
            String numericPart = text.replaceAll("[^\\d.]", "");
            return Double.parseDouble(numericPart);
        } catch (Exception ex) {
            return NaN;
        }

    }

    public static List<Double> getEveryOffer() {

        List<Double> offersList = new ArrayList<>();
        int lastPage = 1;
        SelenideElement pageBar = Util.getPageBar();
        if (pageBar != null) {
            SelenideElement lastDiv = pageBar.$("div:nth-last-child(2)");
            lastPage = Integer.parseInt(lastDiv.getText());
        }

        for (int i = 1; i <= lastPage; i++) {
            SelenideElement mainDiv = $("div.grid-flow-row.gap-x-4.gap-y-8");
            mainDiv.$("a.flex-col.gap-3.cursor-pointer").should(exist);
            ElementsCollection tempList = mainDiv.$$("a.flex-col.gap-3.cursor-pointer");
            List<Double> tempListInt = tempList.stream().map(Util::getPrice).toList();
            offersList.addAll(tempListInt);
            if (i != lastPage) {
                SelenideElement pageBarTemp = getPageBar().scrollTo();
                pageBarTemp.$x(".//div[text()='" + (i + 1) + "']").click();
            }
        }

        return offersList;

    }

    public static Double getLeastExpensiveOffer() {
        $("p.text-primary_black-100-value.cursor-pointer").scrollIntoView("{block: 'center'}").click();;

        SelenideElement mainFilter = $("div.absolute.top-10.right-0.w-56.shadow-sm");
        mainFilter.$x(".//p//p[text()='ფასით ზრდადი']").click();

        SelenideElement mainDiv = $("div.grid-flow-row.gap-x-4.gap-y-8");
        mainDiv.$("a.flex-col.gap-3.cursor-pointer").should(exist);
        SelenideElement smallestOffer = mainDiv.$("a.flex-col.gap-3.cursor-pointer");
        return getPrice(smallestOffer);
    }
}
