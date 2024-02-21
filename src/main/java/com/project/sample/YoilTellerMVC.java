package com.project.sample;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 원격 프로그램 등록 
public class YoilTellerMVC {
	@RequestMapping("/getYoilMVC") // url과 메서드 연결
	public String main(int year, int month, int day, Model model) throws IOException {
		
		if (!isValid(year, month, day)) {
			return "yoilError";
		}
		
		
		char yoil = getYoil(year, month, day);
		
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		model.addAttribute("yoil", yoil);
		
		return "yoil";

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
