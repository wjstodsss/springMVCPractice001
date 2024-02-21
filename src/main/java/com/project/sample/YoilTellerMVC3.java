package com.project.sample;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller // 원격 프로그램 등록 
public class YoilTellerMVC3 {
	@RequestMapping("/yoil3") // url과 메서드 연결
	public ModelAndView main(int year, int month, int day) throws IOException {
		
		ModelAndView mv = new ModelAndView();
		
//		if (!isValid(year, month, day)) {
//			return "yoilError";
//		}
		
		
		char yoil = getYoil(year, month, day);
		
		mv.addObject("year", year);
		mv.addObject("month", month);
		mv.addObject("day", day);
		mv.addObject("yoil", yoil);
		
		mv.setViewName("yoil");
		
		return mv;

	}

	private boolean isValid(int year, int month, int day) {
		try {
			LocalDate.of(year, month, day);
			return true;
		} catch (DateTimeException e)  {
			return false;
		}
	}

	
	private char getYoil(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		
		int dayOfweek = cal.get(Calendar.DAY_OF_WEEK);
		return " 일월화수목금토".charAt(dayOfweek);
	}

}
