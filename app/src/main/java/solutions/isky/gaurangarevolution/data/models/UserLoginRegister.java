package solutions.isky.gaurangarevolution.data.models;

import java.io.Serializable;

public class UserLoginRegister implements Serializable {

    private String login="";
    private String phone="";
    private String passw="";
    private String passw2="";
    private int type_reg=0;
    private boolean is_login=true;
    private boolean is_phone_valid=false;
    private boolean is_email_valid=false;

    public boolean isIs_phone_valid() {
        return is_phone_valid;
    }

    public void setIs_phone_valid(boolean is_phone_valid) {
        this.is_phone_valid = is_phone_valid;
    }

    public boolean isIs_email_valid() {
        return is_email_valid;
    }

    public void setIs_email_valid(boolean is_email_valid) {
        this.is_email_valid = is_email_valid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getPassw2() {
        return passw2;
    }

    public void setPassw2(String passw2) {
        this.passw2 = passw2;
    }

    public int getType_reg() {
        return type_reg;
    }

    public void setType_reg(int type_reg) {
        this.type_reg = type_reg;
    }

    public boolean isIs_login() {
        return is_login;
    }

    public void setIs_login(boolean is_login) {
        this.is_login = is_login;
    }
}
