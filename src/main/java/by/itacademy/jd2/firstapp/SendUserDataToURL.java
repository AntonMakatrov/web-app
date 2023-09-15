package by.itacademy.jd2.firstapp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SendUserDataToURL {
    public static void main(String[] args) {
        try {
            // Запрос имени и фамилии пользователя с консоли
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Введите фамилию: ");
            String lastName = scanner.nextLine();

            // Формирование URL с параметрами
            String urlStr = "http://localhost:8080/firstapp/hello_with_name";
            String queryParams = String.format("firstName=%s&lastName=%s",
                    URLEncoder.encode(firstName, "UTF-8"),
                    URLEncoder.encode(lastName, "UTF-8"));
            URL url = new URL(urlStr + "?" + queryParams);

            // Создание соединения
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Установка метода запроса (GET)
            connection.setRequestMethod("GET");

            // Чтение ответа от сервера (если нужно)
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Ответ от сервера: " + response.toString());
            } else {
                System.out.println("Ошибка при отправке запроса: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

