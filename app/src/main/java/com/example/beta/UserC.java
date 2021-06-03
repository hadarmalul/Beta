package com.example.beta;

import java.io.Serializable;

public class UserC implements Serializable {

        private String name, pass, mail, desc, uid;

        public UserC(){

        }


        public UserC(String name, String pass, String mail, String desc, String uid){
            this.name=name;
            this.pass=pass;
            this.mail=mail;
            this.desc=desc;
            this.uid=uid;
        }



        public String getname(){return name;}
        public void setname(String name){this.name=name;}

        public String getpass(){return pass;}
        public void setpass(String pass){this.pass=pass;}

        public String getmail(){return mail;}
        public void setmail(String mail){this.mail=mail;}

        public String getdesc() {
            return desc;
        }
        public void setdesc(String desc) { this.desc = desc; }

        public String getuid(){return uid;}
        public void setuid(String uid){this.uid=uid;}

    }




