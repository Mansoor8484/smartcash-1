package rebelalliance.smartcash.file.preferences;

public enum UserPreference {
    OVERVIEW_HIDDEN_COMPOSITION_ACCOUNTS("overview.hiddenCompositionAccounts"),
    OVERVIEW_HIDDEN_CATEGORY_SPEND_CATEGORIES("overview.hiddenCategorySpendCategories"),
    OVERVIEW_HIDDEN_HISTORICAL_ACCOUNTS("overview.hiddenHistoricalAccounts");

    private final String key;

    UserPreference(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
