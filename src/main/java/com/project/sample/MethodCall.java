package com.project.sample;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class MethodCall {
	public static void main(String[] args) throws Exception {
		HashMap map = new HashMap();
		// map 생성
		System.out.println("before:" + map);
		// map 초기 상태 확인
		
		ModelController mc = new ModelController();
		// 모델 컨트로러 객체 생성
		
		String viewName = mc.main(map);
		// 맵을 매개변수로 받아서
		// 맵에 값을 추가하고
		// 맵은 원래 main거니까 반환할 필요가 없음
		// 파일 이름을 반환
		
		System.out.println("after :" + map);
		// 추가된 맵의 내용확인
		
		render(map, viewName);
		//
	}
	
	static void render(HashMap map, String viewName) throws IOException {
		String result = "";
		
		Scanner sc = new Scanner(new File(viewName + ".txt"));
		// viewName의 텍스트파일을 만들고 Scanner로 읽어옵니다.
		
		while (sc.hasNextLine()) {
			// Scanner객체가 현재 입력에서 읽어들일 수 있는 라인이 있는 지 확인하고(hasNextLine()) - boolean
			result += sc.nextLine() + System.lineSeparator();
			// 있다면 해당 라인을 일고들여 result문자열에 추가합니다. +  문자열 끝에 개행도 추가합니다. 
		}
		
		Iterator it = map.keySet().iterator();
		// Iterator를 사용하여 맵의 키들을 반복합니다.

		
		while (it.hasNext()) {
			// Iterator에 다음 요소가 있는지 확인합니다. 다음 요소가 있으면, true를 반환하고, 없으면 false를 반환합니다.
			String key = (String)it.next();
			// it.next() Iterator에서 다음 요소롤 가져옵니다. 이것은 현재 이터레이션에서 처리할 맵의 키를 의미합니다.
			result = result.replace("${" + key + "}", (String)map.get(key));
			// 각 키에 대해 템플릿에서 ${key}와 같은 패턴을 찾아 해당 값을 맵의 값으로 대체합니다.
		}
		
		System.out.println(result);
		// 최종적으로 렌더링된 결과를 출력합니다.
	}
}

class ModelController {
	public String main(HashMap map) {
		map.put("id", "aa");
		map.put("pwd", "11");
		return "txtView2";
	}
}