package domaine;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

public class ItemTest {

    public static Item item;

    public static ReductionItem reductionItem;

    @BeforeClass
    public static void setup() {
        item = new Item(BigDecimal.ONE, "X", ItemType.WEIGHT);
        reductionItem = new ReductionItem(2f, 0.5f, 2f);
    }

    @Test
    public void reduction_should_be_Added_to_item() {
        //when
        item.addReduction(reductionItem);

        //then
        Assert.assertTrue(item.getReductionItems().contains(reductionItem));
    }

    @Test
    public void reduction_should_be_removed_from_item() {
        //when
        item.removeReduction(reductionItem);

        //then
        Assert.assertTrue(!item.getReductionItems().contains(reductionItem));
    }

    @Test
    public void reductions_should_be_cleared_from_item() {
        //GIVEN
        item.addReduction(reductionItem);

        //when
        item.removeReductions();

        //then
        Assert.assertTrue(item.getReductionItems().isEmpty());
    }
}
