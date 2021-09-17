import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class APILayer {
    private HttpURLConnection connection;

    private void setupConnection() throws MalformedURLException {
        try {
            URL url = new URL("https://1ryu4whyek.execute-api.us-west-2.amazonaws.com/dev/skus");

            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json; utf-8");
            //enables send request content
            connection.setDoOutput(true);

        } catch (Exception m) {
            System.out.println(m.getMessage());
        }
    }

    private void closeConnection() {
        connection.disconnect();
    }

    public String post(String jsonInputString) throws Throwable{
        setupConnection();
        String stringResponse;
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            stringResponse = response.toString();
            //System.out.println(response.toString());
            //TODO: Add checks for response failure here
        }

        closeConnection();

        return stringResponse;
        }
    }

