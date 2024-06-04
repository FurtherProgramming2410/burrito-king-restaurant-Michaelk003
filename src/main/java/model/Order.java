package model;

public class Order {

    private static double burritoPrice =7.00;
    private static double friesPrice = 4.00;
    private static double sodaPrice = 2.50;
    private static double comboPrice = burritoPrice + friesPrice + sodaPrice - 3;

    private static Integer burritoQuantity;
    private static Integer friesQuantity;
    private static Integer sodaQuantity;
    private static Integer comboQuantity;

    private static double total;

    private static double burritoTotal;
    private static double friesTotal;
    private static double sodaTotal;
    private static double comboTotal;


    public Order(Integer burritoQuantity, Integer friesQuantity, Integer sodaQuantity, Integer comboQuantity) {
        this.burritoQuantity = burritoQuantity ;
        this.friesQuantity = friesQuantity; ;
        this.sodaQuantity = sodaQuantity ;
        this.comboQuantity = comboQuantity; ;


    }


    // Getters and Setters
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

    public static int getBurritoQuantity() {
        return burritoQuantity;
    }

    public static int getFriesQuantity() {
        return friesQuantity;
    }

    public static int getSodaQuantity() {
        return sodaQuantity;
    }

    public static int getComboQuantity() {
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

    public static double getTotal() {
        total = burritoTotal + friesTotal + sodaTotal + comboTotal;
        return total;
    }

    public static double getBurritoTotal() {
        burritoTotal = burritoPrice * burritoQuantity;
        return burritoTotal;
    }

    public static double getFriesTotal() {
        friesTotal = friesPrice * friesQuantity;
        return friesTotal;
    }

    public static double getSodaTotal() {
        sodaTotal = sodaPrice * sodaQuantity;
        return sodaTotal;
    }

    public static double getComboTotal() {
        comboTotal = comboPrice * comboQuantity;
        return comboTotal;
    }





}
