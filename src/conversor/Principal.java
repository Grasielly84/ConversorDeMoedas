package conversor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Principal {
    private static final String API_KEY = "colar chave API aqui";
    private static final String BASE_URL = "https://v6.exchangeratesapi.io/latest?base=";

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                System.out.println("Escolha uma opção:");
                System.out.println("1. USD para EUR");
                System.out.println("2. EUR para USD");
                System.out.println("3. USD para GBP");
                System.out.println("4. GBP para USD");
                System.out.println("5. EUR para GBP");
                System.out.println("6. GBP para EUR");
                System.out.println("0. Sair");
                System.out.print("Opção: ");

                int option = Integer.parseInt(reader.readLine());

                if (option == 0) {
                    System.out.println("Saindo...");
                    break;
                }

                switch (option) {
                    case 1:
                        convertCurrency("USD", "EUR");
                        break;
                    case 2:
                        convertCurrency("EUR", "USD");
                        break;
                    case 3:
                        convertCurrency("USD", "GBP");
                        break;
                    case 4:
                        convertCurrency("GBP", "USD");
                        break;
                    case 5:
                        convertCurrency("EUR", "GBP");
                        break;
                    case 6:
                        convertCurrency("GBP", "EUR");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void convertCurrency(String from, String to) throws IOException {
        URL url = new URL(BASE_URL + from + "&access_key=" + API_KEY);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = reader.readLine();
        reader.close();

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        double rate = jsonObject.getAsJsonObject("rates").get(to).getAsDouble();

        System.out.println("Taxa de conversão de " + from + " para " + to + ": " + rate);
    }
}
