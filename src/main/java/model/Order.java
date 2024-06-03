package model;

public class Order {

    private static double burritoPrice =7.00;
    private static double friesPrice = 4.00;
    private static double sodaPrice = 2.50;
    private static double comboPrice = burritoPrice + friesPrice + sodaPrice - 3;

    private int burritoQuantity;
    private int friesQuantity;
    private int sodaQuantity;
    private int comboQuantity;

    public Order(Integer burritoQuantity, Integer friesQuantity, Integer sodaQuantity, Integer comboQuantity) {
        this.burritoQuantity = burritoQuantity ;
        this.friesQuantity = friesQuantity; ;
        this.sodaQuantity = sodaQuantity ;
        this.comboQuantity = comboQuantity; ;

        this.burritoPrice = burritoPrice;
    }

    public static double getBurritoPrice() {
        return burritoPrice;
    }

    public static double getFriesPrice() {
        return friesPrice;
    }

    public static double getSodaPrice() {
        return sodaPrice;
    }

    public static double getComboPrice() {
        return comboPrice;
    }

    public int getBurritoQuantity() {
        return burritoQuantity;
    }

    public int getFriesQuantity() {
        return friesQuantity;
    }

    public int getSodaQuantity() {
        return sodaQuantity;
    }

    public int getComboQuantity() {
        return comboQuantity;
    }

    public void setBurritoQuantity(int burritoQuantity) {
        this.burritoQuantity = burritoQuantity;
    }

    public void setFriesQuantity(int friesQuantity) {
        this.friesQuantity = friesQuantity;
    }

    public void setSodaQuantity(int sodaQuantity) {
        this.sodaQuantity = sodaQuantity;
    }

    public void setComboQuantity(int comboQuantity) {
        this.comboQuantity = comboQuantity;
    }



}
