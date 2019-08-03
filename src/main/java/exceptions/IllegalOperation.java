package exceptions;

public class IllegalOperation extends  Exception{

    private static final  String message = "Illegal operation";

    public IllegalOperation() {
        super(message);
    }
}
