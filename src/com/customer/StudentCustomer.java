package com.customer;

import com.exception.InsufficientBalanceException;
import com.exception.InvalidCustomerExcepton;

public class StudentCustomer extends Customer{

    public StudentCustomer(String id, String name) throws InvalidCustomerExcepton {
        super(id, name);
    }

    public StudentCustomer(String id, String name, int balance) throws InvalidCustomerExcepton {
        super(id, name, balance);
    }

    /**
     * 用来在用户购买商品时，对该用户进行扣除金额操作,
     * 学生用户享有5%的折扣，并且允许透支500便士的余额
     * @param productPrice  需要扣除的金额
     * @return
     * @throws InsufficientBalanceException
     */
    @Override
    public int chargeAccount(int productPrice) throws InsufficientBalanceException {
        int needMoney = (int) Math.round(productPrice*(1-0.05));
        if ((getBalance()-needMoney)>=-500){
            setBalance(getBalance()-needMoney);
            return needMoney;
        }else {
            throw new InsufficientBalanceException("您的用户余额不足！！！");
        }
    }

    @Override
    public String toString() {
        return "StudentCustomer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
