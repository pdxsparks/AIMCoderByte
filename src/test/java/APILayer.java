import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APILayer {
    private HttpURLConnection connection;

    private void closeConnection() {
        connection.disconnect();
    }

    public JSONObject post(String jsonInputString) throws Throwable{
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
        String stringResponse;
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            int responseCode = connection.getResponseCode();
            if(responseCode!=200){
                System.out.println("The response code was not 200, instead it was: " +responseCode);
                Assertions.fail();
            }
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            stringResponse = response.toString();
        }

        closeConnection();

        return new JSONObject(stringResponse);
        }

    public JSONArray get() throws Throwable{

        try {
            URL url = new URL("https://1ryu4whyek.execute-api.us-west-2.amazonaws.com/dev/skus");

            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json; utf-8");
            connection.setDoInput(true);
            connection.setDoOutput(false);

        } catch (Exception m) {
            System.out.println(m.getMessage());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        int responseCode = connection.getResponseCode();
        if(responseCode!=200){
            System.out.println("The response code was not 200, instead it was: " +responseCode);
            Assertions.fail();
        }
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        closeConnection();

        //JSONArray test = new JSONArray(response);
        //TODO this does not work, will need to use GSON and map? seems wrong
        return new JSONArray(response.toString());
    }

    public JSONObject get(String jsonInputString) throws Throwable{

        try {
            URL url = new URL("https://1ryu4whyek.execute-api.us-west-2.amazonaws.com/dev/skus/"+jsonInputString );

            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json; utf-8");
            connection.setDoInput(true);
            connection.setDoOutput(false);

        } catch (Exception m) {
            System.out.println(m.getMessage());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        int responseCode = connection.getResponseCode();
        if(responseCode!=200){
            System.out.println("The response code was not 200, instead it was: " +responseCode);
            Assertions.fail();
        }
        String inputLine;


        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        closeConnection();

        return new JSONObject(response.toString());
    }

    public int delete(String jsonInputString) throws Throwable{
        try {
            URL url = new URL("https://1ryu4whyek.execute-api.us-west-2.amazonaws.com/dev/skus/"+jsonInputString);

            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type","application/json; utf-8");
            //enables send request content
            connection.setDoOutput(true);

        } catch (Exception m) {
            System.out.println(m.getMessage());
        }

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        closeConnection();

        return responseCode;
    }
}

