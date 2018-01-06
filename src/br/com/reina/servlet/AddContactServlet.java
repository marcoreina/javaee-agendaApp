package br.com.reina.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.reina.jdbc.dao.ContactDao;
import br.com.reina.jdbc.model.Contact;

@WebServlet("/addContact")
public class AddContactServlet extends HttpServlet {

	private static final long serialVersionUID = 8888302449929671252L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get the writer
		PrintWriter out = response.getWriter();
		
		//get request parameters
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String birthdayText = request.getParameter("birthday");
		Calendar birthday = null;
		
		//converting birthday
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthdayText);
			birthday = Calendar.getInstance();
			birthday.setTime(date);
		} catch (ParseException e) {
			out.println("Convertion error.");
			return;
		}
		
		//create a contact object
		Contact contact = new Contact();
		contact.setName(name);
		contact.setAddress(address);
		contact.setEmail(email);
		contact.setBirthday(birthday);
		
		//save contact
		ContactDao dao = new ContactDao();
		dao.add(contact);
		
		//Print contact's name
		out.println("<html>");
		out.println("<body>");
		out.println("Contact " + contact.getName() + " added successfully.");
		out.println("</body>");
		out.println("</html>");
	}
}
