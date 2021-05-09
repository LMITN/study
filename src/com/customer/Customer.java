package com.customer;

import com.exception.InsufficientBalanceException;
import com.exception.InvalidCustomerExcepton;

import java.util.regex.Pattern;

public class Customer {

    public String id;
    public String name;
    public int balance;


    public Customer(String id, String name) throws InvalidCustomerExcepton {
        boolean matches = Pattern.matches("[a-zA-Z1-9]{6}", id);
        if (!matches){
            throw new InvalidCustomerExcepton("您的用户ID不合法！！！");
        }else {
            this.id = id;
            this.name = name;
            this.balance = 0;
        }
    }

    public Customer(String id, String name, int balance) throws InvalidCustomerExcepton {
        boolean matches = Pattern.matches("[a-zA-Z0-9]{6}", id);
        if (!matches || balance<0){
            throw new InvalidCustomerExcepton("您的用户ID不合法或提供了无效余额！！！");
        }else {
            this.id = id;
            this.name = name;
            this.balance = balance;
        }
    }


    public void addFunds(int amount){
        if (amount>0){
            setBalance(getBalance()+amount);
        }
    }

    /**
     * 用来在用户购买商品时，对该用户进行扣除金额操作
     * @param productPrice  需要扣除的金额
     * @return
     * @throws InsufficientBalanceException 如果用户余额不足，则抛出异常
     */
    public int chargeAccount(int productPrice) throws InsufficientBalanceException {
        if (getBalance()>productPrice){
            setBalance(getBalance()-productPrice);
            return productPrice;
        }else {
            throw new InsufficientBalanceException("您的用户余额不足！！！");
        }

    }


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

    public int getBalance() {
        return balance;
    }

    public int setBalance(int balance) {
        this.balance = balance;
        return balance;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
