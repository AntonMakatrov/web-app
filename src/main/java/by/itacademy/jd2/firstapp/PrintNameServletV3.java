package by.itacademy.jd2.firstapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PrintNameServletV3",urlPatterns = "/printNameV3")
public class PrintNameServletV3 extends HttpServlet {

    private String NAME_PARAMETER_HEADER = "ARRAY_NAME_PARAM";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        String header = req.getHeader(NAME_PARAMETER_HEADER);
        String[] names = req.getParameterMap().get(header);

        if (names != null){
            for (String name: names ){
                writer.write("<p>" + header +": " + name + "</p>");
            }
        }
    }
}
