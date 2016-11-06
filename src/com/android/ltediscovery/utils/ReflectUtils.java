package com.android.ltediscovery.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.util.Log;

import com.android.ltediscovery.Constant;

public class ReflectUtils {
    public static Object invoke(Object obj, String methodName) {
        Object result = null;
        try {
            result = obj.getClass().getMethod(methodName).invoke(obj);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String getAllResult(Object obj) {
        StringBuilder builder = new StringBuilder();
        try {
            Method[] methods = obj.getClass().getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("get")
                        && !methodName.startsWith("getClass") &&
                        method.getParameterTypes().length == 0) {
                    builder.append(methodName.substring(3) + ":"
                            + method.invoke(obj)
                            + "\n");
                }
            }
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d(Constant.TAG, "" + builder);
        return builder.toString();
    }
}
