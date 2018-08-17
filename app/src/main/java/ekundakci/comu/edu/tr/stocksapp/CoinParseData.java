package ekundakci.comu.edu.tr.stocksapp;

import com.google.gson.annotations.SerializedName;

import ekundakci.comu.edu.tr.stocksapp.Activity.MainActivity;

public class CoinParseData {

    /**
     * data : {"id":2,"name":"Litecoin","symbol":"LTC","website_slug":"litecoin","rank":7,"circulating_supply":5.7470832E7,"total_supply":5.7470832E7,"max_supply":8.4E7,"quotes":{"TRY":{"price":420.6179267072,"volume_24h":1.6899828701208713E9,"market_cap":2.4173262222E10,"percent_change_1h":0.53,"percent_change_24h":-4.62,"percent_change_7d":13.87},"USD":{"price":87.4145,"volume_24h":3.51219E8,"market_cap":5.023784048E9,"percent_change_1h":0.53,"percent_change_24h":-4.62,"percent_change_7d":13.87}},"last_updated":1531982534}
     * metadata : {"timestamp":1531982173,"error":null}
     */

    private DataBean data;
    private MetadataBean metadata;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public static class DataBean {
        /**
         * id : 2
         * name : Litecoin
         * symbol : LTC
         * website_slug : litecoin
         * rank : 7
         * circulating_supply : 5.7470832E7
         * total_supply : 5.7470832E7
         * max_supply : 8.4E7
         * quotes : {"TRY":{"price":420.6179267072,"volume_24h":1.6899828701208713E9,"market_cap":2.4173262222E10,"percent_change_1h":0.53,"percent_change_24h":-4.62,"percent_change_7d":13.87},"USD":{"price":87.4145,"volume_24h":3.51219E8,"market_cap":5.023784048E9,"percent_change_1h":0.53,"percent_change_24h":-4.62,"percent_change_7d":13.87}}
         * last_updated : 1531982534
         */

        private int id;
        private String name;
        private String symbol;
        private String website_slug;
        private int rank;
        private double circulating_supply;
        private double total_supply;
        private double max_supply;
        private QuotesBean quotes;
        private int last_updated;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getWebsite_slug() {
            return website_slug;
        }

        public void setWebsite_slug(String website_slug) {
            this.website_slug = website_slug;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public double getCirculating_supply() {
            return circulating_supply;
        }

        public void setCirculating_supply(double circulating_supply) {
            this.circulating_supply = circulating_supply;
        }

        public double getTotal_supply() {
            return total_supply;
        }

        public void setTotal_supply(double total_supply) {
            this.total_supply = total_supply;
        }

        public double getMax_supply() {
            return max_supply;
        }

        public void setMax_supply(double max_supply) {
            this.max_supply = max_supply;
        }

        public QuotesBean getQuotes() {
            return quotes;
        }

        public void setQuotes(QuotesBean quotes) {
            this.quotes = quotes;
        }

        public int getLast_updated() {
            return last_updated;
        }

        public void setLast_updated(int last_updated) {
            this.last_updated = last_updated;
        }

        public static class QuotesBean {
            /**
             * TRY : {"price":420.6179267072,"volume_24h":1.6899828701208713E9,"market_cap":2.4173262222E10,"percent_change_1h":0.53,"percent_change_24h":-4.62,"percent_change_7d":13.87}
             * USD : {"price":87.4145,"volume_24h":3.51219E8,"market_cap":5.023784048E9,"percent_change_1h":0.53,"percent_change_24h":-4.62,"percent_change_7d":13.87}
             */

            String symbol = "EUR";
            @SerializedName(value="TRY", alternate={"AUD", "BRL", "CAD", "CHF", "CLP", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PKR", "PLN", "RUB", "SEK", "SGD", "THB", "TWD", "ZAR" })
            private SelectedCurrencyBean TRY;
            private USDBean USD;

            public SelectedCurrencyBean getSelectedCurrency() {
                return TRY;
            }

            public void setSelectedCurrency(SelectedCurrencyBean TRY) {
                this.TRY = TRY;
            }

            public USDBean getUSD() {
                return USD;
            }

            public void setUSD(USDBean USD) {
                this.USD = USD;
            }

            public static class SelectedCurrencyBean {
                /**
                 * price : 420.6179267072
                 * volume_24h : 1.6899828701208713E9
                 * market_cap : 2.4173262222E10
                 * percent_change_1h : 0.53
                 * percent_change_24h : -4.62
                 * percent_change_7d : 13.87
                 */

                private double price;
                private double volume_24h;
                private double market_cap;
                private double percent_change_1h;
                private double percent_change_24h;
                private double percent_change_7d;

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getVolume_24h() {
                    return volume_24h;
                }

                public void setVolume_24h(double volume_24h) {
                    this.volume_24h = volume_24h;
                }

                public double getMarket_cap() {
                    return market_cap;
                }

                public void setMarket_cap(double market_cap) {
                    this.market_cap = market_cap;
                }

                public double getPercent_change_1h() {
                    return percent_change_1h;
                }

                public void setPercent_change_1h(double percent_change_1h) {
                    this.percent_change_1h = percent_change_1h;
                }

                public double getPercent_change_24h() {
                    return percent_change_24h;
                }

                public void setPercent_change_24h(double percent_change_24h) {
                    this.percent_change_24h = percent_change_24h;
                }

                public double getPercent_change_7d() {
                    return percent_change_7d;
                }

                public void setPercent_change_7d(double percent_change_7d) {
                    this.percent_change_7d = percent_change_7d;
                }
            }

            public static class USDBean {
                /**
                 * price : 87.4145
                 * volume_24h : 3.51219E8
                 * market_cap : 5.023784048E9
                 * percent_change_1h : 0.53
                 * percent_change_24h : -4.62
                 * percent_change_7d : 13.87
                 */

                private double price;
                private double volume_24h;
                private double market_cap;
                private double percent_change_1h;
                private double percent_change_24h;
                private double percent_change_7d;

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getVolume_24h() {
                    return volume_24h;
                }

                public void setVolume_24h(double volume_24h) {
                    this.volume_24h = volume_24h;
                }

                public double getMarket_cap() {
                    return market_cap;
                }

                public void setMarket_cap(double market_cap) {
                    this.market_cap = market_cap;
                }

                public double getPercent_change_1h() {
                    return percent_change_1h;
                }

                public void setPercent_change_1h(double percent_change_1h) {
                    this.percent_change_1h = percent_change_1h;
                }

                public double getPercent_change_24h() {
                    return percent_change_24h;
                }

                public void setPercent_change_24h(double percent_change_24h) {
                    this.percent_change_24h = percent_change_24h;
                }

                public double getPercent_change_7d() {
                    return percent_change_7d;
                }

                public void setPercent_change_7d(double percent_change_7d) {
                    this.percent_change_7d = percent_change_7d;
                }
            }
        }
    }

    public static class MetadataBean {
        /**
         * timestamp : 1531982173
         * error : null
         */

        private int timestamp;
        private Object error;

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public Object getError() {
            return error;
        }

        public void setError(Object error) {
            this.error = error;
        }
    }
}
