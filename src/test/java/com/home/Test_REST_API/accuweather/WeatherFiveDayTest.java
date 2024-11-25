package com.home.Test_REST_API.accuweather;


import com.home.Test_REST_API.accuweather.weather.Weather;
import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

@Epic("Тестирование проекта accuweather.com")
@Feature("Тестирование API Forecast API")
public class WeatherFiveDayTest extends AccuweatherAbstractTest {

    @Test
    @DisplayName("Тест WeatherFiveDayTest - поиск погоды за 5 дней")
    @Description("Данный тест для получения данных о погоде за 5 дней")
    @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
    @Severity(SeverityLevel.NORMAL)
    @Story("Вызов метода получения погоды за 5 дней")
    @Owner("Alex Kop")
    void getWeatherFiveDay_shouldReturn() {

        Weather weather = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/5day/295954")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body().as(Weather.class);

        Assertions.assertEquals(5, weather.getDailyForecasts().size());
    }
}
