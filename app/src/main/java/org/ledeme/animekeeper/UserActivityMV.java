package org.ledeme.animekeeper;

import android.databinding.ObservableField;

public class UserActivityMV {


    private ObservableField<String> username = new ObservableField<>(LogginActivity.USER_USERNAME);
    private ObservableField<String> passwd = new ObservableField<>("");
    private ObservableField<String> passwdCheck = new ObservableField<>("");

    private String usrNm;
    private String usrPwd;
    private String usrPwdC;

    public String getUsername(){
        return username.get();
    }
    public void setUsername(ObservableField<String> username) {
        this.username = username;
        this.username.notifyChange();
    }

    public String getPasswd(){
        return passwd.get();
    }
    public void setPasswd(ObservableField<String> passwd) {
        this.passwd = passwd;
        this.passwd.notifyChange();
    }

    public String getPasswdCheck(){
        return passwdCheck.get();
    }
    public void setPasswdCheck(ObservableField<String> passwdCheck) {
        this.passwdCheck = passwdCheck;
        this.passwdCheck.notifyChange();
    }

    UserActivityMV(){}

    public void afterUserNameChange(CharSequence s)
    {
        //Log.i("truc", s.toString());
        this.usrNm = s.toString();
    }

    public void afterUserPasswdChange(CharSequence s)
    {
        //Log.i("truc", s.toString());
        this.usrPwd = s.toString();
    }

    public void afterUserPasswdCheckChange(CharSequence s)
    {
        //Log.i("truc", s.toString());
        this.usrPwdC = s.toString();
    }

    public void onClick(){

        UserAccountActivity.MVVM_click(usrNm, usrPwd, usrPwdC);

    }


}
