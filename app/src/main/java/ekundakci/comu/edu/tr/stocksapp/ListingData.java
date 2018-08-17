package ekundakci.comu.edu.tr.stocksapp;

import java.util.List;

public class ListingData {

    private MetadataBean metadata;
    private List<DataBean> data;

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MetadataBean {
        /**
         * timestamp : 1531982285
         * num_cryptocurrencies : 1649
         * error : null
         */

        private int timestamp;
        private int num_cryptocurrencies;
        private Object error;

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public int getNum_cryptocurrencies() {
            return num_cryptocurrencies;
        }

        public void setNum_cryptocurrencies(int num_cryptocurrencies) {
            this.num_cryptocurrencies = num_cryptocurrencies;
        }

        public Object getError() {
            return error;
        }

        public void setError(Object error) {
            this.error = error;
        }
    }

    public static class DataBean {
        /**
         * id : 1
         * name : Bitcoin
         * symbol : BTC
         * website_slug : bitcoin
         */

        private int id;
        private String name;
        private String symbol;
        private String website_slug;

        public int getİd() {
            return id;
        }

        public void setİd(int id) {
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
    }
}
