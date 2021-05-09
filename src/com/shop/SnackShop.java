package com.shop;

import com.customer.Customer;
import com.exception.InsufficientBalanceException;
import com.exception.InvalidCustomerExcepton;
import com.exception.InvalidProductException;
import com.product.Product;
import org.junit.Test;

import java.util.*;

public class SnackShop {



    public String name;
    public long turnOver = 0;
    public Map<String,Product> products = new HashMap<>();
    public Map<String,Customer> customers = new HashMap<>();

    public SnackShop(String name) {
        this.name = name;
    }

    /**
     * 给商店中注册新用户
     * @param customer:需要注册的新用户
     */
    public void addCustomer(Customer customer){
        customers.put(customer.getId(),customer);
    }

    /**
     * 给商店中注册新产品
     * @param product ：需要注册的新产品
     */
    public void addProduct(Product product){
        products.put(product.getId(),product);
    }

    /**
     * 通过用户的ID查询用户
     * @param customerID ：需要查询的用户ID
     * @return 查询到的用户
     * @throws InvalidCustomerExcepton 如果没有查到用户，则抛出异常
     */
    public Customer getCustomer(String customerID) throws InvalidCustomerExcepton {
        if (customers.containsKey(customerID)){
            return customers.get(customerID);
        }else {
            throw new InvalidCustomerExcepton("用户不存在！！！");
        }

    }


    /**
     * 通过商品ID查询商品
     * @param productID ：需要查询的商品ID
     * @return 查询到的商品
     * @throws InvalidProductException 如果没有查到，则抛出异常
     */
    public Product getProduct(String productID) throws InvalidProductException {
        if (products.containsKey(productID)){
            return products.get(productID);
        }else {
            throw new InvalidProductException("产品不存在！！！");
        }
    }


    /**
     * 实现用户购买商品流程
     * @param customerID  购买者ID
     * @param productID   需要购买的商品ID
     * @return  如果购买成功，返回 true，否则抛出异常
     * @throws InsufficientBalanceException
     * @throws InvalidCustomerExcepton
     * @throws InvalidProductException
     */
    public int procesPurchase (String customerID, String productID) throws InsufficientBalanceException, InvalidCustomerExcepton, InvalidProductException {
        try {
            int sales = getCustomer(customerID).chargeAccount(getProduct(productID).calculatePrice());
            this.turnOver = this.turnOver+sales;
            return sales;
        } catch (InsufficientBalanceException e) {
            throw new InsufficientBalanceException("您的用户余额不足，无法购买！！！");
        } catch (InvalidCustomerExcepton e) {
            throw new InvalidCustomerExcepton("提供的用户ID不存在！！！");
        } catch (InvalidProductException e) {
            throw new InvalidProductException("您购买的产品ID不存在！！！");
        }
    }

    /**
     * 查询商店中最贵的商品
     * @return 返回最贵商品的价格
     */
    public int findLargestBasePrice(){
        int largestBasePrice = 0;
        for (Product product : this.products.values() ) {
            if (largestBasePrice<product.getPrice()){
                largestBasePrice= product.getPrice();
            }
        }
        return largestBasePrice;
    }


    /**
     * 计算注册用户中余额小于0的用户量
     * @return  余额小于0的用户数量
     */
    public int countNegativeAccounts(){
        int lessThan = 0;
        for (Customer customer : this.customers.values() ) {
            if (customer.getBalance()<0){
                lessThan = lessThan+1;
            }
        }
        return lessThan;
    }

    /**
     * 计算所有用户余额的中位数
     * @return 返回中位数
     */
    public double  calculateMedianCustomerBalance(){

        //一个容器，用来存储所有用户的余额
        List<Integer> list =  new ArrayList<>();

        //拿到所有用户的余额，存入List中
        for (Customer customer : this.customers.values() ) {
            list.add(customer.getBalance());
        }

        //Collections.sort(list);

        //通过冒泡排序，将List中的余额按从小到大排序
        for (int i = 1; i < list.size(); i++) {  //第一层for循环,用来控制冒泡的次数
            for (int j = 0; j < list.size()-1; j++) { //第二层for循环,用来控制冒泡一层层到最后
                //如果前一个数比后一个数大,两者调换 ,意味着泡泡向上走了一层
                if (list.get(j) > list.get(j+1)){
                    int temp = list.get(j) ;
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                }
            }
        }

        //计算List中的中位数
        if ((list.size() % 2) ==0){
            return (list.get((list.size()/2)-1)+list.get(list.size()/2))/2.0;
        }else {
            return list.get(((list.size()-1)/2));
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(long turnOver) {
        this.turnOver = turnOver;
    }

    @Override
    public String toString() {
        return "SnackShop{" +
                "name='" + name + '\'' +
                ", turnOver=" + turnOver +
                ", products=" + products +
                ", customers=" + customers +
                '}';
    }

}
