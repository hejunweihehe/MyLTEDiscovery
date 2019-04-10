package com.android.ltediscovery.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.widget.TextView;

public class ViewUtils {
    public static void updateText(TextView textView, Object object) {
        try {
            Method[] methods = object.getClass().getMethods();
            StringBuilder builder = new StringBuilder();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") &&
                        method.getParameterTypes().length == 0) {
                    builder.append(methodName.substring(3) + ":"
                            + method.invoke(object)
                            + "\n");
                }
            }
            textView.setText(builder);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static <T> void updateText(TextView textView, List<T> list,
            Class<T> clz) {
        if (list == null) {
            textView.setText("null");
            return;
        }
        try {
            Method[] methods = clz.getMethods();
            StringBuilder builder = new StringBuilder();
            for (T t : list) {
                for (Method method : methods) {
                    String methodName = method.getName();
                    if (methodName.startsWith("get") &&
                            method.getParameterTypes().length == 0) {
                        builder.append(methodName.substring(3) + ":"
                                + method.invoke(t)
                                + "\n");
                    }
                }
                builder.append("\n");
            }
            textView.setText(builder);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
