package uz.tuit.press.service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MessageService {
    public static void main(String[] args) throws IOException {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
        String apiToken = "6513531935:AAFWjGaaonsTPNYz-3jUlIC0wISIvxDqrTY";
        String chatId = "@itkbuz";
        String text = "Hello world!";
        urlString = String.format(urlString, apiToken, chatId, text);
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        StringBuilder sb = new StringBuilder();
        InputStream is = new BufferedInputStream(conn.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String inputLine = "";
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        String response = sb.toString();
        System.out.println(response);
    }
}

