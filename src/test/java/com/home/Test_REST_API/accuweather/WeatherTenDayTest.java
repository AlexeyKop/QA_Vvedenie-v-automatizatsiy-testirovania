package com.home.Test_REST_API.accuweather;


import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

@Epic("Тестирование проекта accuweather.com")
@Feature("Тестирование API Forecast API")
public class WeatherTenDayTest extends AccuweatherAbstractTest {

    @Test
    @DisplayName("Тест WeatherTenDayTest (негативный) - поиск погоды за 10 дней")
    @Description("Негативный тест - проверка получения погоды за 10 дней")
    @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Вызов метода получения погоды за 10 дней")
    @Owner("Alex Kop")
    void getWeatherTenDay_shouldReturn401() {

        given()
                .queryParam("apikey", getApiKey())
                .pathParam("version", "v1")
                .pathParam("location", 295954)
                .when()
                .get(getBaseUrl()+"/forecasts/{version}/daily/10day/{location}")
                .then()
                .statusCode(401);
    }
}
