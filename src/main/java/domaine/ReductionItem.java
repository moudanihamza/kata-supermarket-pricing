package domaine;

import java.util.Objects;

public class ReductionItem {

    private final float boughtNumber;
    private final float reduction;
    private final float applyNumber;

    public ReductionItem(float boughtNumber, float reduction, float applyNumber) {
        this.boughtNumber = boughtNumber;
        this.reduction = reduction;
        this.applyNumber = applyNumber;
    }

    public float getBoughtNumber() {
        return boughtNumber;
    }

    public float getReduction() {
        return reduction;
    }

    public float getApplyNumber() {
        return applyNumber;
    }

    @Override
    public String toString() {
        return "ReductionItem{" +
                "boughtNumber=" + boughtNumber +
                ", reduction=" + reduction +
                ", applyNumber=" + applyNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReductionItem reductionItem = (ReductionItem) o;
        return boughtNumber == reductionItem.boughtNumber &&
                Float.compare(reductionItem.reduction, reduction) == 0 &&
                applyNumber == reductionItem.applyNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(boughtNumber, reduction, applyNumber);
    }
}
