package org.example.api;

import io.restassured.response.Response;
import org.example.models.Order;

public class OrderClient extends BaseRestClient {
        public Response createOrder(Order order) {
            return jsonRequest()
                    .body(order)
                    .when()
                    .post("/api/v1/orders");
        }
}
