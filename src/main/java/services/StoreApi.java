package services;

import TestBase.Specification;
import dto.OrderDTO;

import static TestBase.Specification.requestSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StoreApi {
    private final String baseUrl = System.getProperty("base.url");
    private static final String STORE_ENDPOINT = "/store";

    public void postStoreOrder(OrderDTO orderDTO, int expectedStatusCode) {
        var responseSpec = given()
                .spec(requestSpec(baseUrl))
                .body(orderDTO)
                .when()
                .post(STORE_ENDPOINT + "/order")
                .then()
                .spec(Specification.responseSpec(expectedStatusCode))
                .body("id", notNullValue())
                .body("petId", equalTo(orderDTO.getPetId()))
                .body("quantity", equalTo(orderDTO.getQuantity()))
                .body("shipDate", startsWith(orderDTO.getShipDate().replace("Z", "")))
                .body("complete", equalTo(orderDTO.isComplete()));

        if (orderDTO.getStatus() != null) {
            responseSpec.body("status", equalTo(orderDTO.getStatus()));
        }
    }

    public void deleteStoreOrderId(int expectedStatusCode, int id) {
        given()
                .spec(requestSpec(baseUrl))
                .when()
                .delete(STORE_ENDPOINT + "/order/" + id)
                .then()
                .spec(Specification.responseSpec(expectedStatusCode))
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", equalTo(String.valueOf(id)));
    }

    public void deleteStoreOrderIncorrectId(int expectedStatusCode, int id) {
        given()
                .spec(requestSpec(baseUrl))
                .when()
                .delete(STORE_ENDPOINT + "/order/" + id)
                .then()
                .spec(Specification.responseSpec(expectedStatusCode))
                .body("code", equalTo(404))
                .body("type", equalTo("unknown"))
                .body("message", equalTo("Order Not Found"));
    }
}
