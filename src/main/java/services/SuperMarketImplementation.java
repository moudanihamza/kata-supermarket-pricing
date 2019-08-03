package services;

import domaine.Customer;
import domaine.Item;
import domaine.ReductionItem;
import exceptions.InValidReduction;
import exceptions.NoSuchReduction;

import java.math.BigDecimal;
import java.util.Map;

public class SuperMarketImplementation implements SuperMarket {
    @Override
    public void addReduction(Item item, float numberToBuy, float reduction, float minNumberToApply) throws InValidReduction {
        if (numberToBuy <= 0 || reduction <= 0 || minNumberToApply < numberToBuy) {
            throw new InValidReduction();
        }
        item.addReduction(new ReductionItem(numberToBuy, reduction, minNumberToApply));
    }

    @Override
    public void removeReductions(Item item) {
        item.removeReductions();
    }

    @Override
    public void removeReduction(Item item, ReductionItem reductionItem) {
        item.removeReduction(reductionItem);
    }

    @Override
    public BigDecimal getSum(Customer customer) {
        final Map<Item, Float> cart = customer.getCart();
        BigDecimal sum = BigDecimal.ZERO;
        return sum.add(getDefaultPricing(cart))
                .add(getReducedPricing(cart));
    }

    private BigDecimal getDefaultPricing(Map<Item, Float> cart) {
        return cart.entrySet()
                .stream()
                .filter(o -> o.getKey().getReductionItems().isEmpty())
                .map(o -> new PricingImplementation().calculatePrice(o.getKey(), o.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getReducedPricing(Map<Item, Float> cart) {
        return cart.entrySet()
                .stream()
                .filter(o -> !o.getKey().getReductionItems().isEmpty())
                .map(item -> {
                    try {
                        return new PricingImplementation().calculateReducedPrice(item.getKey(), item.getValue());
                    } catch (NoSuchReduction noSuchReduction) {
                        noSuchReduction.printStackTrace();
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
