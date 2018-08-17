package ekundakci.comu.edu.tr.stocksapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ekundakci.comu.edu.tr.stocksapp.Activity.MainActivity;

public class StockCardInfo {

    private int currency_image_id;
    private String currency_name;
    private double currency_value;
    private String currency_value_1h;
    private String currency_value_24h;
    private String currency_value_7d;
    private String currency_to_convert;
    private double marketcap;
    private long lastupdated;
    private boolean isFavorited;

    public StockCardInfo(int currency_image_id, String currency_name, double currency_value, String currency_value_1h, String currency_value_24h, String currency_to_convert, boolean isFavorited, String currency_value_7d, double marketcap, long lastupdated){
        this.currency_image_id = currency_image_id;
        this.currency_name = currency_name;
        this.currency_value = currency_value;
        this.currency_value_1h = currency_value_1h;
        this.currency_value_24h = currency_value_24h;
        this.currency_to_convert = currency_to_convert;
        this.isFavorited = isFavorited;
        this.currency_value_7d = currency_value_7d;
        this.marketcap = marketcap;
        this.lastupdated = lastupdated;
    }

    public int getCurrency_image_id() {
        return currency_image_id;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public double getCurrency_value() {
        return currency_value;
    }

    public String getCurrency_value_1h() {
        return currency_value_1h;
    }

    public String getCurrency_value_24h() {
        return currency_value_24h;
    }

    public String getCurrency_to_convert() {
        return currency_to_convert;
    }

    public String getCurrency_value_7d() {
        return currency_value_7d;
    }

    public double getMarketcap() {
        return marketcap;
    }

    public long getLastupdated() {
        return lastupdated;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }
}
