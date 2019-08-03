package exceptions;

public class InValidReduction extends Exception {

    private static final String message = "Invalid ReductionItem";

    public InValidReduction() {
        super(message);
    }
}
