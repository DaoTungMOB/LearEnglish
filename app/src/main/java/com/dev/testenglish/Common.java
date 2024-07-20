package com.dev.testenglish;

import android.content.Context;
import android.content.SharedPreferences;

public class Common {
   // Khóa cho SharedPreferences
   public static String SHARED_PREFERENCES_KEY="SHARED_PREFERENCES_KEY";
   public static String HISTORY_POINT="HISTORY_POINT";
   public static String CREATE_DATABASE="CREATE_DATABASE";
   public static String IS_LOGIN="IS_LOGIN";
   // Phương thức để ghi một chuỗi vào SharedPreference
   public static void writeString(Context context, final String KEY, String value) {
      SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).edit();
      editor.putString(KEY, value);
      editor.commit();
   }
   public static String readString(Context context, final String KEY) {
      return context.getSharedPreferences(SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(KEY, null);
   }
   public static void putBoolean(Context context, final String KEY, boolean value) {
      SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).edit();
      editor.putBoolean(KEY, value);
      editor.commit();
   }
   public static boolean getBoolean(Context context, final String KEY) {
      return context.getSharedPreferences(SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getBoolean(KEY, false);
   }
}
