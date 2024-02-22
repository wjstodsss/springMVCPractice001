package com.project.sample;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

// ������ ����Ͽ� HTTP��û�� ó���ϰ�, �ش� ��û�� ó���ϱ� ���� ���÷����� ����Ͽ� �ٸ� Ŭ������ �޼��带
// �������� ȣ���ϰ� ����� ��ȯ�մϴ�. �̸� ���� �� ���ø����̼��� �پ��� ��û�� ó���ϰ� ������ ������ �� �ֽ��ϴ�.

@WebServlet("/myDispatcherServlet")  // http://localhost/ch2/myDispatcherServlet?year=2021&month=10&day=1
// @WebServlet�� ������ URL���Ͽ� �����ϴ� ������̼��Դϴ�.
// �ش� URL�� ���� ��û�� �� ������ ó���մϴ�.
//@Controller + @RequestMapping ��׵��� ������
//@WebServlet ���  ����, ������ Ŭ�������� ��� ���� �׷��� ������ Ŭ������ ���� ������ ��

public class MyDispatcherServlet extends HttpServlet {
	// �׸��� ������ HttpServlet�� �� ��� �޾ƾ� �մϴ�.
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
	// ������ �� service��� �޼����̸��� �������ϰ� �Ű�����(HttpServletRequest request, HttpServletResponse response)�� �־���մϴ�. �����Դϴ�.
	// HTTP��û�� ó���ϴ� service�޼��带 �������̵��մϴ�. �� �޼���� Ŭ���̾�Ʈ�� ������ ��û�� �޾Ƽ� ó���ϰ�, �׿� ���� ������ �����մϴ�.
		
		Map map = request.getParameterMap();
		// HTTP��û�� �Ű������� �� ���·� �����ɴϴ�. �̸� ���� Ŭ���̾�Ʈ�� ������ �Ű������� ó���մϴ�.
		Model  model = null;
		String viewName = "";
		
		try {
			Class clazz = Class.forName("com.project.sample.YoilTellerMVC");
			// Class.forName Ŭ���� �̸��� ����Ͽ� �ش� Ŭ������ �������� �ε�
			// ��Ű�� �ּҸ� �Բ� �ۼ��ϸ� �ش� ��Ű������ Ŭ�����̸��� ���� Ŭ������ �ε�
			// YoilTellerMVC Ŭ������ ã�� �ε��Ͽ� clazz�� �Ҵ�
			
			Object obj = clazz.newInstance();
			// Class.newInstance() Ŭ������ ���� ��ü ����(�⺻������)
			// clazz�� YoilTellerMVC�� �Ҵ� �����Ƿ� yoilTellerMVC��ü �����Ͽ� obj�� �Ҵ�
			
      			// 1. main�޼����� ������ ��´�.
			Method main = clazz.getDeclaredMethod("main", int.class, int.class, int.class, Model.class);
			// ������ �޼��� �̸��� �Ű����� ������ �ش��ϴ� �޼��带 �������� ���ɴϴ�.
			// Class.getDeclareMethod(�˻��Ϸ��� �޼����, ���� �Ű������� Ŭ���� ��ü...)
			// Ŭ���� ��ü�� ���÷��ǿ��� �Ű����� ������ ������ �� ���
			// �������� - �����߿� Ŭ������ ������ �˻��ϰ�,
			// ��ü�� �޼��带 ȣ���ϰų� �ʵ忡 �����ϰ� ���ο� ��ü�� �����ϴ� ���� �۾��� �����ϵ��� �մϴ�.
			
			// 2. main�޼����� �Ű����� ���(paramArr)�� �о �޼��� ȣ�⿡ ����� ���� ���(argArr)�� �����.
			Parameter[] paramArr = main.getParameters();
			// �޼����� �Ű����� ������ �����ɴϴ�.
			Object[] argArr = new Object[main.getParameterCount()];
			// �޼���ȣ�⿡ ����� ���� �迭�� �����մϴ�.

			for(int i=0;i<paramArr.length;i++) {
				String paramName = paramArr[i].getName();
				Class  paramType = paramArr[i].getType();
				Object value = map.get(paramName);

				// paramType�߿� Model�� ������, ���� & ���� 
				if(paramType==Model.class) {
					argArr[i] = model = new BindingAwareModelMap();
				} else if(paramType==HttpServletRequest.class) {
					argArr[i] = request;
				} else if(paramType==HttpServletResponse.class) {
					argArr[i] = response;					
				} else if(value != null) {  // map�� paramName�� ������,
					// value�� parameter�� Ÿ���� ���ؼ�, �ٸ��� ��ȯ�ؼ� ���� 
					String strValue = ((String[])value)[0];	// getParameterMap()���� ���� value�� String�迭�̹Ƿ� ��ȯ �ʿ� 
					argArr[i] = convertTo(strValue, paramType);				
				} 
			}
			
			// 3. Controller�� main()�� ȣ�� - YoilTellerMVC.main(int year, int month, int day, Model model)
			viewName = (String)main.invoke(obj, argArr); 	
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		// 4. �ؽ�Ʈ ������ �̿��� rendering
		render(model, viewName, response);			
	} // main
	
	private Object convertTo(Object value, Class type) {
		if(type==null || value==null || type.isInstance(value)) // Ÿ���� ������ �״�� ��ȯ 
			return value;
		
		// Ÿ���� �ٸ���, ��ȯ�ؼ� ��ȯ
		if(String.class.isInstance(value) && type==int.class) { // String -> int
			return Integer.valueOf((String)value);
		} else if(String.class.isInstance(value) && type==double.class) { // String -> double
			return Double.valueOf((String)value);
		}
			
		return value;
	}
		
	private String getResolvedViewName(String viewName) {
		return getServletContext().getRealPath("/WEB-INF/views") +"/"+viewName+".jsp";
	}
	
	private void render(Model model, String viewName, HttpServletResponse response) throws IOException {
		String result = "";
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		// 1. ���� ������ ���پ� �о �ϳ��� ���ڿ��� �����.
		Scanner sc = new Scanner(new File(getResolvedViewName(viewName)), "utf-8");
		
		while(sc.hasNextLine())
			result += sc.nextLine()+ System.lineSeparator();
		
		// 2. model�� map���� ��ȯ 
		Map map = model.asMap();
		
		// 3.key�� �ϳ��� �о template�� ${key}�� value�ٲ۴�.
		Iterator it = map.keySet().iterator();
		
		while(it.hasNext()) {
			String key = (String)it.next();

			// 4. replace()�� key�� value ġȯ�Ѵ�.
			result = result.replace("${"+key+"}", map.get(key)+"");
		}
		
		// 5.������ ����� ����Ѵ�.
		out.println(result);
	}
}