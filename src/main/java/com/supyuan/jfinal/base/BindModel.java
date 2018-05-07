package com.supyuan.jfinal.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.StrKit;

public class BindModel {
	
	private static <T> T createInstance(Class<T> objClass) {
		try {
			return objClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final <T> T getBindModel(Class<T> modelClass, String modelName, HttpServletRequest request, boolean skipConvertError) {
		T cls = createInstance(modelClass);
		String modelNameAndDot = StrKit.notBlank(modelName) ? modelName + "." : "";
		Map<String, String[]> parasMap = request.getParameterMap();
		Field[] fields = modelClass.getDeclaredFields();
		for (int i=0; i< fields.length;i++) {
			Field field = fields[i];
			String name = field.getName();
			try {
				String value = request.getParameter(modelNameAndDot+name);
				if(StringUtils.isNoneBlank(value)){
					Method method = modelClass.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), field.getType());
					method.invoke(cls, convert(field.getType(), value));
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (skipConvertError == false) {
					throw new RuntimeException("Can not convert parameter: " + name, e);
				}
			}
		}
		return cls;
	}
	
	public static final <T> T getBindModel(Class<T> modelClass, String modelName, HttpServletRequest request) {
		return getBindModel(modelClass, modelName, request, true);
	}
	
	
	public static final <T> T getBindModel(Class<T> modelClass, HttpServletRequest request) {
		return getBindModel(modelClass, "", request, true);
	}

	public static final Object convert(Class<?> type, String s) throws ParseException {
		if (type == String.class) {
			return ("".equals(s) ? null : s);	
		}
		s = s.trim();
		if ("".equals(s)) {	
			return null;
		}
		
		if (type == Integer.class || type == int.class) {
			return Integer.parseInt(s);
		}
		
		if (type == Long.class || type == long.class) {
			return Long.parseLong(s);
		}
		
		if (type == Double.class || type == double.class) {
			return Double.parseDouble(s);
		}
		
		if (type == Float.class || type == float.class) {
			return Float.parseFloat(s);
		}
		
		if (type == Boolean.class || type == boolean.class) {
			String value = s.toLowerCase();
			if ("1".equals(value) || "true".equals(value)) {
				return Boolean.TRUE;
			}
			else if ("0".equals(value) || "false".equals(value)) {
				return Boolean.FALSE;
			}
			else {
				throw new RuntimeException("Can not parse to boolean type of value: " + s);
			}
		}
		
		if (type == java.math.BigDecimal.class) {
			return new java.math.BigDecimal(s);
		}
		
		if (type == java.math.BigInteger.class) {
			return new java.math.BigInteger(s);
		}
		
		if (type == byte[].class) {
			return s.getBytes();
		}
		
		throw new RuntimeException(type.getName() + " can not be converted, please use other type of attributes in your model!");
	}
	
	
	
}
