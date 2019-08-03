package services;

import domaine.Customer;
import domaine.Item;
import domaine.ItemType;
import domaine.ReductionItem;
import exceptions.IllegalOperation;
import exceptions.InValidReduction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;

public class SuperMarketTest {

    public static SuperMarket superMarket;

    @BeforeClass
    public static void setUp() {
        superMarket = new SuperMarketImplementation();
    }

    @Test
    public void item_should_be_correctly_replaced_when_reduction_new_reduction_added() throws InValidReduction {
        //GIVEN
        Item item = new Item(BigDecimal.ONE, "anonymous", ItemType.UNITE);
        float numberToBuy = 2f;
        float minNumberToApply = 2f;
        float reduction = 0.3f;

        //WHEN
        superMarket.addReduction(item, numberToBuy, reduction, minNumberToApply);

        //THEN
        boolean isTheGivenReductionExist = item.getReductionItems()
                .stream()
                .anyMatch(r->r.getBoughtNumber()==numberToBuy && r.getReduction()==reduction);

        Assert.assertTrue(isTheGivenReductionExist);
    }

    @Test
    public void item_should_be_correctly_updated_when_reduction_added() throws InValidReduction {
        //GIVEN
        HashSet<ReductionItem> reductionItems = new HashSet<ReductionItem>();
        reductionItems.add(new ReductionItem(3, 0.2f, 3));
        Item item = new Item(BigDecimal.ONE, "anonymous",
                ItemType.UNITE, reductionItems);
        float numberToBuy = 2;
        float minNumberToApply = 2;
        float reduction = 0.3f;

        //WHEN
        superMarket.addReduction(item, numberToBuy, reduction, minNumberToApply);

        //THEN
        boolean isTheGivenReductionExist = item.getReductionItems()
                .stream()
                .anyMatch(r->r.getBoughtNumber()==numberToBuy && r.getReduction()==reduction);

        Assert.assertTrue(isTheGivenReductionExist);
    }

    @Test
    public void item_should_be_correctly_updated_when_reduction_removed() {

        //GIVEN
        HashSet<ReductionItem> reductionItems = new HashSet<ReductionItem>();
        ReductionItem reductionItem = new ReductionItem(3, 0.2f, 3);
        reductionItems.add(reductionItem);
        Item item = new Item(BigDecimal.ONE, "anonymous",
                ItemType.UNITE, reductionItems);

        //WHEN
        superMarket.removeReduction(item, reductionItem);

        //THEN
        Assert.assertTrue(item.getReductionItems().isEmpty());

    }

    @Test
    public void item_should_be_correctly_updated_when_all_reductions_removed() {

        //GIVEN
        HashSet<ReductionItem> reductionItems = new HashSet<ReductionItem>();
        ReductionItem reductionItem = new ReductionItem(3, 0.2f, 3);
        reductionItems.add(reductionItem);
        Item item = new Item(BigDecimal.ONE, "anonymous",
                ItemType.UNITE, reductionItems);

        //WHEN
        superMarket.removeReductions(item);

        //THEN
        Assert.assertTrue(item.getReductionItems().isEmpty());

    }

    @Test
    public void supermarket_should_be_able_get_corrected_price() throws IllegalOperation {
        //GIVEN
        HashSet<ReductionItem> reductionItems = new HashSet<ReductionItem>();
        reductionItems.add(new ReductionItem(2, 0.5f, 2));
        Item item = new Item(BigDecimal.ONE, "anonymous",
                ItemType.UNITE, reductionItems);
        BigDecimal expectedPrice = BigDecimal.valueOf(2);
        Customer customer = new Customer();
        customer.addToCart(item, 3);

        //WHEN
        BigDecimal price = superMarket.getSum(customer);

        //THEN
        Assert.assertEquals(0, price.compareTo(expectedPrice));


    }

    // unhappy paths

    @Test(expected = InValidReduction.class)
    public void supermarket_should_not_be_able_to_add_invalid_reduction() throws InValidReduction {
        //GIVEN
        HashSet<ReductionItem> reductionItems = new HashSet<ReductionItem>();
        reductionItems.add(new ReductionItem(3, 0.2f, 3));
        Item item = new Item(BigDecimal.ONE, "anonymous",
                ItemType.UNITE, reductionItems);
        float numberToBuy = 2;
        float minNumberToApply = 1;
        float reduction = 0.3f;

        //WHEN
        superMarket.addReduction(item, numberToBuy, reduction, minNumberToApply);

    }


}
