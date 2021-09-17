import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class JUnit5ExampleTest {
    private APILayer api = new APILayer();
    private JSONObject response;
    private int responseCode;
    private String createSku = "berliner";
    private String description = "Jelly donut";
    private String price = "2.99";

    @Test
    void happyPathPost() {
        try{
            response = api.post("{\"sku\":\""+createSku+"\",\"description\": \""+description+"\",\"price\":\""+price+"\"}");
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assert.fail();
        }

        //get and save sku
        String sku = response.get("sku").toString();
        if (!sku.equalsIgnoreCase(createSku)){
            System.out.println("the description" + createSku + "was expected returned: " +sku);
            Assert.fail();
        }
        String desc = response.get("description").toString();
        if (!desc.equalsIgnoreCase(description)){
            System.out.println("the description" + description + "was expected returned: " +desc);
            Assert.fail();
        }
        String p = response.get("price").toString();
        if (!price.equalsIgnoreCase(p)){
            System.out.println("the price: " + price + "was expected returned: " +p);
            Assert.fail();
        }
    }

    @Test
    public void happyPathGet(){
        try{
            response = api.post("{\"sku\":\""+createSku+"\",\"description\": \""+description+"\",\"price\":\""+price+"\"}");
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assert.fail();
        }

        try{
            response = api.get(createSku);
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assert.fail();
        }

        //get and save sku
        String sku = response.getJSONObject("Item").get("sku").toString();
        if (!sku.equalsIgnoreCase(createSku)){
            System.out.println("the description" + createSku + "was expected returned: " +sku);
            Assert.fail();
        }
        String desc = response.getJSONObject("Item").get("description").toString();
        if (!desc.equalsIgnoreCase(description)){
            System.out.println("the description" + description + "was expected returned: " +desc);
            Assert.fail();
        }
        String p = response.getJSONObject("Item").get("price").toString();
        if (!price.equalsIgnoreCase(p)){
            System.out.println("the price: " + price + "was expected returned: " +p);
            Assert.fail();
        }
    }

    @Test
    public void happyPathGetAll(){
        JSONArray response;
        try{
            response = api.get();
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assert.fail();
        }

        //TODO needs to parse JSONArray
    }

    @Test
    public void happyPathDelete() {
        try{
            responseCode = api.delete(createSku);
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assert.fail();
        }

        if(responseCode!=200) {
            System.out.println("The response code was not 200, instead it was: " +responseCode);
            Assert.fail();
        }
    }

    @Test
    public void MalformedPOST(){
        try{
            response = api.post("{[\"sku\":\""+createSku+"\",\"description\": \""+description+"\",\"price\":\""+price+"\"]}");
        } catch(Throwable t) {
            if (!t.toString().contains("Server returned HTTP response code: 502 for URL:")) {
                Assert.fail();
            }
        }
    }

    @Test
    public void MalformedGET(){
        try{
            response = api.post("{"+createSku+"}");
        } catch(Throwable t) {
            if (!t.toString().contains("Server returned HTTP response code: 502 for URL:")) {
                Assert.fail();
            }
        }
    }

    @Test
    public void MalformedGETAll(){
        try{
            response = api.post("{}");
        } catch(Throwable t) {
            if (!t.toString().contains("Server returned HTTP response code: 400 for URL:")) {
                Assert.fail();
            }
        }
    }
}