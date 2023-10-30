package settings.bot;

import java.util.Date;

public class CurrencyRate {
    private String baseCurrencyName;
    private String targetCurrencyName;
    private int baseCurrencyCode;
    private int targetCurrencyCode;
    private float exchangeRateBuy;
    private float exchangeRateSell;
    private Date dateCollect;
    public CurrencyRate(String baseCurrencyName, String targetCurrencyName, int baseCurrencyCode,int targetCurrencyCode, float exchangeRateBuy,float exchangeRateSell, Date dateCollect) {
        this.baseCurrencyName = baseCurrencyName;
        this.targetCurrencyName = targetCurrencyName;
        this.baseCurrencyCode = baseCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.exchangeRateBuy = exchangeRateBuy;
        this.exchangeRateSell = exchangeRateSell;
        this.dateCollect = dateCollect;
    }
    public String getBaseCurrencyName() {
        return baseCurrencyName;
    }
    public void setBaseCurrencyName(String baseCurrencyName) {
        this.baseCurrencyName = baseCurrencyName;
    }
    public String getTargetCurrencyName() {
        return targetCurrencyName;
    }
    public void setTargetCurrencyName(String targetCurrencyName) {
        this.targetCurrencyName = targetCurrencyName;
    }
    public int getBaseCurrencyCode() {
        return baseCurrencyCode;
    }
    public void setBaseCurrencyCode(int baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }
    public int getTargetCurrencyCode() {
        return targetCurrencyCode;
    }
    public void setTargetCurrencyCode(int targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }
    public float getExchangeRateBuy() {
        return exchangeRateBuy;
    }
    public void setExchangeRateBuy(float exchangeRateBuy) {
        this.exchangeRateBuy = exchangeRateBuy;
    }
    public float getExchangeRateSell() {
        return exchangeRateSell;
    }
    public void setExchangeRateSell(float exchangeRateSell) {
        this.exchangeRateSell = exchangeRateSell;
    }
    public Date getDateCollect() {
        return dateCollect;
    }
    public void setDateCollect(Date dateCollect) {
        this.dateCollect = dateCollect;
    }
}
