package org.jsonkey.replacer.jackson;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.jsonkey.replacer.JsonKeyReplacer;
import org.jsonkey.replacer.ReplaceRule;

import java.util.ArrayList;
import java.util.List;

public class JacksonKeyReplacerTest extends TestCase {

    private JsonKeyReplacer jsonKeyReplacer = new JacksonKeyReplacer();

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    private static final String json1 = "{\n" +
            "  \"id\": \"12345\",\n" +
            "  \"name\": \"Example JSON Structure with Dynamic Keys\",\n" +
            "  \"metadata\": {\n" +
            "    \"created_at\": \"2023-10-01T12:34:56Z\",\n" +
            "    \"updated_at\": \"2023-10-05T18:22:33Z\",\n" +
            "    \"version\": 3\n" +
            "  },\n" +
            "  \"dynamic_data\": [\n" +
            "    {\n" +
            "      \"type\": \"user\",\n" +
            "      \"user_id\": \"user1\",\n" +
            "      \"name\": \"John Doe\",\n" +
            "      \"email\": \"john.doe@example.com\",\n" +
            "      \"age\": 30,\n" +
            "      \"address\": {\n" +
            "        \"street\": \"123 Main St\",\n" +
            "        \"city\": \"New York\",\n" +
            "        \"country\": \"USA\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"product\",\n" +
            "      \"product_id\": \"prod1\",\n" +
            "      \"name\": \"Laptop\",\n" +
            "      \"price\": 999.99,\n" +
            "      \"attributes\": {\n" +
            "        \"brand\": \"ExampleBrand\",\n" +
            "        \"model\": \"X123\",\n" +
            "        \"specs\": {\n" +
            "          \"cpu\": \"Intel i7\",\n" +
            "          \"ram\": \"16GB\",\n" +
            "          \"storage\": \"512GB SSD\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"availability\": {\n" +
            "        \"in_stock\": true,\n" +
            "        \"quantity\": 15\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"order\",\n" +
            "      \"order_id\": \"order1\",\n" +
            "      \"user_id\": \"user1\",\n" +
            "      \"products\": [\n" +
            "        {\n" +
            "          \"product_id\": \"prod1\",\n" +
            "          \"quantity\": 1\n" +
            "        },\n" +
            "        {\n" +
            "          \"product_id\": \"prod2\",\n" +
            "          \"quantity\": 2\n" +
            "        }\n" +
            "      ],\n" +
            "      \"total_price\": 2399.97,\n" +
            "      \"payment_method\": \"credit_card\",\n" +
            "      \"special_note\": \"Please deliver before 5 PM.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"review\",\n" +
            "      \"review_id\": \"rev1\",\n" +
            "      \"product_id\": \"prod1\",\n" +
            "      \"user_id\": \"user1\",\n" +
            "      \"rating\": 4.5,\n" +
            "      \"comment\": \"Great laptop, fast and reliable!\",\n" +
            "      \"date\": \"2023-10-03\",\n" +
            "      \"images\": [\n" +
            "        {\n" +
            "          \"url\": \"https://example.com/image1.jpg\",\n" +
            "          \"caption\": \"Laptop on desk\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"url\": \"https://example.com/image2.jpg\",\n" +
            "          \"caption\": \"Close-up of keyboard\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"promotion\",\n" +
            "      \"promotion_id\": \"promo1\",\n" +
            "      \"name\": \"Holiday Sale\",\n" +
            "      \"discount_percentage\": 10,\n" +
            "      \"valid_for\": [\"prod1\", \"prod2\"],\n" +
            "      \"valid_until\": \"2023-12-31\",\n" +
            "      \"terms_and_conditions\": \"Offer valid for limited time only.\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public void testModifyKeys() throws Exception {
        System.out.println(json1);

        List<ReplaceRule> replaceRules = new ArrayList<>();
        replaceRules.add(new ReplaceRule("$.dynamic_data[*].attributes", "attrs"));
        replaceRules.add(new ReplaceRule("$.dynamic_data[*].images", "imgs"));
        replaceRules.add(new ReplaceRule("$.dynamic_data[*].images[*].caption", "common"));

        String json1Modify = "{\"id\":\"12345\",\"name\":\"Example JSON Structure with Dynamic Keys\",\"metadata\":{\"created_at\":\"2023-10-01T12:34:56Z\",\"updated_at\":\"2023-10-05T18:22:33Z\",\"version\":3},\"dynamic_data\":[{\"type\":\"user\",\"user_id\":\"user1\",\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"age\":30,\"address\":{\"street\":\"123 Main St\",\"city\":\"New York\",\"country\":\"USA\"}},{\"type\":\"product\",\"product_id\":\"prod1\",\"name\":\"Laptop\",\"price\":999.99,\"availability\":{\"in_stock\":true,\"quantity\":15},\"attrs\":{\"brand\":\"ExampleBrand\",\"model\":\"X123\",\"specs\":{\"cpu\":\"Intel i7\",\"ram\":\"16GB\",\"storage\":\"512GB SSD\"}}},{\"type\":\"order\",\"order_id\":\"order1\",\"user_id\":\"user1\",\"products\":[{\"product_id\":\"prod1\",\"quantity\":1},{\"product_id\":\"prod2\",\"quantity\":2}],\"total_price\":2399.97,\"payment_method\":\"credit_card\",\"special_note\":\"Please deliver before 5 PM.\"},{\"type\":\"review\",\"review_id\":\"rev1\",\"product_id\":\"prod1\",\"user_id\":\"user1\",\"rating\":4.5,\"comment\":\"Great laptop, fast and reliable!\",\"date\":\"2023-10-03\",\"imgs\":[{\"url\":\"https://example.com/image1.jpg\",\"common\":\"Laptop on desk\"},{\"url\":\"https://example.com/image2.jpg\",\"common\":\"Close-up of keyboard\"}]},{\"type\":\"promotion\",\"promotion_id\":\"promo1\",\"name\":\"Holiday Sale\",\"discount_percentage\":10,\"valid_for\":[\"prod1\",\"prod2\"],\"valid_until\":\"2023-12-31\",\"terms_and_conditions\":\"Offer valid for limited time only.\"}]}";

        String modifiedJson = jsonKeyReplacer.modifyKeys(json1, replaceRules);

        System.out.println(modifiedJson);

        Assert.assertEquals(json1Modify, modifiedJson);

    }

}