package lesson2;

public class ArrayDataException extends RuntimeException {
    public ArrayDataException(String message, Throwable error) {
        super(message, error);
    }
}
