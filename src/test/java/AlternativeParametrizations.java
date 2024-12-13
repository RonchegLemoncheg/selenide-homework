import ge.tbcitacademy.data.Constants;
import ge.tbcitacademy.data.DataSupplier;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class AlternativeParametrizations extends BaseTest {

    @Test
    @Parameters({"firstName", "lastName", "gender", "mobileNumber"})
    public void testUsingParameters(String firstName, String lastName, String gender, String mobileNumber) {
        open(Constants.FORMSLINK);
        $("#firstName").scrollIntoView("{block: 'center'}").setValue(firstName);
        $("#lastName").scrollIntoView("{block: 'center'}").setValue(lastName);
        $x(String.format("//label[text()='%s']", gender)).scrollIntoView("{block: 'center'}").click();
        $("#userNumber").scrollIntoView("{block: 'center'}").setValue(mobileNumber);
        $("#submit").scrollIntoView("{block: 'center'}").click();
        String realName = firstName + " " + lastName;
        $x("//td[text()='Student Name']//following-sibling::td").should(exist).shouldHave(text(realName));
    }



    @Test(dataProvider = "formsDataSupplier", dataProviderClass = DataSupplier.class)
    public void testUsingDataProvider(String firstName, String lastName, String gender, String mobileNumber) {
        open(Constants.FORMSLINK);
        $("#firstName").scrollIntoView("{block: 'center'}").setValue(firstName);
        $("#lastName").scrollIntoView("{block: 'center'}").setValue(lastName);
        $x(String.format("//label[text()='%s']", gender)).scrollIntoView("{block: 'center'}").click();
        $("#userNumber").scrollIntoView("{block: 'center'}").setValue(mobileNumber);
        $("#submit").scrollIntoView("{block: 'center'}").click();
        String realName = firstName + " " + lastName;
        $x("//td[text()='Student Name']//following-sibling::td").should(exist).shouldHave(text(realName));
    }
}
