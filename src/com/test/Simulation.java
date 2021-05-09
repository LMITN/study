package com.test;

import com.customer.Customer;
import com.customer.StaffCustomer;
import com.customer.StudentCustomer;
import com.exception.InsufficientBalanceException;
import com.exception.InvalidCustomerExcepton;
import com.exception.InvalidProductException;
import com.product.Drink;
import com.product.Food;
import com.product.Product;
import com.shop.SnackShop;

import java.io.*;


public class Simulation {

    public static void main(String[] args) {
        File productFile = new File("resources\\products.txt");
        File customerFile = new File("resources\\customers.txt");
        File transactionFile = new File("resources\\transactions.txt");

        SnackShop snackShop = initaliseShop("myShop", productFile, customerFile);


        simulateShopping(snackShop,transactionFile);


        System.out.println("用户余额中位数："+snackShop.calculateMedianCustomerBalance());
        System.out.println("用户余额小于0的用户量："+snackShop.countNegativeAccounts());
        System.out.println("商品价格最大为："+snackShop.findLargestBasePrice());
        System.out.println("总营业额为："+snackShop.getTurnOver());
    }

    /**
     * 读取并解析customers.txt和products.txt文件，并把其中信息创建成Customer类和Product类
     * @param shopName 商店名称
     * @param productFile products.txt的File类
     * @param customerFile customers.txt的File类
     * @return 返回创建完成的 SnackShop类
     */
    public static SnackShop initaliseShop(String shopName, File productFile, File customerFile){
        Product product;
        Customer customer;
        StaffCustomer staffCustomer;
        StudentCustomer studentCustomer;


        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String[] split = null;

        SnackShop snackShop = new SnackShop(shopName);

        //读取并解析products.txt
        try {
            String line;
            fileReader= new FileReader(productFile);
            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine())!=null){

                 split = line.split("@");

                if (split[0].startsWith("F")){
                    if("hot".equals(split[2])){
                        product = new Food(split[0],split[1],true,Integer.parseInt(split[3]));
                        snackShop.addProduct(product);
//                        System.out.println(product);
                    }else {
                        product = new Food(split[0],split[1],false,Integer.parseInt(split[3]));
                        snackShop.addProduct(product);
//                        System.out.println(product);
                    }
                }else if(split[0].startsWith("D")){
                    product = new Drink(split[0],split[1],split[2],Integer.parseInt(split[3]));
                    snackShop.addProduct(product);
//                    System.out.println(product);
                }
            }

        } catch (Exception e) {
            if(e instanceof InvalidProductException){
                System.out.println("您用来创建产品的ID"+":"+split[0]+"无效");
            }else {
                e.printStackTrace();
            }
        }finally {
            try {
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //读取并解析customers.txt
        try {
            String line;
            fileReader = new FileReader(customerFile);
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                try{
                    split = line.split("#");

                    if (split.length == 3) {
                        customer = new Customer(split[0], split[1], Integer.parseInt(split[2]));
                        snackShop.addCustomer(customer);
//                        System.out.println(customer);
                    } else {
                        if ("STUDENT".equals(split[3])) {
                            studentCustomer = new StudentCustomer(split[0], split[1], Integer.parseInt(split[2]));
                            snackShop.addCustomer(studentCustomer);
//                            System.out.println(studentCustomer);
                        } else if ("STAFF".equals(split[3]) && split.length == 5) {
                            staffCustomer = new StaffCustomer(split[0], split[1], Integer.parseInt(split[2]), split[4]);
                            snackShop.addCustomer(staffCustomer);
//                            System.out.println(staffCustomer);
                        } else {
                            staffCustomer = new StaffCustomer(split[0], split[1], Integer.parseInt(split[2]), "");
                            snackShop.addCustomer(staffCustomer);
//                            System.out.println(staffCustomer);
                        }
                    }
                }catch (Exception e){
                    if (e instanceof InvalidCustomerExcepton){
                        System.out.println("您用来创建用户的ID"+":"+split[0]+"无效或余额为负");
                    }else if(e instanceof InsufficientBalanceException){
                        System.out.println(e.getMessage());
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return snackShop;
    }


    /**
     * 解析transactions.txt中的指令，并执行。
     * @param shop
     * @param transactionFile
     */
    public static void simulateShopping(SnackShop shop, File transactionFile){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String[] split = null;
        try{
            String line;
            fileReader = new FileReader(transactionFile);
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                try{
                    split = line.split(",");

                    switch (split[0]){
                        case "PURCHASE":
                            int i = shop.procesPurchase(split[1],split[2]);
                            System.out.println("用户："+split[1]+",购买了"+shop.getProduct(split[2]).getName()+",花费了"+i+",余额："+shop.getCustomer(split[1]).getBalance());
                            break;
                        case "ADD_FUNDS":
                            shop.getCustomer(split[1]).addFunds(Integer.parseInt(split[2]));
                            System.out.println("用户："+shop.getCustomer(split[1]).getId()+",充值了"+split[2]);
                            break;
                        case "NEW_CUSTOMER":
                            if (split.length == 4) {
                                Customer customer = new Customer(split[0], split[1], Integer.parseInt(split[2]));
                                shop.addCustomer(customer);
                                System.out.println("添加新用户："+customer.getId()+","+customer.getName()+","+customer.getBalance());
                            } else {
                                if ("STUDENT".equals(split[4])) {
                                    StudentCustomer studentCustomer = new StudentCustomer(split[1], split[2], Integer.parseInt(split[3]));
                                    shop.addCustomer(studentCustomer);
                                    System.out.println("添加新用户："+studentCustomer.getId()+","+studentCustomer.getName()+","+studentCustomer.getBalance());
                                } else if ("STAFF".equals(split[3]) && split.length == 6) {
                                    StaffCustomer staffCustomer = new StaffCustomer(split[1], split[2], Integer.parseInt(split[5]), split[4]);
                                    shop.addCustomer(staffCustomer);
                                    System.out.println("添加新用户："+staffCustomer.getId()+","+staffCustomer.getName()+","+staffCustomer.getStaff()+","+staffCustomer.getBalance());
                                } else {
                                    StaffCustomer staffCustomer = new StaffCustomer(split[1], split[2], Integer.parseInt(split[4]), "");
                                    shop.addCustomer(staffCustomer);
                                    System.out.println("添加新用户："+staffCustomer.getId()+","+staffCustomer.getName()+","+staffCustomer.getBalance());
                                }
                            }
                    }
                }catch (Exception e){
                    if (e instanceof InsufficientBalanceException){
                        System.out.println("用户："+split[1]+"余额不足，无法购买！！！");
                    }else if(e instanceof InvalidProductException){
                        System.out.println("您购买的产品ID:"+split[2]+"不存在！！！");
                    }else if(e instanceof InvalidCustomerExcepton){
                        System.out.println("用户:"+split[1]+"不存在!!!");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}

