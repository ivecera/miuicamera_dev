package com.ss.android.vesdk;

import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class VEJavaCalls {
    private static final String LOG_TAG = "JavaCalls";
    private static final Map<Class<?>, Class<?>> PRIMITIVE_MAP = new HashMap();

    public static class JavaParam<T> {
        public final Class<? extends T> clazz;
        public final T obj;

        public JavaParam(Class<? extends T> cls, T t) {
            this.clazz = cls;
            this.obj = t;
        }
    }

    static {
        PRIMITIVE_MAP.put(Boolean.class, Boolean.TYPE);
        PRIMITIVE_MAP.put(Byte.class, Byte.TYPE);
        PRIMITIVE_MAP.put(Character.class, Character.TYPE);
        PRIMITIVE_MAP.put(Short.class, Short.TYPE);
        PRIMITIVE_MAP.put(Integer.class, Integer.TYPE);
        PRIMITIVE_MAP.put(Float.class, Float.TYPE);
        PRIMITIVE_MAP.put(Long.class, Long.TYPE);
        PRIMITIVE_MAP.put(Double.class, Double.TYPE);
        Map<Class<?>, Class<?>> map = PRIMITIVE_MAP;
        Class<?> cls = Boolean.TYPE;
        map.put(cls, cls);
        Map<Class<?>, Class<?>> map2 = PRIMITIVE_MAP;
        Class<?> cls2 = Byte.TYPE;
        map2.put(cls2, cls2);
        Map<Class<?>, Class<?>> map3 = PRIMITIVE_MAP;
        Class<?> cls3 = Character.TYPE;
        map3.put(cls3, cls3);
        Map<Class<?>, Class<?>> map4 = PRIMITIVE_MAP;
        Class<?> cls4 = Short.TYPE;
        map4.put(cls4, cls4);
        Map<Class<?>, Class<?>> map5 = PRIMITIVE_MAP;
        Class<?> cls5 = Integer.TYPE;
        map5.put(cls5, cls5);
        Map<Class<?>, Class<?>> map6 = PRIMITIVE_MAP;
        Class<?> cls6 = Float.TYPE;
        map6.put(cls6, cls6);
        Map<Class<?>, Class<?>> map7 = PRIMITIVE_MAP;
        Class<?> cls7 = Long.TYPE;
        map7.put(cls7, cls7);
        Map<Class<?>, Class<?>> map8 = PRIMITIVE_MAP;
        Class<?> cls8 = Double.TYPE;
        map8.put(cls8, cls8);
    }

    public static <T> T callMethod(Object obj, String str, Object... objArr) {
        try {
            return callMethodOrThrow(obj, str, objArr);
        } catch (Exception e2) {
            Log.w(LOG_TAG, "Meet exception when call Method '" + str + "' in " + obj, e2);
            return null;
        }
    }

    public static <T> T callMethodOrThrow(Object obj, String str, Object... objArr) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return getDeclaredMethod(obj.getClass(), str, getParameterTypes(objArr)).invoke(obj, getParameters(objArr));
    }

    public static <T> T callStaticMethod(String str, String str2, Object... objArr) {
        try {
            return callStaticMethodOrThrow(Class.forName(str), str2, objArr);
        } catch (Exception e2) {
            Log.w(LOG_TAG, "Meet exception when call Method '" + str2 + "' in " + str, e2);
            return null;
        }
    }

    public static <T> T callStaticMethodOrThrow(Class<?> cls, String str, Object... objArr) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return getDeclaredMethod(cls, str, getParameterTypes(objArr)).invoke(null, getParameters(objArr));
    }

    public static <T> T callStaticMethodOrThrow(String str, String str2, Object... objArr) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return getDeclaredMethod(Class.forName(str), str2, getParameterTypes(objArr)).invoke(null, getParameters(objArr));
    }

    private static boolean compareClassLists(Class<?>[] clsArr, Class<?>[] clsArr2) {
        if (clsArr == null) {
            return clsArr2 == null || clsArr2.length == 0;
        }
        if (clsArr2 == null) {
            return clsArr.length == 0;
        }
        if (clsArr.length != clsArr2.length) {
            return false;
        }
        for (int i = 0; i < clsArr.length; i++) {
            if (!clsArr[i].isAssignableFrom(clsArr2[i]) && (!PRIMITIVE_MAP.containsKey(clsArr[i]) || !PRIMITIVE_MAP.get(clsArr[i]).equals(PRIMITIVE_MAP.get(clsArr2[i])))) {
                return false;
            }
        }
        return true;
    }

    private static Method findMethodByName(Method[] methodArr, String str, Class<?>[] clsArr) {
        if (str != null) {
            for (Method method : methodArr) {
                if (method.getName().equals(str) && compareClassLists(method.getParameterTypes(), clsArr)) {
                    return method;
                }
            }
            return null;
        }
        throw new NullPointerException("Method name must not be null.");
    }

    private static Method getDeclaredMethod(Class<?> cls, String str, Class<?>... clsArr) throws NoSuchMethodException, SecurityException {
        Method findMethodByName = findMethodByName(cls.getDeclaredMethods(), str, clsArr);
        if (findMethodByName != null) {
            findMethodByName.setAccessible(true);
            return findMethodByName;
        } else if (cls.getSuperclass() != null) {
            return getDeclaredMethod(cls.getSuperclass(), str, clsArr);
        } else {
            throw new NoSuchMethodException();
        }
    }

    private static Object getDefaultValue(Class<?> cls) {
        if (Integer.TYPE.equals(cls) || Integer.class.equals(cls) || Byte.TYPE.equals(cls) || Byte.class.equals(cls) || Short.TYPE.equals(cls) || Short.class.equals(cls) || Long.TYPE.equals(cls) || Long.class.equals(cls) || Double.TYPE.equals(cls) || Double.class.equals(cls) || Float.TYPE.equals(cls) || Float.class.equals(cls)) {
            return 0;
        }
        if (Boolean.TYPE.equals(cls) || Boolean.class.equals(cls)) {
            return false;
        }
        return (Character.TYPE.equals(cls) || Character.class.equals(cls)) ? (char) (char) 0 : null;
    }

    public static <T> T getField(Object obj, String str) {
        try {
            return getFieldOrThrow(obj, str);
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static <T> T getFieldOrThrow(Object obj, String str) throws NoSuchFieldException, IllegalAccessException {
        Class<?> cls = obj.getClass();
        Field field = null;
        while (field == null) {
            try {
                field = cls.getDeclaredField(str);
                field.setAccessible(true);
                continue;
            } catch (NoSuchFieldException unused) {
                cls = cls.getSuperclass();
                continue;
            }
            if (cls == null) {
                throw new NoSuchFieldException();
            }
        }
        field.setAccessible(true);
        return field.get(obj);
    }

    private static Class<?>[] getParameterTypes(Object... objArr) {
        if (objArr == null || objArr.length <= 0) {
            return null;
        }
        Class<?>[] clsArr = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj == null || !(obj instanceof JavaParam)) {
                clsArr[i] = obj == null ? null : obj.getClass();
            } else {
                clsArr[i] = ((JavaParam) obj).clazz;
            }
        }
        return clsArr;
    }

    private static Object[] getParameters(Object... objArr) {
        if (objArr == null || objArr.length <= 0) {
            return null;
        }
        Object[] objArr2 = new Object[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj == null || !(obj instanceof JavaParam)) {
                objArr2[i] = obj;
            } else {
                objArr2[i] = ((JavaParam) obj).obj;
            }
        }
        return objArr2;
    }

    public static <T> T newEmptyInstance(Class<?> cls) {
        try {
            return newEmptyInstanceOrThrow(cls);
        } catch (Exception e2) {
            Log.w(LOG_TAG, "Meet exception when make instance as a " + cls.getSimpleName(), e2);
            return null;
        }
    }

    public static <T> T newEmptyInstanceOrThrow(Class<?> cls) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        if (declaredConstructors == null || declaredConstructors.length == 0) {
            throw new IllegalArgumentException("Can't get even one available constructor for " + cls);
        }
        Constructor<?> constructor = declaredConstructors[0];
        constructor.setAccessible(true);
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (parameterTypes == null || parameterTypes.length == 0) {
            return constructor.newInstance(new Object[0]);
        }
        Object[] objArr = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            objArr[i] = getDefaultValue(parameterTypes[i]);
        }
        return constructor.newInstance(objArr);
    }

    public static <T> T newInstance(Class<?> cls, Object... objArr) {
        try {
            return newInstanceOrThrow(cls, objArr);
        } catch (Exception e2) {
            Log.w(LOG_TAG, "Meet exception when make instance as a " + cls.getSimpleName(), e2);
            return null;
        }
    }

    public static Object newInstance(String str, Object... objArr) {
        try {
            return newInstanceOrThrow(str, objArr);
        } catch (Exception e2) {
            Log.w(LOG_TAG, "Meet exception when make instance as a " + str, e2);
            return null;
        }
    }

    public static <T> T newInstanceOrThrow(Class<?> cls, Object... objArr) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return cls.getConstructor(getParameterTypes(objArr)).newInstance(getParameters(objArr));
    }

    public static Object newInstanceOrThrow(String str, Object... objArr) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return newInstanceOrThrow(Class.forName(str), getParameters(objArr));
    }

    public static void setField(Object obj, String str, Object obj2) {
        try {
            setFieldOrThrow(obj, str, obj2);
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }

    public static void setFieldOrThrow(Object obj, String str, Object obj2) throws NoSuchFieldException, IllegalAccessException {
        Class<?> cls = obj.getClass();
        Field field = null;
        while (field == null) {
            try {
                field = cls.getDeclaredField(str);
                continue;
            } catch (NoSuchFieldException unused) {
                cls = cls.getSuperclass();
                continue;
            }
            if (cls == null) {
                throw new NoSuchFieldException();
            }
        }
        field.setAccessible(true);
        field.set(obj, obj2);
    }
}
