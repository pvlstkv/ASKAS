package com.example.javaserver.testing.n.config;

import java.lang.reflect.Field;

public class FieldCopyUtil {
    public static Object setFields(Object from) {
        Object to = new Object();
        Field[] fields = from.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                Field fieldFrom = from.getClass().getDeclaredField(field.getName());
                Object value = fieldFrom.get(from);
                to.getClass().getDeclaredField(field.getName()).set(to, value);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return to;
    }
}