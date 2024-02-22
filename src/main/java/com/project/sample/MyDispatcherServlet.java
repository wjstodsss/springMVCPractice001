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

// 서블릿을 사용하여 HTTP요청을 처리하고, 해당 요청을 처리하기 위해 리플레션을 사용하여 다른 클래스의 메서드를
// 동적으로 호출하고 결과를 반환합니다. 이를 통해 웨 애플리케이션의 다양한 요청을 처리하고 응답을 생성할 수 있습니다.

@WebServlet("/myDispatcherServlet")  // http://localhost/ch2/myDispatcherServlet?year=2021&month=10&day=1
// @WebServlet은 지정된 URL패턴에 매핑하는 어노테이션입니다.
// 해당 URL에 대한 요청을 이 서블릿이 처리합니다.
//@Controller + @RequestMapping 얘네들은 스프링
//@WebServlet 얘는  서블릿, 서블릿은 클래스에만 사용 가능 그래서 단점이 클래스를 많이 만들어야 함

public class MyDispatcherServlet extends HttpServlet {
	// 그리고 서블릿은 HttpServlet을 꼭 상속 받아야 합니다.
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
	// 서블릿으 꼭 service라는 메서드이름을 가져야하고 매개변수(HttpServletRequest request, HttpServletResponse response)도 있어야합니다. 고정입니다.
	// HTTP요청을 처리하는 service메서드를 오버라이딩합니다. 이 메서드는 클라이언트로 부터의 요청을 받아서 처리하고, 그에 대한 응답을 생성합니다.
		
		Map map = request.getParameterMap();
		// HTTP요청의 매개변수를 맵 형태로 가져옵니다. 이를 통해 클라이언트가 전달한 매개변수를 처리합니다.
		Model  model = null;
		String viewName = "";
		
		try {
			Class clazz = Class.forName("com.project.sample.YoilTellerMVC");
			// Class.forName 클래스 이름을 사용하여 해당 클래스를 동적으로 로드
			// 패키지 주소를 함께 작성하면 해당 패키지에서 클래스이름이 같은 클래스를 로드
			// YoilTellerMVC 클래스를 찾아 로드하여 clazz에 할당
			
			Object obj = clazz.newInstance();
			// Class.newInstance() 클래스로 부터 객체 생성(기본생성자)
			// clazz에 YoilTellerMVC를 할당 했으므로 yoilTellerMVC객체 생성하여 obj에 할당
			
      			// 1. main메서드의 정보를 얻는다.
			Method main = clazz.getDeclaredMethod("main", int.class, int.class, int.class, Model.class);
			// 지정된 메서드 이름과 매개변수 유형에 해당하는 메서드를 동적으로 얻어옵니다.
			// Class.getDeclareMethod(검색하려는 메서드명, 가변 매개변수의 클래스 객체...)
			// 클래스 객체는 리플렉션에서 매개변수 유형을 지정할 때 사용
			// 리를렉션 - 실행중에 클래스의 정보를 검사하고,
			// 객체의 메서드를 호출하거나 필드에 접근하고 새로운 객체를 생성하는 등의 작업을 수행하도록 합니다.
			
			// 2. main메서드의 매개변수 목록(paramArr)을 읽어서 메서드 호출에 사용할 인자 목록(argArr)을 만든다.
			Parameter[] paramArr = main.getParameters();
			// 메서드의 매개변수 정보를 가져옵니다.
			Object[] argArr = new Object[main.getParameterCount()];
			// 메서드호출에 사용할 인자 배열을 생성합니다.

			for(int i=0;i<paramArr.length;i++) {
				String paramName = paramArr[i].getName();
				Class  paramType = paramArr[i].getType();
				Object value = map.get(paramName);

				// paramType중에 Model이 있으면, 생성 & 저장 
				if(paramType==Model.class) {
					argArr[i] = model = new BindingAwareModelMap();
				} else if(paramType==HttpServletRequest.class) {
					argArr[i] = request;
				} else if(paramType==HttpServletResponse.class) {
					argArr[i] = response;					
				} else if(value != null) {  // map에 paramName이 있으면,
					// value와 parameter의 타입을 비교해서, 다르면 변환해서 저장 
					String strValue = ((String[])value)[0];	// getParameterMap()에서 꺼낸 value는 String배열이므로 변환 필요 
					argArr[i] = convertTo(strValue, paramType);				
				} 
			}
			
			// 3. Controller의 main()을 호출 - YoilTellerMVC.main(int year, int month, int day, Model model)
			viewName = (String)main.invoke(obj, argArr); 	
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		// 4. 텍스트 파일을 이용한 rendering
		render(model, viewName, response);			
	} // main
	
	private Object convertTo(Object value, Class type) {
		if(type==null || value==null || type.isInstance(value)) // 타입이 같으면 그대로 반환 
			return value;
		
		// 타입이 다르면, 변환해서 반환
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
		
		// 1. 뷰의 내용을 한줄씩 읽어서 하나의 문자열로 만든다.
		Scanner sc = new Scanner(new File(getResolvedViewName(viewName)), "utf-8");
		
		while(sc.hasNextLine())
			result += sc.nextLine()+ System.lineSeparator();
		
		// 2. model을 map으로 변환 
		Map map = model.asMap();
		
		// 3.key를 하나씩 읽어서 template의 ${key}를 value바꾼다.
		Iterator it = map.keySet().iterator();
		
		while(it.hasNext()) {
			String key = (String)it.next();

			// 4. replace()로 key를 value 치환한다.
			result = result.replace("${"+key+"}", map.get(key)+"");
		}
		
		// 5.렌더링 결과를 출력한다.
		out.println(result);
	}
}