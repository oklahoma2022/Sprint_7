package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseRestClient {
     protected BaseRestClient(){
         RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
     }

     protected RequestSpecification jsonRequest(){
         return RestAssured
                 .given()
                 .contentType(ContentType.JSON);
     }
}
