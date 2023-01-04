package org.example.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ListClient extends BaseRestClient {
    public static Response listGet () {
        return given()
                .get("/api/v1/orders");
    }
}
