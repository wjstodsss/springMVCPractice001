package com.project.sample;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class MethodCall {
	public static void main(String[] args) throws Exception {
		HashMap map = new HashMap();
		// map ����
		System.out.println("before:" + map);
		// map �ʱ� ���� Ȯ��
		
		ModelController mc = new ModelController();
		// �� ��Ʈ�η� ��ü ����
		
		String viewName = mc.main(map);
		// ���� �Ű������� �޾Ƽ�
		// �ʿ� ���� �߰��ϰ�
		// ���� ���� main�Ŵϱ� ��ȯ�� �ʿ䰡 ����
		// ���� �̸��� ��ȯ
		
		System.out.println("after :" + map);
		// �߰��� ���� ����Ȯ��
		
		render(map, viewName);
		//
	}
	
	static void render(HashMap map, String viewName) throws IOException {
		String result = "";
		
		Scanner sc = new Scanner(new File(viewName + ".txt"));
		// viewName�� �ؽ�Ʈ������ ����� Scanner�� �о�ɴϴ�.
		
		while (sc.hasNextLine()) {
			// Scanner��ü�� ���� �Է¿��� �о���� �� �ִ� ������ �ִ� �� Ȯ���ϰ�(hasNextLine()) - boolean
			result += sc.nextLine() + System.lineSeparator();
			// �ִٸ� �ش� ������ �ϰ�鿩 result���ڿ��� �߰��մϴ�. +  ���ڿ� ���� ���൵ �߰��մϴ�. 
		}
		
		Iterator it = map.keySet().iterator();
		// Iterator�� ����Ͽ� ���� Ű���� �ݺ��մϴ�.

		
		while (it.hasNext()) {
			// Iterator�� ���� ��Ұ� �ִ��� Ȯ���մϴ�. ���� ��Ұ� ������, true�� ��ȯ�ϰ�, ������ false�� ��ȯ�մϴ�.
			String key = (String)it.next();
			// it.next() Iterator���� ���� ��ҷ� �����ɴϴ�. �̰��� ���� ���ͷ��̼ǿ��� ó���� ���� Ű�� �ǹ��մϴ�.
			result = result.replace("${" + key + "}", (String)map.get(key));
			// �� Ű�� ���� ���ø����� ${key}�� ���� ������ ã�� �ش� ���� ���� ������ ��ü�մϴ�.
		}
		
		System.out.println(result);
		// ���������� �������� ����� ����մϴ�.
	}
}

class ModelController {
	public String main(HashMap map) {
		map.put("id", "aa");
		map.put("pwd", "11");
		return "txtView2";
	}
}