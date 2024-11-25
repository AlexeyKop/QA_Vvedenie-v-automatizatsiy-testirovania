package com.home.Test_REST_API.accuweather;

import com.home.Test_REST_API.accuweather.location.Location;
import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("Тестирование проекта accuweather.com")
@Feature("Тестирование API Location API")
public class CityNeighborsByLocationTest extends AccuweatherAbstractTest{

    @Test
    @DisplayName("Тест CityNeighborsByLocationTest - поиск городов рядом с Location")
    @Description("Данный тест на поиск городов рядом с Location Kazan")
    @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
    @Severity(SeverityLevel.NORMAL)
    @Story("Получение объекта рядом с Location Kazan")
    @Owner("Alex Kop")
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
