package com.home.Test_REST_API.accuweather;

import com.home.Test_REST_API.accuweather.location.Location;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CityNeighborsByLocationTest extends AccuweatherAbstractTest{

    @Test
    void getLocation_search_CityNeighborsByKazan() {

        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "Kazan")
                .when()
                .get(getBaseUrl()+"/locations/v1/cities/neighbors/295954")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        Assertions.assertEquals(10,response.size());
        Assertions.assertEquals("Vyerkhniy Uslon", response.get(0).getEnglishName());
    }
}
