package domaine;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Item {
    private BigDecimal price;
    private String name;
    private ItemType type;
    private Set<ReductionItem> reductionItems;

    public Item(BigDecimal price, String name, ItemType type) {
        this.price = price;
        this.name = name;
        this.type = type;
        this.reductionItems = new HashSet<>();
    }

    public Item(BigDecimal price, String name, ItemType type, Set<ReductionItem> reductionItems) {
        this.price = price;
        this.name = name;
        this.type = type;
        this.reductionItems = reductionItems;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public Set<ReductionItem> getReductionItems() {
        return new HashSet<>(reductionItems);
    }

    public void addReduction(ReductionItem reductionItem) {
        Optional<ReductionItem> localReduction = this.reductionItems
                .stream()
                .filter(r -> r.getBoughtNumber() == r.getBoughtNumber())
                .findFirst();
        if (localReduction.isPresent()) {
            this.reductionItems.remove(localReduction.get());
            this.reductionItems.add(reductionItem);
        } else {
            this.reductionItems.add(reductionItem);
        }
    }

    public void removeReductions() {
        this.reductionItems.clear();
    }

    public void removeReduction(ReductionItem reductionItem) {
        this.reductionItems.remove(reductionItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return price.equals(item.price) &&
                name.equals(item.name) &&
                type == item.type &&
                reductionItems.equals(item.reductionItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name, type, reductionItems);
    }
}
