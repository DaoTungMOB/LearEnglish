package com.dev.testenglish.database;

import android.content.Context;

import com.dev.testenglish.model.MyObjectBox;

import io.objectbox.BoxStore;

public class ObjectBox {
   private static BoxStore boxStore;

   public static void init(Context context) {
      boxStore = MyObjectBox.builder()
              .androidContext(context.getApplicationContext())
              .build();
   }

   public static BoxStore get() { return boxStore; }
}
