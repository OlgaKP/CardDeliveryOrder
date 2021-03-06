package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(4);

    @Test
    void shouldSendSuccessfulData() {
        //Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        //$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE); --
        // очистить от значения поле ввода по умолчанию:
        // выделить содержимое двойным кликом и послать нажатием Backspace

        //element.sendKeys(Keys.CONTROL+"a") - выделяет element
        // .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "Your Value") - выделяет элемент,
        //      удаляет его, вставляет нужное значение
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void shouldSendWithoutCity() {
        open("http://localhost:9999/");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendWithoutBelorussianCity() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Минск");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldSendWithCityOnEnglish() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Moscow");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldSendWithoutDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(3))); //дата актуальна только в текущий день!!
    }

    @Test
    void shouldSendWithMeetingDateIn1Day() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(1));
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='date'] .input_invalid .input__sub")
                .shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldSendWithMeetingDateIn3Day() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(3));
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(3)));
    }

    @Test
    void shouldSendWithoutName() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendWithoutPhone() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendWithoutCheckbox() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Ия Ли");
        $("[data-test-id='phone'] input").setValue("+78001112233");
        $(".button").click();
        $("[data-test-id='agreement'] .checkbox__text")
                .shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldSendNothing() {
        open("http://localhost:9999/");
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
}
