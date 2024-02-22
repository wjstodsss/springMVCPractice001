package com.project.sample;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

public class MethodCall3 {
	// �Ű������� �������� �����Ͽ� �����ϱ� ���� 
	// Parameter ��ü�� ����Ͽ� �Ű����� Ÿ�� �� �̸��� Ȯ���� �Ŀ� �ش��ϴ� ���� �����մϴ�.
	// �Ű������� �����ϴ� �������� Model ��ü�� �ʿ��� ��쿡�� �����ϰ� �����մϴ�.
	public static void main(String[] args) throws Exception{
		// 1. ��û�� �� ������ �� - request.getParameterMap();
		Map map = new HashMap();
		map.put("year", "2021");
		map.put("month", "10");
		map.put("day", "1");

		Model model = null;
		Class clazz = Class.forName("com.project.sample.YoilTellerMVC");
		Object obj  = clazz.newInstance();
		
		// YoilTellerMVC.main(int year, int month, int day, Model model)
		Method main = clazz.getDeclaredMethod("main", int.class, int.class, int.class, Model.class);
		
		// ��ü �迭�� �������� ���� ��û�Ҷ� ���� ������ ����
		Parameter[] paramArr = main.getParameters(); // �Ű����� ����� �����´�. 
		Object[] argArr = new Object[main.getParameterCount()]; // �Ű����� ������ ���� ���� ������Ʈ �迭�� ����
		
		for(int i=0;i<paramArr.length;i++) {
			String paramName = paramArr[i].getName();
			Class  paramType = paramArr[i].getType();
			Object value = map.get(paramName); // map���� ��ã���� value�� null

			// paramType�߿� Model�� ������, ���� & ���� 
			if(paramType==Model.class) {
				argArr[i] = model = new BindingAwareModelMap(); 
			} else if(value != null) {  // map�� paramName�� ������,
				// value�� parameter�� Ÿ���� ���ؼ�, �ٸ��� ��ȯ�ؼ� ����  
				argArr[i] = convertTo(value, paramType);	
				// convertTo() ���� Ÿ�԰� �ٸ� ��� �ڵ� ��ȯ�� ���� ���
				// �ؿ� ���� �Ǿ� �ֽ��ϴ�.
			} 
		}
		System.out.println("paramArr="+Arrays.toString(paramArr));
		// paramArr=[int year, int month, int day, org.springframework.ui.Model model]
		System.out.println("argArr="+Arrays.toString(argArr));
		
		
		// Controller�� main()�� ȣ�� - YoilTellerMVC.main(int year, int month, int day, Model model)
		String viewName = (String)main.invoke(obj, argArr); 	
		System.out.println("viewName="+viewName);	
		
		// Model�� ������ ��� 
		System.out.println("[after] model="+model);
				
		// �ؽ�Ʈ ������ �̿��� rendering
		render(model, viewName);			
	} // main
	
	private static Object convertTo(Object value, Class type) {
		if(type==null || value==null || type.isInstance(value))
		// type�� �־��� ��value�� Ŭ������ ������ Ȯ���մϴ�. ���ర�ٸ�, ��ȯ�� �ʿ䰡 �����Ƿ� �״�� ��ȯ�մϴ�.
			return value;

		// Ÿ���� �ٸ���, ��ȯ�ؼ� ��ȯ
		if(String.class.isInstance(value) && type==int.class) { 
			// �ִ��� ���� ���ڿ����� Ȯ���մϴ�.
			return Integer.valueOf((String)value);
		} else if(String.class.isInstance(value) && type==double.class) { // String -> double
			return Double.valueOf((String)value);
		}
			
		return value;
		
		// �� �޼���� �־��� ���� �� Ÿ���� null�̰ų� ��ȯ�� �ʿ䰡 ���� ��쿡�� �״�� ��ȯ�մϴ�.
		// �׷��� ���� ��쿡�� ���ڿ� ���� �־��� Ÿ������ ��ȯ�Ͽ� ��ȯ�մϴ�.
		// �̷��� ������� �پ��� Ÿ�� ���� ������ ������ �� �ֽ��ϴ�.
	}
	
	
	private static void render(Model model, String viewName) throws IOException {
		String result = "";
		
		// 1. ���� ������ ���پ� �о �ϳ��� ���ڿ��� �����.
		Scanner sc = new Scanner(new File("src/main/webapp/WEB-INF/views/"+viewName+".jsp"), "utf-8");
		
		while(sc.hasNextLine())
			result += sc.nextLine()+ System.lineSeparator();
		
		// 2. model�� map���� ��ȯ 
		Map map = model.asMap();
		
		// 3.key�� �ϳ��� �о template�� ${key}�� value�ٲ۴�.
		Iterator it = map.keySet().iterator();
		
		while(it.hasNext()) {
			String key = (String)it.next();

			// 4. replace()�� key�� value ġȯ�Ѵ�.
			result = result.replace("${"+key+"}", ""+map.get(key));
		}
		
		// 5.������ ����� ����Ѵ�.
		System.out.println(result);
	}
}