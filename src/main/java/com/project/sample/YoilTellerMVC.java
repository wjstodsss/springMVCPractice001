package com.project.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 원격 프로그램 등록 
public class YoilTellerMVC {
	@RequestMapping("/getYoilMVC") // url과 메서드 연결
	public void main(String year, String month, String day, HttpServletResponse response) throws IOException {
		int yyyy = Integer.parseInt(year);
		int mm = Integer.parseInt(month);
		int dd = Integer.parseInt(day);
		
		Calendar cal = Calendar.getInstance();
		cal.set(yyyy, mm - 1, dd);
		
		int dayOfweek = cal.get(Calendar.DAY_OF_WEEK);
		char yoil = " 일월화수목금토".charAt(dayOfweek);
		
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println(year + "년 " + month + "월 " + day + "일은 ");
		out.println(yoil + "요일입니다.");
		out.println("</body>");
		out.println("</html>");
		
	}

}
