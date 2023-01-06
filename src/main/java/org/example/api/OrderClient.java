package org.example.api;

import io.restassured.response.Response;
import org.example.models.Order;

import static org.example.constants.ConstantEndpoints.ORDERS_API;

public class OrderClient extends BaseRestClient {
        public Response createOrder(Order order) {
            return jsonRequest()
                    .body(order)
                    .when()
                    .post(ORDERS_API);
        }
}
