package com.product;

import com.exception.InvalidProductException;

import java.util.regex.Pattern;

public class Food extends Product {

    public boolean isHot;
    public final double surchargePercentage = 0.1;

    public Food(String id, String name, boolean isHot,int price) throws InvalidProductException {
        super(id, name, price);

        //正则表达式匹配食物ID是否合法
        boolean matches = Pattern.matches("F-\\d{7}", id);
        if (!matches){
            throw new InvalidProductException("提供的产品ID不合法！！！");
        }
        this.isHot = isHot;
    }

    /**
     * 计算购买该食物需要花多少钱，热食需收10%的加热费
     * @return 购买该食物所需金额
     */
    @Override
    public int calculatePrice() {
        if (isHot){
            return (int) Math.round(price*(1+surchargePercentage));

        }else {
            return  price;
        }
    }

    public boolean getHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public double getSurchargePercentage() {
        return surchargePercentage;
    }

    @Override
    public String toString() {
        return "Food{" +
                "isHot=" + isHot +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
