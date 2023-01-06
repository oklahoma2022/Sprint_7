package org.example.api;

import io.restassured.response.Response;
import org.example.models.AuthCourier;
import org.example.models.Courier;
import static io.restassured.RestAssured.given;
import static org.example.constants.ConstantEndpoints.COURIER_API;
import static org.example.constants.ConstantEndpoints.LOGIN_API;

public class CourierClient extends BaseRestClient {
    public Response createCourier(Courier courier) {
        return jsonRequest()
            .body(courier)
            .when()
            .post(COURIER_API);
    }

    public Response loginCourier(AuthCourier auth) {
        return jsonRequest()
                .body(auth)
                .when()
                .post(LOGIN_API);
    }

    public Response deleteCourier(Integer userId) {
        return given().when().delete(COURIER_API + '/' + userId);
    }
}
