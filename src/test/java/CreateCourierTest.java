import Models.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.example.Constants.ConstantCourier.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    //курьера можно создать;
    @Test
    @DisplayName("Проверка создания курьера")
    public void toCreateCourierTestValid(){
        Courier courier = new Courier(LOGIN,PASSWORD,FIRST_NAME);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201)
                .and()
                .assertThat().body("ok",equalTo(true) );
    }
    // нельзя создать двух одинаковых курьеров;
    @Test
    @DisplayName("Проверка создания одинаковых курьеров")
    public void toCreateCourierReplay(){
        Courier courier = new Courier(LOGIN,PASSWORD,FIRST_NAME);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(409)
                .and()
                .assertThat().body("message",equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    // чтобы создать курьера, нужно передать в ручку все обязательные поля;
    @Test
    @DisplayName("Проверка создания курьера если не передаем обязательное поле login")
    public void toCreateCourierNoLogin(){
        Courier courier = new Courier("",PASSWORD,FIRST_NAME);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo( "Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка создания курьера если не передаем обязательнео поле  password")
    public void toCreateCourierNoPassword(){
        Courier courier = new Courier(LOGIN,"",FIRST_NAME);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo( "Недостаточно данных для создания учетной записи"));
    }
}
