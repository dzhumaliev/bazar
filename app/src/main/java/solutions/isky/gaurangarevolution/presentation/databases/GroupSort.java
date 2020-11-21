package solutions.isky.gaurangarevolution.presentation.databases;

import android.content.Context;

import solutions.isky.gaurangarevolution.R;

/**
 * Created by sergey on 21.03.18.
 */

public class GroupSort {
    public static String newest = "publicated_less";
    public static String cheapest = "price_more";
    public static String expensive = "price_less";
    static GroupSort groupSort;
    static Context context;
    private GroupSort() {
    }

    public static GroupSort getInstance(Context ctx) {
        context=ctx;
        if(groupSort==null){
            groupSort=new GroupSort();
        }
        return groupSort;
    }

    public String getText(String s) {
        if(s.equalsIgnoreCase(newest)){
            return context.getString(R.string.newest)  ;
        }else if(s.equalsIgnoreCase(cheapest)){
            return context.getString(R.string.cheapest)  ;
        }else if(s.equalsIgnoreCase(expensive)){
            return context.getString(R.string.expensive)  ;
        }else{
            return context.getString(R.string.newest);
        }

    }
}
