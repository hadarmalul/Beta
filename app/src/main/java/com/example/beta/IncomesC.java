package com.example.beta;

public class IncomesC {

    private String Itype, Idate, Iprice, Iuid;

    public IncomesC() {

    }

    public IncomesC(String Itype, String Idate, String Iprice, String Iuid) {

        this.Itype = Itype;
        this.Idate = Idate;
        this.Iprice = Iprice;
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
}
