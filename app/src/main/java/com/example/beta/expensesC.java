package com.example.beta;

public class expensesC {
    
    private String Etype, Edate, Eprice,Emonth, Euid;
    
    public expensesC() {
        
    }

    public expensesC(String Etype, String Edate, String Eprice,String Emonth, String Euid) {

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

    public String getEprice() {
        return Eprice;
    }

    public void setEprice(String eprice) {
        Eprice = eprice;
    }

    public String getEmonth() {
        return Emonth;
    }

    public void setEmonth(String emonth) {
        Emonth = emonth;
    }
}

