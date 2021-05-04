package com.example.beta;

import java.io.Serializable;

public class expensesC implements Serializable {
    
    private String Etype, Edate, Euid;
    private int Eprice, Emonth;
    
    public expensesC() {
        
    }

    public expensesC(String Etype, String Edate, int Eprice,int Emonth, String Euid) {

        this.Etype = Etype;
        this.Edate = Edate;
        this.Eprice = Eprice;
        this.Emonth = Emonth;
        this.Euid = Euid;
        
    }


    public String getEtype() {
        return Etype;
    }

    public void setEtype(String etype) {
        Etype = etype;
    }

    public String getEdate() {
        return Edate;
    }

    public void setEdate(String edate) {
        Edate = edate;
    }

    public String getEuid() {
        return Euid;
    }

    public void setEuid(String euid) {
        Euid = euid;
    }

    public int getEprice() {
        return Eprice;
    }

    public void setEprice(int eprice) {
        Eprice = eprice;
    }

    public int getEmonth() {
        return Emonth;
    }

    public void setEmonth(int emonth) {
        Emonth = emonth;
    }
}

