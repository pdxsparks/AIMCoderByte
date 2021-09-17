import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class JUnit5ExampleTest {
    APILayer api = new APILayer();
    private final Logger logger = LogManager.getLogger();

    @Test
    //should create a new "sku"
    void happyPathPost() {
        String response = "";
        try{
            response = api.post("{\"sku\":\"berliner\",\"description\": \"Jelly donut\",\"price\":\"2.99\"}");
        } catch(Throwable t) {
            logger.error("The following Throwable was thrown: " + t);
        }

        //TODO: add test later, checking that the runner works
        logger.info(response);
    }
}