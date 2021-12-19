package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderTest {

    @Test
    void shouldSendSuccessfulData() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        //element.sendKeys(Keys.CONTROL+"a") - выделяет element
        // .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "Your Value") - выделяет элемент,
        //      удаляет его, вставляет нужное значение
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "25.12.2021");
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на 25.12.2021"));
    }

    @Test
    void shouldSendWithoutCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "25.12.2021");
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendWithoutDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на 22.12.2021")); //дата актуальна только в текущий день!!
    }

    @Test
    void shouldSendWithoutName() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "25.12.2021");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendWithoutPhone() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "25.12.2021");
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendWithoutCheckbox() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "25.12.2021");
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".button").click();
        $("[data-test-id='agreement'] .checkbox__text")
                .shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldSendNothing() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
}
