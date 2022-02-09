package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataGenerator;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldCheckReappointment() {
        String planningDate = DataGenerator.getPlanningDate(4);
        DataGenerator.UserInfo userInfo = DataGenerator.Registration.getUserInfo("ru");

        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue(userInfo.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue(userInfo.getName());
        $("[data-test-id=phone] input").setValue(userInfo.getPhone());
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $$("[class=notification__content]").get(0).shouldBe(visible, Duration.ofMillis(14000))
                .shouldHave(exactText("Встреча успешно запланирована на " + planningDate));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        planningDate = DataGenerator.getPlanningDate(5);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[class=button__text]").click();
        $$("[class=notification__content]").get(1).shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(text("У вас уже запланирована встреча на другую дату."))
                .find("[class=button__text]").shouldBe(visible, Duration.ofMillis(15000)).click();
        $$("[class=notification__content]").get(0).shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Встреча успешно запланирована на " + planningDate));
    }
}
