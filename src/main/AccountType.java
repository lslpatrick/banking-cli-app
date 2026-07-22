package main;

public enum AccountType {
    CHECKING("Checking"),
    SAVING("Saving");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
