package model;

public class IncorrectCostException extends Exception {
    public IncorrectCostException() {
        super("Указана некорректная цена.");
    }
}
