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
}
