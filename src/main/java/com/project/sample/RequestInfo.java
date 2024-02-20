package com.project.sample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestInfo {
	
	@RequestMapping("/requestInfo")
	public void main(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<p>request.getCharacterEncoding()=" + request.getCharacterEncoding() + "</p>"); // 요청 내용의 인코딩
		out.println("<p>request.getContentLength()=" + request.getContentLength() + "<p>"); // 요청 내용의 길이. 알 수 없을 때는 -1
		out.println("<p>request.getContentType()=" + request.getContentType() + "<p>"); // 요청 내용의 타입. 알 수 없을 때는 null 
		
		out.println("<p>request.getMethod()=" + request.getMethod() + "<p>"); // 요청 방법(get, post ...)
		out.println("<p>request.getProtocol()=" + request.getProtocol() + "<p>"); // 프로토콜의 종류와 버젼 (HTTP/1.1)
		out.println("<p>request.getScheme()=" + request.getScheme() + "<p>"); // 프로토콜
		
		out.println("<p>request.getServerName()=" + request.getServerName() + "<p>"); // 서버의 이름 또는 ip주소
		out.println("<p>request.getServerPort()=" + request.getServerPort() + "<p>"); // 서버 포트
		out.println("<p>request.getRequestURL()=" + request.getRequestURL() + "<p>"); // 요청 URL
		out.println("<p>request.getRequestURI()=" + request.getRequestURI() + "<p>"); // 요청 URI
		
		out.println("<p>request.getContextPath()=" + request.getContextPath() + "<p>"); // context path
		out.println("<p>request.getServletPath()=" + request.getServletPath() + "<p>"); // servlet path
		out.println("<p>request.getQueryString()=" + request.getQueryString() + "<p>"); // 쿼리 스트링
		
		out.println("<p>request.getLocalName()=" + request.getLocalName() + "<p>"); // 로컬 이름
		out.println("<p>request.getLocalPort()=" + request.getLocalPort() + "<p>"); // 로컬 포트
		
		out.println("<p>request.getRemoteAddr()=" + request.getRemoteAddr() + "<p>"); // 원격 ip주소
		out.println("<p>request.getRemoteHost()=" + request.getRemoteHost() + "<p>"); // 원격 호스트 또는 ip주소
		out.println("<p>request.getRemotePort()=" + request.getRemotePort() + "<p>"); // 원격 포트
		
		out.println("</body>");
		out.println("</html>");
		
	}
}