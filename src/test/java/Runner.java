import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class JUnit5ExampleTest {
    APILayer api = new APILayer();
    JSONObject response;
    int responseCode;

    @Test
    //should create a new "sku"
    void happyPath() {
        String createSku = "berliner";
        String description = "Jelly donut";
        String price = "2.99";

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

        try{
            response = api.get(sku);
        } catch(Throwable t) {
            System.out.println("The following Throwable was thrown: " + t);
            Assert.fail();
        }

        //get and save sku
        sku = response.getJSONObject("Item").get("sku").toString();
        if (!sku.equalsIgnoreCase(createSku)){
            System.out.println("the description" + createSku + "was expected returned: " +sku);
            Assert.fail();
        }
        desc = response.getJSONObject("Item").get("description").toString();
        if (!desc.equalsIgnoreCase(description)){
            System.out.println("the description" + description + "was expected returned: " +desc);
            Assert.fail();
        }
        p = response.getJSONObject("Item").get("price").toString();
        if (!price.equalsIgnoreCase(p)){
            System.out.println("the price: " + price + "was expected returned: " +p);
            Assert.fail();
        }

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
}