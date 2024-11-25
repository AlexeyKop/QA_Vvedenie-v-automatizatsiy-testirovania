package com.home.Test_REST_API.accuweather;

import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;
import com.home.Test_REST_API.accuweather.location.Location;
import java.util.List;
import static io.restassured.RestAssured.given;

@Epic("Тестирование проекта accuweather.com")
@Feature("Тестирование API Location API")
public class LocationTest extends AccuweatherAbstractTest{

    @Test
    @DisplayName("Тест LocationTest - поиск объекта Location")
    @Description("Данный тест на поиск Location по ключу Kazan")
    @Link("https://developer.accuweather.com/accuweather-locations-api/apis")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение объекта Location для Kazan")
    @Owner("Alex Kop")
    void getLocation_search_returnKazan() {

        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "Kazan")
                .when()
                .get(getBaseUrl()+"/locations/v1/cities/search")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        Assertions.assertEquals(6,response.size());
        Assertions.assertEquals("Kazan", response.get(0).getEnglishName());
    }
}
