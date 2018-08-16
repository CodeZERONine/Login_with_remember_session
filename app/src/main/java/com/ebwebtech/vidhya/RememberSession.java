package com.ebwebtech.vidhya;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class RememberSession {
   private SharedPreferences mSharedPreference;
   private SharedPreferences.Editor mEditor;
   private Context mContext;
   private final static String PREF_NAME="rememberPreference";
   private final static String USER_NAME="username";
   private final static String PASSWORD="password";
   private final static String SAVE_LOGIN="save_login";

    public RememberSession(Context mContext) {
        this.mContext = mContext;
        mSharedPreference = mContext.getSharedPreferences(PREF_NAME,0);
        mEditor = mSharedPreference.edit();
    }
    public void CreateSession(boolean savelogin, String username, String password){
          mEditor.putBoolean(SAVE_LOGIN,savelogin);
          mEditor.putString(USER_NAME,username);
          mEditor.putString(PASSWORD,password);
          mEditor.commit();
    }
    public void DeleteSession(){
        mEditor.clear();
        mEditor.commit();
    }
    public HashMap<String,String> getUserData(){
         HashMap<String, String> udata= new HashMap<>();
         udata.put(USER_NAME,mSharedPreference.getString(USER_NAME,null));
         udata.put(PASSWORD, mSharedPreference.getString(PASSWORD,null));
         return udata;
    }
    public boolean isSessionCreated(){
            if(mSharedPreference.getBoolean(SAVE_LOGIN,false))
        {
            return true;
        }
        return false;
    }


}
