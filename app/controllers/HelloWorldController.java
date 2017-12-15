package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class HelloWorldController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(HelloWorldController.class);


    public Result helloWorld() {

        return badRequest("helloWorld");

    }

    public Result helloMattias() {

        return ok("hello Mattias");

    }

    public Result hello(String name) {

        return ok("hello " + name + "!");

    }

    public Result hellos(String name, Integer count) {

        final StringBuffer sb = new StringBuffer();
        sb.append("Hello ");

        for (int i = 0; i < count; i++) {
            sb.append(name + " ");
        }

        sb.append("!");

        return ok(sb.toString());

    }

    public Result greetings() {

        final JsonNode jsonNode = request().body().asJson();
        final String firstName = jsonNode.get("first_name").asText();
        final String lastName = jsonNode.get("last_name").asText();

        final Person person = play.libs.Json.fromJson(jsonNode, Person.class);

        return ok("Greetings " + person.firstName + " " + person.lastName);

    }

    public Result me() {

        final Person me = new Person();
        me.setFirstName("Mattias");
        me.setLastName("Levin");

        //Logger.error("me {} {}", me.firstName, me.lastName);

        LOGGER.debug("me {} {}", me.getFirstName(), me.getLastName());


        final JsonNode jsonNode = Json.toJson(me);

        return ok(jsonNode);

    }

    static class Person {

        private String firstName;

        private String lastName;

        @JsonProperty("first_name")
        public String getFirstName() {
            return firstName;
        }

        @JsonProperty("first_name")
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        @JsonProperty("last_name")
        public String getLastName() {
            return lastName;
        }

        @JsonProperty("last_name")
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

}
