package org.example.api;

import io.restassured.response.Response;
import org.example.models.AuthCourier;
import org.example.models.Courier;
import static io.restassured.RestAssured.given;

public class CourierClient extends BaseRestClient {
    public Response createCourier(Courier courier) {
        return jsonRequest()
            .body(courier)
            .when()
            .post("/api/v1/courier");
    }

    public Response loginCourier(AuthCourier auth) {
        return jsonRequest()
                .body(auth)
                .when()
                .post("/api/v1/courier/login");
    }

    public Response deleteCourier(String userId) {
        return given().when().delete("/api/v1/courier/" + userId);
    }
}
