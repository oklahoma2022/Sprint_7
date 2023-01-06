package org.example.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.example.constants.ConstantEndpoints.ORDERS_API;

public class ListClient extends BaseRestClient {
    public Response listGet () {
        return given()
                .get(ORDERS_API);
    }
}
