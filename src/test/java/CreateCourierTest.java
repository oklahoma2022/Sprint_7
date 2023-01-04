import io.qameta.allure.junit4.DisplayName;
import org.example.api.CourierClient;
import org.example.models.Courier;
import org.example.services.CourierGenerator;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    CourierClient courierClient;

    Courier courier;

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
                .body("ok",equalTo(true) );
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
}
