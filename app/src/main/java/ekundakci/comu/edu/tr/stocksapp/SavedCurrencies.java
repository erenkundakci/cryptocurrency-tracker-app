package ekundakci.comu.edu.tr.stocksapp;

public class SavedCurrencies {

    private int id;
    private String currencyName;
    private boolean isFavorite;
    private String currencyToConvert;


    public SavedCurrencies(int id, String currencyName, boolean isFavorite, String currencyToConvert) {
        this.id = id;
        this.currencyName = currencyName;
        this.isFavorite = isFavorite;
        this.currencyToConvert = currencyToConvert;
    }

    public int getSavedCurrencyId() {
        return id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getCurrencyToConvert() {
        return currencyToConvert;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
