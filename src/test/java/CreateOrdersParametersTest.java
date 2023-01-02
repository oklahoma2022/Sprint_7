import Models.Orders;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.example.Constants.ConstantColors.BLACK;
import static org.example.Constants.ConstantColors.GREY;
import static org.example.Constants.ConstantOrders.*;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrdersParametersTest {
    private final String[] currentColor;

    public CreateOrdersParametersTest(String[] currentColor){
        this.currentColor = currentColor;;
    }

    @Parameterized.Parameters(name = "Тестовые данные: Передаваймый цвет: {0}")
    public static Object[][] ColorsSamocat() {
        return new Object[][]{
                {new String[]{BLACK}},
                {new String[]{GREY}},
                {new String[]{BLACK, GREY}},
                {new String[]{}},
        };
    };

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Проверка cоздания заказа ")
    public void toCreatedOrders(){
        Orders orders = new Orders(FIRST_NAME_ORDERS,
                                    lAST_NAME_ORDERS,
                                    ADDRESS,
                                    METRO_STATION,
                                    PHONE,
                                    RENT_TIME,
                                    DELIVERY_DATE
                                    ,COMMENT, currentColor);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(orders)
                .when()
                .post("/api/v1/orders")
                .then().statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());
    }

}
