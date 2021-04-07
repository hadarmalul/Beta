package com.example.beta;

public class IncomesC {

    private String Itype, Idate, Iprice, Imonth, Iuid;

    public IncomesC() {

    }

    public IncomesC(String Itype, String Idate, String Iprice, String Imonth, String Iuid) {

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

    public String getIprice() {
        return Iprice;
    }

    public void setIprice(String iprice) {
        Iprice = iprice;
    }

    public String getImonth() {
        return Imonth;
    }

    public void setImonth(String Imonth) {
        this.Imonth = Imonth;
    }
}
