package com.gys.android.gys.SignLang;

import android.content.Context;
import android.content.Intent;

public class Ques {
   public void ques(Context context) {
        Intent intent = new Intent(context, QuesLang.class);
        context.startActivity(intent);
    }
}
