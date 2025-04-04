package t2308e.assignment.enums;

public enum Category {
    DIGITAL_ART(1, "Tranh Kỹ Thuật Số"),
    AUDIO(2, "Âm Thanh"),
    VIDEO(3, "Video"),
    GIF(4, "GIF"),
    MODEL_3D(5, "Mô Hình 3D");

    private final int value;
    private final String description;

    Category(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static Category fromValue(int value) {
        for (Category category : values()) {
            if (category.value == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Category value: " + value);
    }

    public static Category fromDescription(String description) {
        for (Category category : values()) {
            if (category.description.equalsIgnoreCase(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Category description: " + description);
    }
}

