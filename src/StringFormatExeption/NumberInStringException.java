package StringFormatExeption;

public class NumberInStringException extends Exception {
    public NumberInStringException(String message) {
        super(message);
    }
    public static void checkString(String name) throws NumberInStringException {
        if (name.matches("\\d+")) {
            throw new NumberInStringException("Строка содержит число!");
        }
    }

}