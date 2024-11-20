package com.home.Test_REST_API.accuweather;


import com.home.Test_REST_API.accuweather.weather.Weather;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class WeatherFiveDayTest extends AccuweatherAbstractTest {



    @Test
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
