import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.api.CourierClient;
import org.example.models.AuthCourier;
import org.example.models.Courier;
import org.example.services.CourierGenerator;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    CourierClient courierClient;

    Courier courier;

    Integer id;

    public CreateCourierTest() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    //курьера можно создать;
    @Test
    @DisplayName("Проверка создания курьера")
    public void toCreateCourierTestValid(){
        courierClient.createCourier(courier)
                .then()
                .statusCode(201)
                .and()
                .assertThat()
                .body("ok",equalTo(true));
    }

    // нельзя создать двух одинаковых курьеров;
    @Test
    @DisplayName("Проверка создания одинаковых курьеров")
    public void toCreateCourierReplay(){
        courierClient.createCourier(courier)
                .then()
                .statusCode(409)
                .and()
                .assertThat()
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));

        // Получаем ID пользователя после повторной проверки на авторизацию
        // Удаляем на этапе повторной проверки т.к. анотация After  удаляет данные после каждого теста,
        // а нам необходима проверка  создания пользователя с уже имеющимися данными
        AuthCourier auth = new AuthCourier(courier.getLogin(), courier.getPassword());
        Response loginResponse = courierClient.loginCourier(auth);
        id = loginResponse.then().extract().path("id");
    }

    // чтобы создать курьера, нужно передать в ручку все обязательные поля;
    @Test
    @DisplayName("Проверка создания курьера если не передаем обязательное поле login")
    public void toCreateCourierNoLogin(){
        Courier newCourier = new Courier("", courier.getPassword(), courier.getFirstName());
        courierClient.createCourier(newCourier)
                .then()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message",equalTo( "Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка создания курьера если не передаем обязательнео поле  password")
    public void toCreateCourierNoPassword(){
        Courier newCourier = new Courier(courier.getLogin(),"",courier.getFirstName());
        courierClient.createCourier(newCourier)
                .then()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message",equalTo( "Недостаточно данных для создания учетной записи"));
    }

    //Удаляем курьера из базы через анотацию ,после каждого теста
    //Если он создан и значения id не пустое
    @After
    public void deleteCourier() {
        if (id != null) {
            courierClient.deleteCourier(id).then().statusCode(200);
        }
    }
}
