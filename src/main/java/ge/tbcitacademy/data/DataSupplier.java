package ge.tbcitacademy.data;

import org.testng.annotations.DataProvider;

public class DataSupplier {
    @DataProvider
    public static Object[][] dataSupplier(){
        return new Object[][] {
                {"კარტინგით რბოლა", 25},
                {"კარტინგი, ტენისი, ბილიარდი და მინი-გოლფი ლისი ლემანსისგან. ", 40},
                {"ფიტნესის აბონემენტი", 65},
                {"თხილამურზე სრიალის სასწავლო პროგრამა", 72},
                {"ულიმიტო აბონემენტი + ინსტრუქტორი", 49},
                {"ფიტნესის 1 თვიანი აბონიმენტი ქალბატონებისთვის", 50},
                {"საცხენოსნო ტურები და საცხენოსნო გაკვეთილები", 50},
                {"პადელის კორტი 1 საათით", 40},
                {"8 ვიზიტი პადელის ღია კორტზე 1 საათით 18:00 საათამდე", 255},
                {"დახურული აუზი წყნეთში ორშაბათი-პარასკევი", 20},
                {"ცხენებზე ჯირითი ან პონით გასეირნება", 40},
        };
    }

    @DataProvider
    public static Object[][] formsDataSupplier(){
        return new Object[][] {
                { "Roncheg", "Lemoncheg", "Male", "1231231231" },
                { "Gverberg", "Gvainus", "Male", "1451671490" },
                { "Jens", "Kidman", "Other", "1451231251" }
        };
    }
}
