package com.product;

import com.exception.InvalidProductException;

import java.util.regex.Pattern;

public abstract class Product {
//    产品ID，名称和基本价格
    public String id;
    public String name;
    public int price;

    public Product(String id, String name, int price) throws InvalidProductException {

        //通过正则表达式，匹配输入的产品ID是否合法
        // [a-zA-Z]-\d{7}：表示任意字母开头，7位整数结尾，中间以-连接
        boolean matches = Pattern.matches("[a-zA-Z]-\\d{7}", id);

        if (matches && price>=0){
            this.id = id;
            this.price = price;
            this.name = name;
        }else {
            throw new InvalidProductException("提供的产品ID或价格不合法！！！");
        }

    }

    public abstract int calculatePrice();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
