package by.itacademy.jd2.firstapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "VotingServlet", urlPatterns = "/voting")
public class VotingServlet extends HttpServlet {
    private static final Map<String, Integer> resultsPerformers = new HashMap<>();
    private static final Map<String, Integer> resultsGenres = new HashMap<>();
    private static final Map<String, Long> dates = new HashMap<>();
    private static final List<String> textsAbout = new ArrayList<>();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String performer = request.getParameter("performer");
        String[] chosenGenres = request.getParameterValues("genre");
        String textAbout = request.getParameter("text_about");

        try {
            if (chosenGenres != null && chosenGenres.length >= 3 && chosenGenres.length <= 5) {
                for (String genre : chosenGenres) {
                    resultsGenres.merge(genre, 1, Integer::sum);
                }
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h1>Ошибка</h1>");
                out.println("<p>Выберите от 3 до 5 любимых жанров.</p>");
                out.println("</body></html>");
                throw new ServletException("Выбрано недопустимое количество жанров");
            }

            if (performer != null && !performer.isEmpty()) {
                resultsPerformers.merge(performer, 1, Integer::sum);
            }

            // Сохранение даты/времени отправки
            long curDate = System.currentTimeMillis();
            dates.put(textAbout, curDate);
            textsAbout.add(textAbout);

            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/voting");
        } catch (ServletException e) {
            e.printStackTrace();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Voting.html");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<html><head><title>Результаты голосования</title></head><body>");
        resp.getWriter().write("<h2>Лучшие исполнители:</h2>");
        resultsOutput(resultsPerformers, resp);
        resp.getWriter().write("<h2>Любимые жанры:</h2>");
        resultsOutput(resultsGenres, resp);
        resp.getWriter().write("<h2>Тексты о вас:</h2>");
        textsOutput(textsAbout, resp);
        resp.getWriter().write("</body></html>");
    }

    private void resultsOutput(Map<String, Integer> results, HttpServletResponse resp) throws IOException {
        if (results.isEmpty()) {
            resp.getWriter().println("<p>Результатов пока нет.</p>");
        } else {
            // Сортировка результатов по количеству голосов
            List<Map.Entry<String, Integer>> sortedResults = results.entrySet()
                    .stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).toList();

            // Вывод отсортированных результатов
            resp.getWriter().println("<ol>");
            for (Map.Entry<String, Integer> entry : sortedResults) {
                resp.getWriter().println("<li>" + entry.getKey() + ": " + entry.getValue() + " голос(а)</li>");
            }
            resp.getWriter().println("</ol>");
        }
    }

    private void textsOutput(List<String> texts, HttpServletResponse resp) throws IOException {
        if (texts.isEmpty()) {
            resp.getWriter().println("<p>Текстов о вас пока нет.</p>");
        } else {
            // Сортировка текстов по дате/времени
            texts.sort(Comparator.naturalOrder());

            // Вывод отсортированных текстов
            resp.getWriter().println("<ul>");
            for (String text : texts) {
                resp.getWriter().println("<li>" + text + "</li>");
            }
            resp.getWriter().println("</ul>");
        }
    }

}
