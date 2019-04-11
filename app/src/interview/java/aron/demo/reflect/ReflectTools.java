package aron.demo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import aron.Utils.ELogger;

/**
 * des:
 * Created by liuwenrong on 2019/4/2
 */
public class ReflectTools {
    public static void test() {
        Class strClass = String.class;
        Class langStrClass = java.lang.String.class;
        Class intClass = int.class;
        Class intArrayClass = int[].class;
        ELogger.d("test----------liuwenrong--2019/4/2--: ");

        try {
            Class testReflect = Class.forName("aron.demo.reflect.TestReflect");
            Object objNoParam = testReflect.newInstance();//如果无参数则直接new
            Class[] paramClass = {int.class, String.class};
            Constructor constructor = testReflect.getDeclaredConstructor(paramClass);
            Object objWithParam = constructor.newInstance(27, "Aron");
            Class[] paramIntClass = {int.class};
            Method methodIntToStr = testReflect.getDeclaredMethod("intToString", paramIntClass);
            methodIntToStr.setAccessible(true);
            Object argList[] = {666};
            invokeMethod(objWithParam, methodIntToStr, argList);

            // 获取私有字段并修改
            Field ageField = testReflect.getDeclaredField("age");
            ageField.setAccessible(true);
            Object fieldObj = ageField.get(objWithParam);
            ageField.setInt(objWithParam, 18);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    private static void invokeMethod(Object objWithParam, Method methodIntToStr, Object[] argList) throws IllegalAccessException, InvocationTargetException {
        Object result = methodIntToStr.invoke(objWithParam, argList);
        if (result instanceof String) {
            String resultStr = (String) result;
            ELogger.d("test----------liuwenrong--2019/4/2--: resultStr = " + resultStr);
        }
    }
}
