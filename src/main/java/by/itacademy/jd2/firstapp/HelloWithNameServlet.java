package by.itacademy.jd2.firstapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloWithNameServlet", urlPatterns = "/hello_with_name")
public class HelloWithNameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        String firstName = req.getParameter("firstName");
        String lastName = req. getParameter("lastName");
        resp.setContentType("text/html; charset=UTF-8");

        // Если параметры переданы, сохраняем их в куках
        if (firstName != null && lastName != null) {
            Cookie firstNameCookie = new Cookie("firstName", firstName);
            Cookie lastNameCookie = new Cookie("lastName", lastName);
            resp.addCookie(firstNameCookie);
            resp.addCookie(lastNameCookie);
        } else {
            // Если параметры не переданы, пытаемся получить их из кук
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("firstName")) {
                        firstName = cookie.getValue();
                    } else if (cookie.getName().equals("lastName")) {
                        lastName = cookie.getValue();
                    }
                }
            }
        }

        // Если после всех проверок информации нет, выводим ошибку
        if (firstName == null || lastName == null) {
            resp.getWriter().println("Ошибка: Информация не найдена в параметрах запроса или куках.");
        } else {
            // Иначе выводим приветственное сообщение с именем и фамилией
            resp.getWriter().println("Привет, " + firstName + " " + lastName + "!");
        }
    }
}
