package company.greatapp.moneycircle.tools;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.UUID;

import company.greatapp.moneycircle.constants.C;

/**
 * Created by gyanendra.sirohi on 6/29/2015.
 */
public class Tools {

    public static String generateUniqueId() {
        String uid = UUID.randomUUID().toString();
        return "NEW-"+uid;
    }


    public static String getFormatedNumber(String number) {
        int length = number.length();
        if(length < 10) return null;
        String plain = "";
        int len = 0;
        for (int i = length-1; i >= 0 && len < 10;i--){
            char ch = number.charAt(i);
            if(TextUtils.isDigitsOnly("" + ch)){
                plain = ch+plain;
                len++;
            }
        }
        return plain;
    }

    public static String getSplitableString(ArrayList<String> list) {
        if(list == null) return null;
        String result = C.DIVIDER;
        for(String s : list) {
            result = result + s + C.DIVIDER;
        }
        return result;
    }


}
