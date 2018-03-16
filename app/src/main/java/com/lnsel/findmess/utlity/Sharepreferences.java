package com.lnsel.findmess.utlity;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lnsel.findmess.SetterGetter.UserInfo;


public class Sharepreferences {


	private static String preferanceRemember = "RememberMe";
	private static String preferanceSaveUserInfo = "SaveUserInfo";


	/*public static void setRememberMe(Context context,String userName , String password , int remember)
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceRemember, context.MODE_PRIVATE); 
		Editor editor = pref.edit(); 

		editor.putString("userName", userName); 
		editor.putString("password",password); 
		editor.putInt("remember", remember);   

		editor.commit();
	}

	public static RememberData getRememberMe(Context context)
	{
		RememberData rememberData = new RememberData();

		SharedPreferences pref = context.getSharedPreferences(preferanceRemember, context.MODE_PRIVATE);

		rememberData.setUserName(pref.getString("userName", ""));
		rememberData.setPassword(pref.getString("password", ""));
		rememberData.setRemember(pref.getInt("remember", 0)); 

		return rememberData; 
	}*/


	public static void setUserinfo(Context context,String userid,String username )
	{
		SharedPreferences pref = context.getSharedPreferences(preferanceSaveUserInfo, context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString("userid",userid);
		editor.putString("username",username);

		editor.commit();
	}

	public static UserInfo getUserinfo(Context context)
	{
		UserInfo userInfo = new UserInfo();
		SharedPreferences pref = context.getSharedPreferences(preferanceSaveUserInfo, context.MODE_PRIVATE);

		userInfo.setUserId(pref.getString("userid", ""));
		userInfo.setUserName(pref.getString("username", ""));

		return userInfo;
	}

}
