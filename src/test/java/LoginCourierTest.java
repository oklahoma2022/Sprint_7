import Models.AuthCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.example.Constants.ConstantCourier.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    //курьер может авторизоваться;
    @Test
    @DisplayName("Авторизация курьера в системе")
    public void LoginCourier() {
        AuthCourier auth = new AuthCourier(LOGIN,PASSWORD);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(auth)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());
    }

     // для авторизации нужно передать все обязательные поля;
     @Test
     @DisplayName("Авторизация курьера без передачи поля login ")
     public void LoginCourierNoLogin(){
         AuthCourier auth = new AuthCourier("",PASSWORD);
         given()
                 .header("Content-type", "application/json")
                 .and()
                 .body(auth)
                 .when()
                 .post("/api/v1/courier/login")
                 .then().statusCode(400)
                 .and()
                 .assertThat().body("message",equalTo("Недостаточно данных для входа"));
     }

    @Test
    @DisplayName("Авторизация курьера без передачи поля password")
    public void LoginCourierNoPassword(){
        AuthCourier auth = new AuthCourier(LOGIN,"");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(auth)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для входа"));
    }

    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    @DisplayName("Авторизация курьера при вводе не верного логина ")
    public void LoginCourierNotValidLogin(){
        AuthCourier auth = new AuthCourier("LOGIN",PASSWORD);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(auth)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404)
                .and()
                .assertThat().body("message",equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера при вводе не верного пароля ")
    public void LoginCourierNotValidPassword(){
        AuthCourier auth = new AuthCourier(LOGIN,"1");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(auth)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404)
                .and()
                .assertThat().body("message",equalTo("Учетная запись не найдена"));
    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("Авторизация курьера которого нет в системе ")
    public void LoginCourierNoCourier(){
        AuthCourier auth = new AuthCourier("NoCourier","1");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(auth)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404)
                .and()
                .assertThat().body("message",equalTo("Учетная запись не найдена"));
    }
}
