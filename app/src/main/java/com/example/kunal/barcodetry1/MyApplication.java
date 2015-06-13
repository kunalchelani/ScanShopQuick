package com.example.kunal.barcodetry1;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by kunal on 14/6/15.
 */
public class MyApplication extends Application {
    public ArrayList<String> arrayList = new ArrayList<String>();
    public static double totalCart = 0;
    public static double lastPrice;
    public ArrayList<String> getArrayList(){
        return this.arrayList;
    }

    public void setLastPrice(double d){
        lastPrice = d;
    }
    public double getTotalCart(){
        return totalCart;
    }
    public void addToArrayList(String string){
        this.arrayList.add(string);
        totalCart = totalCart +lastPrice;
    }

    public void removeFromArrayList(int i) {
        this.arrayList.remove(i);
    }

}
