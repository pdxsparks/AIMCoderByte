import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JUnit5ExampleTest {
    private APILayer api = new APILayer();
    private JSONObject response;
    private int responseCode;
    private final String createSku = "berliner";
    private final String description = "Jelly donut";
    private final String price = "2.99";

    @Test
    void happyPathPost() {
        try{
            response = api.post("{\"sku\":\""+createSku+"\",\"description\": \""+description+"\",\"price\":\""+price+"\"}");
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assertions.fail();
        }

        //get and save sku
        String sku = response.get("sku").toString();
        if (!sku.equalsIgnoreCase(createSku)){
            System.out.println("the description" + createSku + "was expected returned: " +sku);
            Assertions.fail();
        }
        String desc = response.get("description").toString();
        if (!desc.equalsIgnoreCase(description)){
            System.out.println("the description" + description + "was expected returned: " +desc);
            Assertions.fail();
        }
        String p = response.get("price").toString();
        if (!price.equalsIgnoreCase(p)){
            System.out.println("the price: " + price + "was expected returned: " +p);
            Assertions.fail();
        }
    }

    @Test
    public void happyPathGet(){
        try{
            response = api.post("{\"sku\":\""+createSku+"\",\"description\": \""+description+"\",\"price\":\""+price+"\"}");
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assertions.fail();
        }

        try{
            response = api.get(createSku);
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assertions.fail();
        }

        //get and save sku
        String sku = response.getJSONObject("Item").get("sku").toString();
        if (!sku.equalsIgnoreCase(createSku)){
            System.out.println("the description" + createSku + "was expected returned: " +sku);
            Assertions.fail();
        }
        String desc = response.getJSONObject("Item").get("description").toString();
        if (!desc.equalsIgnoreCase(description)){
            System.out.println("the description" + description + "was expected returned: " +desc);
            Assertions.fail();
        }
        String p = response.getJSONObject("Item").get("price").toString();
        if (!price.equalsIgnoreCase(p)){
            System.out.println("the price: " + price + "was expected returned: " +p);
            Assertions.fail();
        }
    }

    @Test
    public void happyPathGetAll(){
        //make two skus to guarentee that the test will have more than 1
        try{
            response = api.post("{\"sku\":\"sparksTest1\",\"description\": \""+description+"\",\"price\":\""+price+"\"}");
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assertions.fail();
        }
        try{
            response = api.post("{\"sku\":\"sparksTest2\",\"description\": \""+description+"\",\"price\":\""+price+"\"}");
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assertions.fail();
        }
        JSONArray arrayResponse;
        try{
            arrayResponse = api.get();
            if(arrayResponse.length() <= 2) {
                System.out.println("The expected result was greater than 1 and instead was less than 1");
                Assertions.fail();
            }
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assertions.fail();
        }
    }

    @Test
    public void happyPathDelete() {
        try{
            responseCode = api.delete(createSku);
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assertions.fail();
        }

        if(responseCode!=200) {
            System.out.println("The response code was not 200, instead it was: " +responseCode);
            Assertions.fail();
        }
    }

    @Test
    public void MalformedPOST(){
        try{
            response = api.post("{[\"sku\":\""+createSku+"\",\"description\": \""+description+"\",\"price\":\""+price+"\"]}");
        } catch(Throwable t) {
            if (!t.toString().contains("Server returned HTTP response code: 502 for URL:")) {
                Assertions.fail();
            }
        }
    }

    @Test
    public void MalformedGET(){
        try{
            response = api.post("{"+createSku+"}");
        } catch(Throwable t) {
            if (!t.toString().contains("Server returned HTTP response code: 502 for URL:")) {
                Assertions.fail();
            }
        }
    }

    @Test
    public void MalformedGETAll(){
        try{
            response = api.post("{}");
        } catch(Throwable t) {
            if (!t.toString().contains("Server returned HTTP response code: 400 for URL:")) {
                Assertions.fail();
            }
        }
    }

}