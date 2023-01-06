package org.example.services;

import com.github.javafaker.Faker;
import org.example.models.Courier;

public class CourierGenerator {
    private static Courier courier = null;

    public static Courier getRandomCourier() {
        if (courier == null) {
            Faker faker = new Faker();
            courier = new Courier(
                    faker.name().username(),
                    faker.internet().password(),
                    faker.name().firstName()
            );
        }
        return courier;
    }
}
