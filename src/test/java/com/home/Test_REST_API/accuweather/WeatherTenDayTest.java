package com.home.Test_REST_API.accuweather;


import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class WeatherTenDayTest extends AccuweatherAbstractTest {

    @Test
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
