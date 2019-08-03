package services;

import domaine.Item;
import domaine.ItemType;
import domaine.ReductionItem;
import exceptions.NoSuchReduction;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class PricingTest {

    private static PricingImplementation pricing;

    @BeforeClass
    public static void setup() {
        PricingTest.pricing = new PricingImplementation();
    }

    @Test
    public void default_price_should_be_unit_price_multiply_number() {
        // GIVEN
        Item item = new Item(BigDecimal.valueOf(12.5), "milk", ItemType.UNITE);
        float boughtNumber = 3f;

        //WHEN
        BigDecimal sum = pricing.calculatePrice(item, boughtNumber);

        //THEN
        Assert.assertThat(new BigDecimal("37.50"), is(sum));
    }

    /*
     * in this function we test two test one buy two get one free
     * the second when when reduction applied on production by percent
     * and the last when 1 pound is for 1$ what is the price of 4 ounces
     */
    @Test
    @Parameters({
            "3f, 100, 200, 2, 0.5f, UNITE, 3",
            //the idea behind buy two get one free is we applied a reduction to item of 50% and the last still have the same price
            "5f, 80, 240, 2, 0.5f, WEIGHT, 2f",
            "2f, 100, 200, 2, 0.5f, UNITE, 3f",
    })
    public void reduced_price_should_be_reached(float boughtNumber, BigDecimal unitPrice,
                                                BigDecimal expectedPrice, int numberToBuy,
                                                float reduction, ItemType type, float minNumberToApply) throws NoSuchReduction {
        //GIVEN
        Set<ReductionItem> reductionItems = new HashSet<ReductionItem>();
        reductionItems.add(new ReductionItem(numberToBuy, reduction, minNumberToApply));
        Item item = new Item(unitPrice, "anonymous", type, reductionItems);

        //WHEN
        BigDecimal sum = pricing.calculateReducedPrice(item, boughtNumber);

        Assert.assertEquals(0, expectedPrice.compareTo(sum));

    }

    /*
     * ADVANCED TEST
     * Assuming that we have multiple reductions for the same item
     * for example  user buy 2 item 1 and for the same item 2 for 1S
     */

    @Test
    public void reduced_price_forTheSameItem_should_be_reached() throws NoSuchReduction {
        //GIVEN
        Set<ReductionItem> reductionItems = new HashSet<ReductionItem>();
        reductionItems.add(new ReductionItem(2f, 0.25f, 2f));
        reductionItems.add(new ReductionItem(2f, 0.5f, 3f));
        Item item = new Item(BigDecimal.valueOf(1), "anonymous", ItemType.UNITE, reductionItems);

        //WHEN
        BigDecimal sum = pricing.calculateReducedPrice(item, 5);

        Assert.assertEquals(0, BigDecimal.valueOf(2.5).compareTo(sum));

    }

    // unhappy paths
    @Test(expected = NoSuchReduction.class)
    public void calculation_give_exception_if_there_is_no_reduction() throws NoSuchReduction {
        //GIVEN
        Item item = new Item(BigDecimal.valueOf(1), "anonymous", ItemType.UNITE);

        //WHEN
        BigDecimal sum = pricing.calculateReducedPrice(item, 2);


    }

}
