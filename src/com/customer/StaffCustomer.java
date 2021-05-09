package com.customer;

import com.exception.InsufficientBalanceException;
import com.exception.InvalidCustomerExcepton;

public class StaffCustomer extends Customer{
    public  String staff;



    public StaffCustomer(String id, String name, String staff) throws InvalidCustomerExcepton {
        super(id, name);
        this.staff = staff;
    }

    public StaffCustomer(String id, String name, int balance,String staff) throws InvalidCustomerExcepton {
        super(id, name, balance);
        this.staff = staff;
    }

    /**
     * 用来在用户购买商品时，对该用户进行扣除金额操作,
     * CMP员工享有10%的折扣，BIO和MTH员工享有2.5%的折扣
     * @param productPrice  需要扣除的金额
     * @return
     * @throws InsufficientBalanceException
     */
    @Override
    public int chargeAccount(int productPrice) throws InsufficientBalanceException {
        int needMoney;
        switch (staff){
            case "CMP":
                needMoney = (int) Math.round(productPrice*(1-0.1));
                if (getBalance()>needMoney){
                    setBalance(getBalance()-needMoney);
                }else {
                    throw new InsufficientBalanceException("您的用户余额不足！！！");
                }
                return needMoney;
            case  "BIO":
            case  "MTH":
                needMoney = (int)Math.round(productPrice*(1-0.025));
                if (getBalance()>needMoney){
                    setBalance(getBalance()-needMoney);
                }else {
                    throw new InsufficientBalanceException("您的用户余额不足！！！");
                }
                return needMoney;
            default:
                if (getBalance()>productPrice){
                    setBalance(getBalance()-productPrice);
                    return productPrice;
                }else {
                    throw new InsufficientBalanceException("您的用户余额不足！！！");
                }
        }

    }
    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "StaffCustomer{" +
                "staff='" + staff + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
