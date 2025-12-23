package PetstoreTest;

import dto.OrderDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import services.StoreApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class StoreTest {

    private final StoreApi storeApi = new StoreApi();
    private int generatedId = ThreadLocalRandom.current().nextInt(1, 10);
    private final List<Integer> createdOrders = new ArrayList<>();

    //Отправка запроса POST/store/order и проверка в ответе статус кода, а также ключей и полей
    @Test
    void postStoreOrderTest() {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(generatedId)
                .petId(0)
                .quantity(0)
                .shipDate("2025-12-16T16:59:14.135Z")
                .status("placed")
                .complete(true)
                .build();

        storeApi.postStoreOrder(orderDTO, 200);
        createdOrders.add(generatedId);
    }

    //Отправка запроса POST/store/order без поля status и проверка в ответе статус кода, а также ключей и полей
    @Test
    void postStoreOrderWithoutStatusTest() {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(generatedId)
                .petId(0)
                .quantity(0)
                .shipDate("2025-12-16T16:59:14.135Z")
                .complete(true)
                .build();

        storeApi.postStoreOrder(orderDTO, 200);
        createdOrders.add(generatedId);
    }

    //Отправка запроса GET/store/order/{orderId} и проверка в ответе статус кода, а также ключей и полей
    @Test
    void getStoreOrderIdTest() {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(generatedId)
                .petId(0)
                .quantity(0)
                .shipDate("2025-12-16T16:59:14.135Z")
                .status("placed")
                .complete(true)
                .build();

        storeApi.postStoreOrder(orderDTO, 200);

        storeApi.getStoreOrderId(orderDTO, 200, generatedId);
        createdOrders.add(generatedId);
    }

    //Отправка запроса GET/store/order/{orderId} с некорректным orderId и проверка в ответе статус кода, а также ключей и полей
    @Test
    void getStoreIncorrectOrderIdTest() {
        storeApi.getStoreOrderIncorrectId(404, generatedId);
    }

    //Отправка запроса DELETE/store/order/{orderId} и проверка в ответе статус кода, а также ключей и полей
    @Test
    void deleteStoreOrderIdTest() {
        int id = generatedId;

        OrderDTO orderDTO = OrderDTO.builder()
                .id(id)
                .petId(0)
                .quantity(0)
                .shipDate("2025-12-16T16:59:14.135Z")
                .status("placed")
                .complete(true)
                .build();

        storeApi.postStoreOrder(orderDTO, 200);

        storeApi.deleteStoreOrderId(200, id);
    }

    //Отправка запроса DELETE/store/order/{orderId} для несуществующего orderId и проверка в ответе статус кода, а также ключей и полей
    @Test
    void deleteStoreOrderIncorrectOrderIdTest() {
        storeApi.deleteStoreOrderIncorrectOrderId(404, generatedId);
    }

    @AfterEach
    void cleanup() {
        for (int id : createdOrders) {
            storeApi.deleteStoreOrderId(200, id);
        }
        createdOrders.clear();
    }
}
