import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.CourierClient;
import org.example.models.AuthCourier;
import org.example.models.Courier;
import org.example.services.CourierGenerator;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserCourierDeleteTest {
    CourierClient courierClient;

    Courier courier;

    public UserCourierDeleteTest() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    @Test
    @DisplayName("Удаление курьера  ")
    public void deleteCourier(){
        courierClient.deleteCourier(this.getUserId())
                .then()
                .statusCode(200)
                .body("ok",equalTo(true));
    }

    // Получаю идентификатор пользователя
    private String getUserId() {
        AuthCourier auth = new AuthCourier(courier.getLogin(), courier.getPassword());
        Response loginResponse = courierClient.loginCourier(auth);
        loginResponse.then().statusCode(200);

        return this.getIdFromResponse(loginResponse);
    }

    // Получаю идентификатор пользователя из ответа
    private String getIdFromResponse(Response response) {
        JsonPath result = new JsonPath(response.getBody().asString());
        return result.getString("id");
    }
}
