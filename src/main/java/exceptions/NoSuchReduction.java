package exceptions;

public class NoSuchReduction extends Exception {

    private static final String message = "There is no special pricing for the given product";

    public NoSuchReduction() {
        super(message);
    }
}
