package com.home.Test_REST_API.accuweather;

import com.home.Test_REST_API.accuweather.weather.Weather;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class WeatherOneDayTest extends AccuweatherAbstractTest {

    @Test
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
