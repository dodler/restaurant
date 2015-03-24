package model;

@Deprecated
public class IncorrectCostException extends Exception {
    public IncorrectCostException() {
        super("Указана некорректная цена.");
    }
}
