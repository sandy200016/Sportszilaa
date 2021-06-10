package com.example.sportszilla;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation {

    public static boolean specialCharacter(final String str) {
        String Specialchar = "!@#$%^&*()-=+/*?|][}{><.,;'':_";
        for (int i = 0; i < str.length(); i++) {
            if (Specialchar.contains(Character.toString(str.charAt(i)))) {
                return true;
            }

        }
        return false;
    }

    public static boolean isNumeric(final String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    public void Msg(Context context,String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public boolean validatePhone(String phone){


        Pattern p=Pattern.compile("[6-9][0-9]{9}");
        Matcher m=p.matcher(phone);
        return (m.find() && m.group().equals(phone));



    }







}
