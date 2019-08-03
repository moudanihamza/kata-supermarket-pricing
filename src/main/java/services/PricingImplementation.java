package services;

import domaine.Item;
import domaine.ReductionItem;
import exceptions.NoSuchReduction;

import java.math.BigDecimal;
import java.util.Optional;

public class PricingImplementation implements Pricing {

    @Override
    public BigDecimal calculateReducedPrice(Item item, float boughtNumber) throws NoSuchReduction {
        if (item.getReductionItems().isEmpty()) {
            throw new NoSuchReduction();
        }
        return getReducedPrice(item, boughtNumber, BigDecimal.ZERO);
    }

    private BigDecimal getReducedPrice(Item item, float boughtNumber, BigDecimal priceWithReduction) {
        Optional<ReductionItem> reductionItem = item.getReductionItems()
                .stream()
                .sorted((o1, o2) -> Float.compare(o2.getApplyNumber(), o1.getApplyNumber()))
                .filter(r -> boughtNumber >= r.getApplyNumber())
                .findFirst();
        if (reductionItem.isPresent()) {
            int numberForReduction = (int) reductionItem.get().getBoughtNumber();
            float numberToApply = reductionItem.get().getApplyNumber();
            float reductionValue = reductionItem.get().getReduction();
            int numberOfReductions = (int) (boughtNumber / numberToApply);
            BigDecimal price = item.getPrice()
                    .multiply(BigDecimal.valueOf(numberOfReductions))
                    .multiply(BigDecimal.valueOf(numberForReduction))
                    .multiply(BigDecimal.valueOf(reductionValue))
                    .add(this.calculatePrice(item, (numberToApply - numberForReduction) * numberOfReductions));
            return getReducedPrice(item, boughtNumber - (numberToApply * numberOfReductions), priceWithReduction.add(price));

        } else {
            return priceWithReduction
                    .add(this.calculatePrice(item, boughtNumber));
        }
    }
}