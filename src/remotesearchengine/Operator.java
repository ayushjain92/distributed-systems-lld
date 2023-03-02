package remotesearchengine;

public enum Operator {
    EQUALS("EQUALS"),
    IN("IN");

    String value;

    Operator(String v) {
        value = v;
    }
}
