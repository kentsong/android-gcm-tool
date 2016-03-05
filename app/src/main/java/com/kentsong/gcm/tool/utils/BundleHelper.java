package com.kentsong.gcm.tool.utils;

import android.os.Bundle;

import org.joda.time.LocalDateTime;

import java.util.Iterator;

/**
 * Created by Kent on 2016/3/4.
 */
public class BundleHelper {

    public static String toString(Bundle bundle) {
        StringBuffer sb = new StringBuffer();
        sb.append(LocalDateTime.now().toString("yyyy/MM/dd HH:mm:ss")+"\n");
        sb.append("{\n");

        Iterator<String> it = bundle.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            sb.append("  "+key+"="+bundle.get(key)+"\n");
        }
        sb.append("}\n");

        return sb.toString();
    }
}
