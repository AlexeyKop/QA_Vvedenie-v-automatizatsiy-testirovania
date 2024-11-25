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
public class WeatherOneDayTest extends AccuweatherAbstractTest {

    @Test
    @DisplayName("Тест WeatherOneDayTest - поиск погоды за 1 день")
    @Description("Данный тест для получения данных о погоде за 1 день")
    @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
    @Severity(SeverityLevel.NORMAL)
    @Story("Вызов метода получения погоды за 1 день")
    @Owner("Alex Kop")
    void getWeatherOneDay_shouldReturn() {

        Weather response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/1day/295954")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .response()
                .body().as(Weather.class);

        Assertions.assertEquals(1,response.getDailyForecasts().size());
    }
}
