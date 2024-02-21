package com.project.sample;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.StringJoiner;

public class MethodInfo {
	public static void main(String[] args) throws Exception {
		
		Class clazz = Class.forName("com.project.sample.YoilTeller");
		Object obj = clazz.newInstance();
		// �����ڷ� Ŭ������ ��ü ����
		
		Method[] methodArr = clazz.getDeclaredMethods();
		// ��� �޼��� ������ �����ͼ� �迭�� ����
		
		for(Method m : methodArr) {
			String name = m.getName(); // �޼����� �̸�
			Parameter[] paramArr = m.getParameters(); // �Ű����� ���
			Class returnType = m.getReturnType(); // ��ȯ Ÿ��
			
			StringJoiner paramList = new StringJoiner(",", "(", ")");
			// StringJoiner(������, ���λ�, ���̻�)
			
			for(Parameter param : paramArr) {
				String paramName = param.getName();
				Class  paramType = param.getType();
				
				paramList.add(paramType.getName() + " " + paramName);
			
			}
			
			System.out.printf("%s %s%s%n", returnType.getName(), name, paramList);
		}
		
	}
}
