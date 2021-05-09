package com.product;

import com.exception.InvalidProductException;

import java.util.regex.Pattern;

public class Drink extends Product {

    public String sugarContent;

    public Drink(String id, String name,String sugarContent,int price) throws InvalidProductException {
        super(id, name, price);
        boolean matches = Pattern.matches("D-\\d{7}", id);
        if (!matches){
            throw new InvalidProductException("提供的产品ID不合法！！！");
        }
        this.sugarContent = sugarContent;
    }

    public Drink(String id, String name, int price) throws InvalidProductException {
        super(id, name, price);
        boolean matches = Pattern.matches("D-\\d{7}", id);
        if (!matches){
            throw new InvalidProductException("提供的产品ID不合法！！！");
        }
        this.sugarContent = "none";
    }


    /**
     * 计算购买该饮品所需金额，如果含糖量高需要加24便士糖税，含糖量低收18便士
     * @return
     */
    @Override
    public int calculatePrice() {

        switch (sugarContent){
            case "high":
                return (price+24);
            case "low":
                return (price+18);
            default:
                return price;
        }
    }

    public String getSugarContent() {
        return sugarContent;
    }

    public void setSugarContent(String sugarContent) {
        this.sugarContent = sugarContent;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "sugarContent='" + sugarContent + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
