package t2308e.assignment.enums;

public enum Status {
    FOR_SALE(1, "Đang Bán"),
    SOLD(2, "Đã Bán"),
    NOT_FOR_SALE(3, "Không Bán");

    private final int value;
    private final String description;

    Status(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static Status fromValue(int value) {
        for (Status status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status value: " + value);
    }

    public static Status fromDescription(String description) {
        for (Status status : values()) {
            if (status.description.equalsIgnoreCase(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status description: " + description);
    }
}

