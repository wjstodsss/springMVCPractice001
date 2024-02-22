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
		// Class.forName Ŭ���� �̸��� ����Ͽ� �ش� Ŭ������ �������� �ε�
		// ��Ű�� �ּҸ� �Բ� �ۼ��ϸ� �ش� ��Ű������ Ŭ�����̸��� ���� Ŭ������ �ε�
		// YoilTellerMVC Ŭ������ ã�� �ε��Ͽ� clazz�� �Ҵ�
		
		Object obj = clazz.newInstance();
		// Object.newInstance() Ŭ������ ���� ��ü ����(�⺻�����ڷ� �ʱ�ȭ)
		// clazz�� YoilTellerMVC�� �Ҵ� �����Ƿ� YoilTellerMVC ��ü�� �����Ͽ� 
		// obj�� �Ҵ�
		
		Method main = clazz.getDeclaredMethod("main", int.class, int.class, int.class, Model.class);
		// Object.getDeclaredMethod()�� Ŭ������ �������̽��� �޼��� �߿��� ������ �̸��� �Ű����� ������ ������ �ִ� �޼��带 ã���ϴ�.
		// Method�� ��ȯ�մϴ�.
		// ���� �˻� �޼��尡 �������� ������ NoSuchMethodException �߻�
		// Object.getDeclaredMethod(�˻��Ϸ��� �޼����, �Ű������� Ŭ���� ��ü...)
		// Ŭ���� ��ü�� ���÷��ǿ��� �Ű����� ������ ������ �� ���
		// ���÷��� - �����߿� Ŭ������ ������ �˻��ϰ�, ��ü�� �޼��带 ȣ���ϰų� �ʵ忡 �����ϰ�
		// ���ο� ��ü�� �����ϴ� ���� �۾��� �����ϵ��� �մϴ�.
		// main�޼��带 �˻��ؼ� �����ϸ� main�� �Ҵ�
		
		
		Model model = new BindingAwareModelMap();
		// BindingAwareModelMap() �� ��ü�� ���� �� �ϳ��̸�
		// �����͸� �𵨿� �߰��ϰ� ��� �����ϴµ� ���
		
		System.out.println("[before] model" + model);
		// model�� �߰��� �����Ͱ� �����Ƿ� �������
		// [before] model{}
		
		String viewName = (String)main.invoke(obj, new Object[] { 2021, 10, 1, model });
		// Method.invoke()�� �޼��� ȣ�� �ÿ� �ʿ��� ��ü��� �ʿ��� �Ű������� ����
		// ������ �Ҵ� ���� main�޼��忡 �ʿ��� ��ü(YoilTellerMVC��ü obj)�� �ʿ��� �Ű�����
		// ���⼭�� ��ü�迭�� ���� �Ű����� 2021, 10, 1, model�� �־��µ�
		// invoke()�� �����μ��� ����ϱ� ������ �޸��� �����ؼ� �־� ���� ������ ���� ����
		// YoilTellerMVC�� main()�� model��ü�� �Ű������� �߰��ϰ� ���ڿ� ���̸� yoil�� ��ȯ�մϴ�.
		
		System.out.println("viewName+" + viewName);
		// viewName+yoil
		
		System.out.println("[after] model =" + model);
		// �Ű������� model�� �߰��� ���� Ȯ��
		// [after] model ={year=2021, month=10, day=1, yoil=��}
		
		render(model, viewName);
		// 
	}
	
	static void render(Model model, String viewName) throws IOException {
		// model ={year=2021, month=10, day=1, yoil=��}
		// viewName = yoil
		
		String result = "";
		
		Scanner sc = new Scanner (new File("src/main/webapp/WEB-INF/views/" + viewName + ".jsp"), "utf-8");
		// new Scanner()�� �ڹٿ��� ǥ�� �Է�, ����, ���ڿ��� �پ��� �ҽ��κ���
		// �Է��� �б� ���� ���Ǵ� Ŭ�����Դϴ�.
		// �ҽ��� �б� ���� ���ڵ��� ������ �� �ֽ��ϴ�.
		// new File()�� �� ������ �����ϰų� ���� ���Ͽ� ���� ������ ���� �� �ֽ��ϴ�.
		// ������ yoil.jsp������ utf-8�� ���ڵ� �Ͽ� �о�� sc�� �Ҵ��մϴ�.
		
		while(sc.hasNextLine()) {
			result += sc.nextLine() + System.lineSeparator();
		}
		// Scanner.hasNextLine()�� ��ü�� ���� �� �ִ� �Է� ��Ʈ������ ������ ���� ���� �ִ��� ���θ� Ȯ���Ͽ�
		// Scanner.nextLine()�� ������ġ�� ������ �а� ���� ȣ��� ���� �ٿ��� ȣ��˴ϴ�.
		// System.lineSeparator()�� ���� �÷����� �� �ٲ� ���ڸ� ��ȯ�ϴ� �޼����Դϴ�.
		// ������ true, �׷��� ������ false�� ��ȯ�մϴ�.
		// �׷��ϱ� yoil.jsp������ ������ ��� result�� ���ڿ��� �־����ϴ�.
		
		Map map = model.asMap();
		// Model.asMap()�� Model ��ü�� ����� �Ӽ����� �����·� ��ȯ�մϴ�.
		// Spring MVC���� ��Ʈ�ѷ��� �信 �����͸� �����ϴ� ��� �� �ϳ��� Model ��ü�� ����ϴ� ���Դϴ�. 
		// Model ��ü�� ��Ʈ�ѷ��� �� ���� �����͸� �����ϴ� �� ���Ǹ�, ��Ʈ�ѷ����� ��� �����͸� ������ ��
		// �� �޼��带 ����Ͽ� �����͸� �� ���·� ������ �� �ֽ��ϴ�.
		// model ={year=2021, month=10, day=1, yoil=��}
		// �̹� ���������� �����·� �Ǿ�������
		// �����͸� ���ϰ� �ٷ�� ���� Map���� ����ȯ�� �մϴ�.
		
		Iterator it = map.keySet().iterator();
		// Map.keySet().iterator()�� Map�� Ű ���տ� ���� �ݺ��ڸ� ��ȯ�ϴ� �޼����Դϴ�.
		// �̸� ���� Map�� Ű�� �ݺ������� ��ȸ�ϸ鼭 �� Ű�� ���� ó���� ������ �� �ֽ��ϴ�.
		// Iterator�� Java�÷��� �����ӿ�ũ���� �÷��� ��Ҹ� �ݺ������� Ž���ϱ� ���� �������̽��Դϴ�.
		// �̸� ���� �÷��� ������ ��Ҹ� ���������� ������ �� �ֽ��ϴ�.
		// �ش� �۾��� �켱 jsp������ �⺻���� ����
		// ������ �κп� ����� ������ �����Ͽ� ����ϱ� ���� �۾��Դϴ�.
		// ���⼭�� jsp���� EL�� ǥ���� ���κ��� �־� ����� �� �ֽ��ϴ�.
		
		while(it.hasNext()) {
			String key = (String)it.next();
		
			result = result.replace("${" + key + "}", "" + map.get(key));
		}
		
		System.out.println(result);
	}
			

}
