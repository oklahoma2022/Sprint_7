import io.qameta.allure.junit4.DisplayName;
import org.example.api.CourierClient;
import org.example.models.AuthCourier;
import org.example.models.Courier;
import org.example.services.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    CourierClient courierClient;

    Courier courier;

    Integer id;

    //Создаем "рамдомного" курьера для использования данных при тестировании
    public LoginCourierTest() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    @Before
    public void toCreateCourier() {
        courierClient.createCourier(courier);
    }

    //курьер может авторизоваться;
    @Test
    @DisplayName("Авторизация курьера в системе")
    public void loginCourier() {
        AuthCourier auth = new AuthCourier(courier.getLogin(), courier.getPassword());
        id = courierClient.loginCourier(auth)
            .then().statusCode(200)
            .and()
            .assertThat()
                .body("id", notNullValue())
                .extract()
                .path("id");
    }

     // для авторизации нужно передать все обязательные поля;
     @Test
     @DisplayName("Авторизация курьера без передачи поля login ")
     public void loginCourierNoLogin(){
         AuthCourier auth = new AuthCourier("",courier.getPassword());
         courierClient.loginCourier(auth)
                 .then().statusCode(400)
                 .and()
                 .assertThat().body("message",equalTo("Недостаточно данных для входа"));
     }

    @Test
    @DisplayName("Авторизация курьера без передачи поля password")
    public void loginCourierNoPassword(){
        AuthCourier auth = new AuthCourier(courier.getLogin(),"");
        courierClient.loginCourier(auth)
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для входа"));
    }

    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    @DisplayName("Авторизация курьера при вводе не верного логина ")
    public void loginCourierNotValidLogin(){
        AuthCourier auth = new AuthCourier("LOGIN",courier.getPassword());
        courierClient.loginCourier(auth)
                .then().statusCode(404)
                .and()
                .assertThat().body("message",equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера при вводе не верного пароля ")
    public void loginCourierNotValidPassword(){
        AuthCourier auth = new AuthCourier(courier.getLogin(),"1");
        courierClient.loginCourier(auth)
                .then().statusCode(404)
                .and()
                .assertThat().body("message",equalTo("Учетная запись не найдена"));
    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("Авторизация курьера которого нет в системе ")
    public void loginCourierNoCourier(){
        AuthCourier auth = new AuthCourier("NoCourier","1");
        courierClient.loginCourier(auth)
                .then().statusCode(404)
                .and()
                .assertThat().body("message",equalTo("Учетная запись не найдена"));
    }

   //Удалям курьера из базы ( убираемся  за собой после окончания проверок)
   //Если он создан и значения id не пустое
    @After
    public void deleteCourier() {
        if (id != null) {
            courierClient.deleteCourier(id).then().statusCode(200);
        }
    }
}
