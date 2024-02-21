package com.project.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // ���� ���α׷� ��� 
public class YoilTellerMVC {
	@RequestMapping("/getYoilMVC") // url�� �޼��� ����
	public void main(String year, String month, String day, HttpServletResponse response) throws IOException {
		int yyyy = Integer.parseInt(year);
		int mm = Integer.parseInt(month);
		int dd = Integer.parseInt(day);
		
		Calendar cal = Calendar.getInstance();
		cal.set(yyyy, mm - 1, dd);
		
		int dayOfweek = cal.get(Calendar.DAY_OF_WEEK);
		char yoil = " �Ͽ�ȭ�������".charAt(dayOfweek);
		
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println(year + "�� " + month + "�� " + day + "���� ");
		out.println(yoil + "�����Դϴ�.");
		out.println("</body>");
		out.println("</html>");
		
	}

}
