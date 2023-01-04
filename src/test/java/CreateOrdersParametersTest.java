import io.qameta.allure.junit4.DisplayName;
import org.example.api.OrderClient;
import org.example.models.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.example.constants.ConstantOrders.*;
import static org.example.constants.ConstantСolors.BLACK;
import static org.example.constants.ConstantСolors.GREY;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrdersParametersTest {
    private final String[] currentColor;

    OrderClient orderClient;
    public CreateOrdersParametersTest(String[] currentColor){
        this.currentColor = currentColor;
        orderClient = new OrderClient();
    }

    @Parameterized.Parameters(name = "Тестовые данные: Передаваймый цвет: {0}")
    public static Object[][] ColorsSamocat() {
        return new Object[][]{
                {new String[]{BLACK}},
                {new String[]{GREY}},
                {new String[]{BLACK, GREY}},
                {new String[]{}},
        };
    };

    @Test
    @DisplayName("Проверка cоздания заказа ")
    public void toCreatedOrders(){
        Order order = new Order(FIRST_NAME_ORDERS,
                                    lAST_NAME_ORDERS,
                                    ADDRESS,
                                    METRO_STATION,
                                    PHONE,
                                    RENT_TIME,
                                    DELIVERY_DATE
                                    ,COMMENT, currentColor);
        orderClient.createOrder(order)
                .then()
                .statusCode(201)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }

}
