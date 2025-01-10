package server.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDBTest {

    private final JsonDB db = new JsonDB();
    private final Gson gson = new Gson();

    @Test
    void saveElementWithStringKey() {
        db.save("person",
                gson.fromJson(
                        """
                                {"name":"Elon Musk","car":{"model":"Tesla Roadster","year":"2018"},"rocket":{"name":"Falcon 9","launches":"87"}}
                                """, JsonElement.class)
        );

        var person = db.get("person");
        System.out.println(person);
    }

    @Test
    void getStringValueByArrayKey() {
        var result = db.get(gson.toJsonTree("""
                ["person","name"]
                """));
        System.out.println(result);
        assertThat(result).isEqualTo("Elon Musk");
    }

    @Test
    void setValueUsingArrayKey() {
        var jsonArray = new JsonArray();
        jsonArray.add("person");
        jsonArray.add("rocket");
        jsonArray.add("launches");

        db.save(jsonArray, new JsonPrimitive("88"));
        var person = db.get("person");
        System.out.println(person);
        /*var personTree = gson.fromJson(person, JsonElement.class);
        var map = personTree.getAsJsonObject().asMap();
        System.out.println(map);*/
        //assertThat(person).isEqualTo(gson.toJsonTree("88"));
    }

    @Test
    void saveElementWithArrayKey() {
    }
}