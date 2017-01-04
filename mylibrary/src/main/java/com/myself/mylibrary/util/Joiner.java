package com.myself.mylibrary.util;

/**
 * Created by juude on 15-5-13.
 * copied from https://github.com/JuudeDemos/droidViews/blob/master/app/src/main/java/net/juude/droidviews/utils/Joiner.java
 */

import android.support.annotation.NonNull;

import java.util.Collection;

public class Joiner {
    private String mSeparator;

    public Joiner(@NonNull String separator) {
        mSeparator = separator;
    }

    public String of(@NonNull Object[] args) {
        StringBuilder builder = null;
        for(Object object : args) {
            if(builder == null) {
                builder = new StringBuilder();
            }else {
                builder.append(mSeparator);
            }
            builder.append(object);
        }
        return builder.toString();
    }


    public String of(@NonNull Collection args) {
        StringBuilder builder = null;
        for(Object object : args) {
            if(builder == null) {
                builder = new StringBuilder();
            }else {
                builder.append(mSeparator);
            }
            builder.append(object);
        }
        return builder == null ? "" : builder.toString();
    }
}
