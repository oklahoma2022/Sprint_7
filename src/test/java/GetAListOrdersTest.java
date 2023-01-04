import io.qameta.allure.junit4.DisplayName;
import org.example.api.ListClient;
import org.junit.Test;


public class GetAListOrdersTest  {

    ListClient listClient;
    public GetAListOrdersTest() {
        listClient = new ListClient();
    }


    @Test
    @DisplayName("Проверка, что в тело возвращается список заказов ")
    public void listOrders(){
               ListClient.listGet()
                .then().statusCode(200);
    }

}
