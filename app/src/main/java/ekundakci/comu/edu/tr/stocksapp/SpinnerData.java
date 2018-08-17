package ekundakci.comu.edu.tr.stocksapp;

public class SpinnerData {

    private int id;
    private String coinName;
    private String coinToConvert;

    public SpinnerData(int id, String coinName, String coinToConvert) {
        this.id = id;
        this.coinName = coinName;
        this.coinToConvert = coinToConvert;
    }

    public int getId() {
        return id;
    }

    public String getCoinName() {
        return coinName;
    }

    public String getCoinToConvert() {
        return coinToConvert;
    }

    @Override
    public String toString() {
        return coinName;
    }
}
