package com.project.sample;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

public class MethodCall2 {
	public static void main(String[] args) throws Exception {
		Class clazz = Class.forName("com.project.sample.YoilTellerMVC");
		// Class.forName 클래스 이름을 사용하여 해당 클래스를 동적으로 로드
		// 패키지 주소를 함께 작성하면 해당 패키지에서 클래스이름이 같은 클래스를 로드
		// YoilTellerMVC 클래스를 찾아 로드하여 clazz에 할당
		
		Object obj = clazz.newInstance();
		// Object.newInstance() 클래스로 부터 객체 생성(기본생성자로 초기화)
		// clazz에 YoilTellerMVC를 할당 했으므로 YoilTellerMVC 객체를 생성하여 
		// obj에 할당
		
		Method main = clazz.getDeclaredMethod("main", int.class, int.class, int.class, Model.class);
		// Object.getDeclaredMethod()은 클래스나 인터페이스의 메서드 중에서 지정된 이름과 매개변수 유형을 가지고 있는 메서드를 찾습니다.
		// Method를 반환합니다.
		// 만약 검색 메서드가 존재하지 않으면 NoSuchMethodException 발생
		// Object.getDeclaredMethod(검색하려는 메서드명, 매개변수의 클래스 객체...)
		// 클래스 객체는 리플렉션에서 매개변수 유형을 지정할 때 사용
		// 리플랙션 - 실행중에 클래스의 정보를 검사하고, 객체의 메서드를 호출하거나 필드에 접근하고
		// 새로운 객체를 생성하는 등의 작업을 수행하도록 합니다.
		// main메서드를 검색해서 존재하면 main에 할당
		
		
		Model model = new BindingAwareModelMap();
		// BindingAwareModelMap() 모델 객체의 구현 중 하나이며
		// 데이터를 모델에 추가하고 뷰로 전달하는데 사용
		
		System.out.println("[before] model" + model);
		// model에 추가한 데이터가 없으므로 비어있음
		// [before] model{}
		
		String viewName = (String)main.invoke(obj, new Object[] { 2021, 10, 1, model });
		// Method.invoke()는 메서드 호출 시에 필요한 객체명과 필요한 매개변수를 전달
		// 위에서 할당 받은 main메서드에 필요한 객체(YoilTellerMVC객체 obj)와 필요한 매개변수
		// 여기서는 객체배열을 만들어서 매개변수 2021, 10, 1, model을 넣었는데
		// invoke()는 가변인수를 사용하기 때문에 콤마로 구분해서 넣어 직접 전달할 수도 있음
		// YoilTellerMVC의 main()는 model객체에 매개변수를 추가하고 문자열 뷰이름 yoil을 반환합니다.
		
		System.out.println("viewName+" + viewName);
		// viewName+yoil
		
		System.out.println("[after] model =" + model);
		// 매개변수가 model에 추가된 것을 확인
		// [after] model ={year=2021, month=10, day=1, yoil=금}
		
		render(model, viewName);
		// 
	}
	
	static void render(Model model, String viewName) throws IOException {
		// model ={year=2021, month=10, day=1, yoil=금}
		// viewName = yoil
		
		String result = "";
		
		Scanner sc = new Scanner (new File("src/main/webapp/WEB-INF/views/" + viewName + ".jsp"), "utf-8");
		// new Scanner()는 자바에서 표준 입력, 파일, 문자열등 다양한 소스로부터
		// 입력을 읽기 위해 사용되는 클래스입니다.
		// 소스를 읽기 위해 인코딩을 지정할 수 있습니다.
		// new File()은 새 파일을 생성하거나 기존 파일에 대한 참조를 얻을 수 있습니다.
		// 지정된 yoil.jsp파일을 utf-8로 인코딩 하여 읽어와 sc에 할당합니다.
		
		while(sc.hasNextLine()) {
			result += sc.nextLine() + System.lineSeparator();
		}
		// Scanner.hasNextLine()은 객체가 읽을 수 있는 입력 스트림에서 다음에 읽을 줄이 있는지 여부를 확인하여
		// Scanner.nextLine()은 현재위치의 라인을 읽고 다음 호출시 다음 줄에서 호출됩니다.
		// System.lineSeparator()는 현재 플랫폼의 줄 바꿈 문자를 반환하는 메서드입니다.
		// 있으면 true, 그렇지 않으면 false를 반환합니다.
		// 그러니까 yoil.jsp파일의 내용을 모두 result에 문자열로 넣었습니다.
		
		Map map = model.asMap();
		// Model.asMap()은 Model 객체에 저장된 속성들을 맵형태로 반환합니다.
		// Spring MVC에서 컨트롤러가 뷰에 데이터를 전달하는 방법 중 하나는 Model 객체를 사용하는 것입니다. 
		// Model 객체는 컨트롤러와 뷰 간에 데이터를 전달하는 데 사용되며, 컨트롤러에서 뷰로 데이터를 전달할 때
		// 이 메서드를 사용하여 데이터를 맵 형태로 가져올 수 있습니다.
		// model ={year=2021, month=10, day=1, yoil=금}
		// 이미 내부적으로 맵형태로 되어있지만
		// 데이터를 편리하게 다루기 위해 Map으로 형변환을 합니다.
		
		Iterator it = map.keySet().iterator();
		// Map.keySet().iterator()는 Map의 키 집합에 대한 반복자를 반환하는 메서드입니다.
		// 이를 통해 Map의 키를 반복적으로 순회하면서 각 키에 대한 처리를 수행할 수 있습니다.
		// Iterator는 Java컬렉션 프레임워크에서 컬렉션 요소를 반복적으로 탐색하기 위한 인터페이스입니다.
		// 이를 통해 컬렉션 내부의 요소를 순차적으로 접근할 수 있습니다.
		// 해당 작업은 우선 jsp파일의 기본형을 갖고
		// 동적인 부분에 변경된 내용을 적용하여 출력하기 위한 작업입니다.
		// 여기서는 jsp에서 EL로 표현된 값부분을 넣어 출력할 수 있습니다.
		
		while(it.hasNext()) {
			String key = (String)it.next();
		
			result = result.replace("${" + key + "}", "" + map.get(key));
		}
		
		System.out.println(result);
	}
			

}
