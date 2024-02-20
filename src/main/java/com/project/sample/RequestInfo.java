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
		
		out.println("<p>request.getCharacterEncoding()=" + request.getCharacterEncoding() + "</p>"); // ��û ������ ���ڵ�
		out.println("<p>request.getContentLength()=" + request.getContentLength() + "<p>"); // ��û ������ ����. �� �� ���� ���� -1
		out.println("<p>request.getContentType()=" + request.getContentType() + "<p>"); // ��û ������ Ÿ��. �� �� ���� ���� null 
		
		out.println("<p>request.getMethod()=" + request.getMethod() + "<p>"); // ��û ���(get, post ...)
		out.println("<p>request.getProtocol()=" + request.getProtocol() + "<p>"); // ���������� ������ ���� (HTTP/1.1)
		out.println("<p>request.getScheme()=" + request.getScheme() + "<p>"); // ��������
		
		out.println("<p>request.getServerName()=" + request.getServerName() + "<p>"); // ������ �̸� �Ǵ� ip�ּ�
		out.println("<p>request.getServerPort()=" + request.getServerPort() + "<p>"); // ���� ��Ʈ
		out.println("<p>request.getRequestURL()=" + request.getRequestURL() + "<p>"); // ��û URL
		out.println("<p>request.getRequestURI()=" + request.getRequestURI() + "<p>"); // ��û URI
		
		out.println("<p>request.getContextPath()=" + request.getContextPath() + "<p>"); // context path
		out.println("<p>request.getServletPath()=" + request.getServletPath() + "<p>"); // servlet path
		out.println("<p>request.getQueryString()=" + request.getQueryString() + "<p>"); // ���� ��Ʈ��
		
		out.println("<p>request.getLocalName()=" + request.getLocalName() + "<p>"); // ���� �̸�
		out.println("<p>request.getLocalPort()=" + request.getLocalPort() + "<p>"); // ���� ��Ʈ
		
		out.println("<p>request.getRemoteAddr()=" + request.getRemoteAddr() + "<p>"); // ���� ip�ּ�
		out.println("<p>request.getRemoteHost()=" + request.getRemoteHost() + "<p>"); // ���� ȣ��Ʈ �Ǵ� ip�ּ�
		out.println("<p>request.getRemotePort()=" + request.getRemotePort() + "<p>"); // ���� ��Ʈ
		
		out.println("</body>");
		out.println("</html>");
		
	}
}