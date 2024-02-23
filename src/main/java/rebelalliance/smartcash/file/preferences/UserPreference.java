package rebelalliance.smartcash.file.preferences;

public enum UserPreference {
    OVERVIEW_HIDDEN_COMPOSITION_ACCOUNTS("overview.hiddenCompositionAccounts", ""),
    OVERVIEW_HIDDEN_CATEGORY_SPEND_CATEGORIES("overview.hiddenCategorySpendCategories", ""),
    OVERVIEW_HIDDEN_HISTORICAL_ACCOUNTS("overview.hiddenHistoricalAccounts", ""),
    TRANSACTIONS_HIDDEN_ACCOUNTS("transactions.hiddenAccounts", ""),
    TRANSACTIONS_HIDDEN_CATEGORIES("transactions.hiddenCategories", "");

    private final String key;
    private final String defaultValue;

    UserPreference(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return this.key;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }
}
