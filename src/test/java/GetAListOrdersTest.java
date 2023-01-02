import Models.AuthCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;


public class GetAListOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Проверка, что в тело возвращается список заказов ")

    public void ListOrders(){
                given()
                .get("/api/v1/orders")
                .then().statusCode(200);
    }

}
