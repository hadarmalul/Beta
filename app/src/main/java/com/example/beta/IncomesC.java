package com.example.beta;

import java.io.Serializable;

public class IncomesC implements Serializable {

    private String Itype, Idate, Iuid;
    private int  Iprice, Imonth;

    public IncomesC() {

    }

    public IncomesC(String Itype, String Idate, int Iprice, int Imonth, String Iuid) {

        this.Itype = Itype;
        this.Idate = Idate;
        this.Iprice = Iprice;
        this.Imonth = Imonth;
        this.Iuid = Iuid;

    }

    public String getItype() {
        return Itype;
    }

    public void setItype(String itype) {
        Itype = itype;
    }

    public String getIdate() {
        return Idate;
    }

    public void setIdate(String idate) {
        Idate = idate;
    }

    public String getIuid() {
        return Iuid;
    }

    public void setIuid(String iuid) {
        Iuid = iuid;
    }

    public int getIprice() {
        return Iprice;
    }

    public void setIprice(int iprice) {
        Iprice = iprice;
    }

    public int getImonth() {
        return Imonth;
    }

    public void setImonth(int Imonth) {
        this.Imonth = Imonth;
    }
}
