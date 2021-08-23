package ru.netology;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryOrderPageTest {
    @BeforeEach
    void setUp(){
        open("http://localhost:9999");
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
        $("[data-test-id=agreement]").shouldHave(cssClass("inputinvalid"));

    }

}
