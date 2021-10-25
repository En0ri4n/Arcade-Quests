package fr.eno.arcadequests.utils;

import java.lang.reflect.*;

public class ReflectUtils
{
    /**
     * Get value from the provided field
     *
     * @param field    The field to get value
     * @param instance The instance of the class
     * @param value    The type of the return value of the field
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Field field, Object instance, Class<T> value)
    {
        try
        {
            return (T) field.get(instance);
        }
        catch(IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Invoke method with specified parameters
     *
     * @param method   The method to invoke
     * @param instance The instance of the class to invoke (null if static method)
     * @param params   The parameters of the method to invoke
     */
    public static void invokeMethod(Method method, Object instance, Object... params)
    {
        try
        {
            method.invoke(instance, params);
        }
        catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param clazz  The class of the method
     * @param name   The Name of the method
     * @param params The class of parameters of the method
     * @return The method
     */
    public static <T> Method getMethodAndSetAccessible(Class<? super T> clazz, String name, Class<?>... params)
    {
        Method method = null;
        try
        {
            method = clazz.getMethod(name, params);
            method.setAccessible(true);
            Field modifiersMethod = Method.class.getDeclaredField("modifiers");
            modifiersMethod.setAccessible(true);
            modifiersMethod.setInt(method, method.getModifiers() & ~Modifier.FINAL);
        }
        catch(IllegalAccessException | NoSuchFieldException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        return method;
    }

    /**
     * Set current static field with a new value
     *
     * @param field    the field to set
     * @param newValue the value to set
     */
    public static void setStaticField(Field field, Object newValue)
    {
        try
        {
            field.setAccessible(true);
            field.set(null, newValue);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set current field with a new value (if its a static field
     *
     * @param field  the field to set
     * @param target instance of the class
     * @param value  value of the field
     */
    public static void setField(Field field, Object target, Object value)
    {
        try
        {
            field.setAccessible(true);
            //MODIFIERS.set(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(target, value);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get field with the provided SRG name in the provided class
     *
     * @param clazz The class of the field
     * @param name  The name of the field
     * @return The field
     */
    public static Field getFieldAndSetAccessible(Class<?> clazz, String name)
    {
        Field field = null;

        try
        {
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return field;
    }
}
