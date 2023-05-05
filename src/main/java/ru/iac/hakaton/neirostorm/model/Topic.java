package ru.iac.hakaton.neirostorm.model;

public enum Topic {
    DEVELOPMENT("Разработка"),
    ANALYSIS("Анализ"),
    TESTING("Тестирование"),
    DOCUMENTATION("Документирование"),
    DEVOPS("DevOps");

    private final String displayName;

    Topic(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Topic fromDisplayName(String displayName) {
        for (Topic value : Topic.values()) {
            if (value.getDisplayName().equals(displayName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum value found for displayName: " + displayName);
    }
}
