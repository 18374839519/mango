package com.louis.mango.common.utils.javaother;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * java反射工具类 ReflectionUtils
 */

/**
 * ��װ�˷���Ļ�������
 * Object invoke(Object obj, String methodName, Object... args) ִ��ָ�����ĳ����������
 * Object invoke(String className, String methodName,Object... args) 
 * Method getMethod(Class<?> cla, String methodName,Class<?>... parametersType) ��ȡָ���ķ������������෽����
 * Object getFieldValue(Object obj, String fieldName) ��ȡ�ֶ�ֵ
 * Field getField(Class<?> cla, String fieldName) ��ȡָ���ֶ�
 * void setFieldValue(Object obj ,String fieldName,Object val) ����ָ���ֶ�ֵ
 * void setFieldValue(String className,String fieldName,Object val)
 * Class<?> getGenericSuperClass(Class<?> className, int index ) ��ȡ������Ĳ�������  ,index�����Ǵ�0��ʼ��
 * 
 *  ���ڸոհ�java����ѧ�ˣ��˹�����Ϊ��ϰд�ģ��кܶ಻�㻹��ָ�̣�����qq����834259846@qq.com
 * 
 * @author dp
 *
 */
public class ReflectionUtils {

	/**
	 * ͨ��������ִ��ָ���ķ������÷��������Ǹ����еģ�Ҳ���ܵ�ǰ��˽�еģ������ܵõ�������˽�еģ�
	 * 
	 * @param obj
	 *            ����Ҫִ�з�����Ӧ�Ķ���
	 * @param methodName
	 *            Ҫִ�еķ�����
	 * @param args
	 *            ִ�з����Ĳ���
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object invoke(Object obj, String methodName, Object... args){
		// Class���͵�����洢������Ӧ��.class����
		Class[] parametersType = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			parametersType[i] = args[i].getClass();
		}
		
		Method method = getMethod(obj.getClass(), methodName, parametersType);
		
		try {
			return method.invoke(obj, args);
		} catch (IllegalAccessException e) {
			System.out.println("�����е�˽�з������㲻�ɷ���!!!");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡָ���ķ����������ڸ����У� �ӵ�ǰ����ָ��Ҫ�ķ�������û����ѭ����̳еĸ�����ң�һ���ҵ��㷵��
	 * ֻ�д���ĵ�ǰ��ſɿ���˽�еģ������е��򲻿���
	 * 
	 * @param cla
	 *            ��
	 * @param methodName
	 *            ������
	 * @param parametersType
	 *            ��������
	 * @return
	 */
	public static Method getMethod(Class<?> cla, String methodName,
			Class<?>... parametersType) {
		boolean isFirstClass = true;
		for (; cla != Object.class; cla = cla.getSuperclass()) {
			Method method = null;
			try {
				method = cla.getDeclaredMethod(methodName, parametersType);
				if(isFirstClass){
					//�����˽�з����ǲ��ܻ�ȡ��
					method.setAccessible(true);
					isFirstClass = false;
				}
				return method;
			} catch (Exception e) {
				// ��û�ҵ���Ӧ�ķ���ʱ�����쳣�����������������һ��ѭ�������Ҽ̳еĸ����Ƿ��и÷���
			}finally{
				//���������ĵ�һ���������쳣����ô֮���ȡ�Ķ��Ǹ��࣬������ǰ���if��������ִ��
				isFirstClass = false;
			}
		}

		return null;
	}

	/**
	 * ���������invoke������ͨ�����������ִ��ָ���ķ���
	 * 
	 * @param className
	 *            ͨ�����������
	 * @param methodname
	 *            ������
	 * @param args
	 *            ������ڲ���
	 * @return
	 */
	public static Object invoke(String className, String methodName,
			Object... args) {

		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(null == obj){
			return null;
		}
		
		return invoke(obj, methodName, args);
	}

	
	
	
	
	
	
	/**
	 * ��ȡĳ������ָ���ֶε�ֵ�����ֶ��п����ǴӸ����м̳еģ�
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Class<?> cla = obj.getClass();
		Field field = getField(cla, fieldName);
		
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("�����е�˽�����ԣ��㲻�ɷ���!!!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡָ���ֶ� �����ڸ���,�����е�˽�����Բ����Ա��������ʳ���
	 * 
	 * @param cla
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Class<?> cla, String fieldName) {
		boolean isFirstClass = true;
		for (; cla != Object.class; cla = cla.getSuperclass()) {
			Field field = null;
			try {
				field = cla.getDeclaredField(fieldName);
				if(isFirstClass){
					//�����˽�������ǲ��ܻ�ȡ��
					field.setAccessible(true);
					isFirstClass = false;
				}
				return field;
			} catch (Exception e) {
				
			}finally{
				//���������ĵ�һ���������쳣����ô֮���ȡ�Ķ��Ǹ��࣬������ǰ���if��������ִ��
				isFirstClass = false;
			}
		}

		return null;
	}

	
	/**
	 * Ϊĳ����������ָ����ֵ
	 * 
	 * @param obj
	 * @param fieldName
	 * @param val
	 */
	public static void setFieldValue(Object obj ,String fieldName,Object val) {
		Field field = getField(obj.getClass(), fieldName);
		try {
			field.set(obj, val);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("�޷����ø����е�˽������!!!");
			e.printStackTrace();
		}
	}
	
	//���ط���setFieldValue
	@SuppressWarnings("unchecked")
	public static <T> T setFieldValue(String className,String fieldName,Object val){
		T obj = null;
		try {
			obj =  (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			System.out.println("�������󣡣���");
			e.printStackTrace();
		}
		setFieldValue(obj, fieldName, val);
		
		return obj;
	}
	
	
	
	
	
	/**
	 * ��ȡ������Ĳ�������
	 * 
	 * @param className �������Class����
	 * @param index �������͵����������������еķ��Ͳ����ǵ�һ����
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Class<?> getGenericSuperClass(Class<?> className, int index ){
		//Tyep �Ǹ��սӿ�	
		Type type = className.getGenericSuperclass();
		if(!(type instanceof ParameterizedType)){
			return null;
		}
		
		//�ýӿ�ParameterizedType�̳���Type ���ýӿ��е�
		//getActualTypeArguments����������ɻ�÷����е�ʵ�ʲ�������
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type[] args = parameterizedType.getActualTypeArguments();
		
		if(index < 0 || index > args.length){
			return null;
		}
		
		if(null != args){
			Class<?> cla = (Class<?>) args[index];
			return cla;
		}
		
		return null;
	}
}
