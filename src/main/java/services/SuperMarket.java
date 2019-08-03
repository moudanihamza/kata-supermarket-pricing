package services;

import domaine.Customer;
import domaine.Item;
import domaine.ReductionItem;
import exceptions.InValidReduction;

import java.math.BigDecimal;

public interface SuperMarket {

    void addReduction(Item item, float numberToBuy, float reduction, float minNumberToApply) throws InValidReduction;
    void removeReductions(Item item);
    void removeReduction(Item item, ReductionItem reductionItem);
    BigDecimal getSum(Customer customer);
}
