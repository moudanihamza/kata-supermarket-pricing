package domaine;

import exceptions.IllegalOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CustomerTest {
    private Customer customer;


    @Before
    public  void setup() {
        this.customer = new Customer();
    }

    @Test
    public void customer_should_be_able_to_add_product_in_his_cart() throws IllegalOperation {
        //GIVEN
        Item item = new Item(BigDecimal.valueOf(12), "unknown", ItemType.UNITE);
        float boughtNumber = 5f;

        //WHEN
        customer.addToCart(item, boughtNumber);

        //THEN
        boolean isProductInCart = customer.getCart()
                .entrySet()
                .stream()
                .allMatch(p -> p.getKey().equals(item) && p.getValue().equals(boughtNumber));
        Assert.assertTrue(isProductInCart);
    }

    @Test
    public  void customer_should_be_able_to_remove_product_from_his_cart() throws IllegalOperation {
        //GIVEN
        Item item = new Item(BigDecimal.valueOf(12), "unknown", ItemType.UNITE);
        float boughtNumber = 5f, numberToRemove = 5f;
        customer.addToCart(item, boughtNumber);

        //WHEN
        customer.removeFromCart(item, numberToRemove);

        //THEN
        Assert.assertTrue(customer.getCart().isEmpty());
    }

    @Test
    public void customer_should_be_able_to_update_product_in_his_cart() throws IllegalOperation {
        //GIVEN
        Item item = new Item(BigDecimal.valueOf(12), "unknown", ItemType.UNITE);
        float boughtNumber = 5f;
        customer.addToCart(item, boughtNumber);

        //WHEN
        customer.addToCart(item, boughtNumber);

        //THEN
        boolean isProductInCart = customer.getCart()
                .entrySet()
                .stream()
                .allMatch(p -> p.getKey().equals(item) && p.getValue().equals(boughtNumber*2));
        Assert.assertTrue(isProductInCart);
    }


    /*
     * Unhappy Paths
     */

    @Test(expected = IllegalOperation.class)
    public void customer_should_not_be_able_to_add_half_of_product_with_item_type() throws IllegalOperation {
        //GIVEN
        Item item = new Item(BigDecimal.valueOf(12), "unknown", ItemType.UNITE);
        float boughtNumber = 1.5f;

        //WHEN
        customer.addToCart(item, boughtNumber);
    }
}
