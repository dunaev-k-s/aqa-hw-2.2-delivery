package ru.netology;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryOrderPageTest {
    @BeforeEach
    void setUp(){
        open("http://localhost:9999");
    }

    @Test
    void shouldSendValidData() {
        $("[data-test-id=city] [class='input__control']").setValue("Волгоград");
        $("[data-test-id=name] [class='input__control']").setValue("Борис Ельцин");
        $("[data-test-id=phone] [class='input__control']").setValue("+79999999999");
        $("[class=checkbox__box]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification] [class=notification__content]")
                .shouldHave(exactText("Встреча успешно забронирована на " +
                        element("[data-test-id=date] input")
                                .getAttribute("value")), Duration.ofSeconds(11));
    }

    @Test
    void shouldDefaultTimeBeRight() {
        assertEquals(LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                element("[data-test-id=date] input")
                .getAttribute("value"));
    }

    @Test
    void shouldNotValidateEmptyCity(){
        $("[data-test-id=city] [class='input__control']").setValue(null);
        $("[data-test-id=name] [class='input__control']").setValue("Борис Ельцин");
        $("[data-test-id=phone] [class='input__control']").setValue("+79999999999");
        $("[class=checkbox__box]").click();
        $(byText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotValidateEmptyName(){
        $("[data-test-id=city] [class='input__control']").setValue("Москва");
        $("[data-test-id=name] [class='input__control']").setValue(null);
        $("[data-test-id=phone] [class='input__control']").setValue("+79999999999");
        $("[class=checkbox__box]").click();
        $(byText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotSendEmptyDate(){
        $("[data-test-id=city] [class='input__control']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL+"a",Keys.DELETE);
        $("[data-test-id=name] [class='input__control']").setValue("Борис Ельцин");
        $("[data-test-id=phone] [class='input__control']").setValue("+79999999999");
        $("[class=checkbox__box]").click();
        $(byText("Забронировать")).click();
        $(byText("Неверно введена дата")).shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldNotValidateEmptyPhone(){
        $("[data-test-id=city] [class='input__control']").setValue("Москва");
        $("[data-test-id=name] [class='input__control']").setValue("Борис Ельцин");
        $("[class=checkbox__box]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone] [class=input__sub]").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotValidateEmptyCheckbox(){
        $("[data-test-id=city] [class='input__control']").setValue("Москва");
        $("[data-test-id=name] [class='input__control']").setValue("Борис Ельцин");
        $("[data-test-id=phone] [class='input__control']").setValue("+79999999999");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));

    }



    @Test
    void shouldNotValidateNonRussianName(){
        $("[data-test-id=city] [class='input__control']").setValue("Москва");
        $("[data-test-id=name] [class='input__control']").setValue("Boris Yeltsin");
        $("[data-test-id=phone] [class='input__control']").setValue("+79999999999");
        $("[class=checkbox__box]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name]").$(byClassName("input__sub"))
                .shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                        "Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldNotValidateWrongCity(){
        $("[data-test-id=city] [class='input__control']").setValue("Караганда");
        $("[data-test-id=name] [class='input__control']").setValue("Борис Ельцин");
        $("[data-test-id=phone] [class='input__control']").setValue("+79999999999");
        $("[class=checkbox__box]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city]").$(byClassName("input__sub"))
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldNotValidateWrongDate(){
        $("[data-test-id=city] [class='input__control']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE + "0");
        $("[data-test-id=name] [class='input__control']").setValue("Борис Ельцин");
        $("[data-test-id=phone] [class='input__control']").setValue("+79999999999");
        $("[class=checkbox__box]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date]").$(byClassName("input__sub"))
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldSendValidDataByUsingPopup(){
        $("[data-test-id=city] [class='input__control']").setValue("Ек");
        $(byClassName("menu")).$(byText("Екатеринбург")).click();
        $("[data-test-id=date]").click();
        $(byClassName("calendar")).sendKeys(Keys.RIGHT,Keys.RIGHT,Keys.RIGHT,Keys.RIGHT,Keys.ENTER);
        $("[data-test-id=name] [class='input__control']").setValue("Борис Ельцин");
        $("[data-test-id=phone] [class='input__control']").setValue("+79999999999");
        $("[class=checkbox__box]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification] [class=notification__content]")
                .shouldHave(exactText("Встреча успешно збронирована на " +
                        element("[data-test-id=date] input")
                                .getAttribute("value")), Duration.ofSeconds(11));

    }
}
