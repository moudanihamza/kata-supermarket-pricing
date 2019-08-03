package services;

import domaine.Item;
import exceptions.NoSuchReduction;

import java.math.BigDecimal;

public interface Pricing {

    default BigDecimal calculatePrice(Item item, float numberBought) {
        return item.getPrice().multiply(BigDecimal.valueOf(numberBought));
    }
    BigDecimal calculateReducedPrice(Item item, float numberBought) throws NoSuchReduction ;
}
