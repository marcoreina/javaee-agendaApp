package br.com.reina.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld extends HttpServlet {
	
	private static final long serialVersionUID = -2223554992979624563L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>First Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Hello world Servlet!");
		out.println("</body>");
		out.println("</html>");
	}
}
