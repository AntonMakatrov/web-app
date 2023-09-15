package by.itacademy.jd2.firstapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ParameterProcessingServlet", urlPatterns = "/process-parameters")
public class ParameterProcessingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        Enumeration<String> params = req.getParameterNames();

        writer.write("<html>");
        writer.write("<body>");
        writer.write("<h1>Имена:</h1>");
        // Проверка на имя в случае, если мы сами можем задать имя параметра а не рандомное
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = req.getParameter(paramName);

            if (paramName.startsWith("name")) {
                writer.write("<p>Имя: " + paramValue + "</p>");
            }
        }
        writer.write("</body>");
        writer.write("</html>");
    }

}


