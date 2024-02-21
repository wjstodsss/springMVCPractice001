package com.project.sample;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.StringJoiner;

public class MethodInfo {
	public static void main(String[] args) throws Exception {
		
		Class clazz = Class.forName("com.project.sample.YoilTeller");
		Object obj = clazz.newInstance();
		// 요일텔러 클래스의 객체 생성
		
		Method[] methodArr = clazz.getDeclaredMethods();
		// 모든 메서드 정보를 가져와서 배열에 저장
		
		for(Method m : methodArr) {
			String name = m.getName(); // 메서드의 이름
			Parameter[] paramArr = m.getParameters(); // 매개변수 목록
			Class returnType = m.getReturnType(); // 반환 타입
			
			StringJoiner paramList = new StringJoiner(",", "(", ")");
			// StringJoiner(구분자, 접두사, 접미사)
			
			for(Parameter param : paramArr) {
				String paramName = param.getName();
				Class  paramType = param.getType();
				
				paramList.add(paramType.getName() + " " + paramName);
			
			}
			
			System.out.printf("%s %s%s%n", returnType.getName(), name, paramList);
		}
		
	}
}
