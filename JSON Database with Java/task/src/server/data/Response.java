package server.data;

public record Response(String response, String value, String reason) {

    public static Response ok() {
        return new Response("OK");
    }

    public static Response ok(String value) {
        return new Response("OK", value, null);
    }

    public static Response error(String reason) {
        return new Response("ERROR", null, reason);
    }

    private Response(String response) {
        this(response, null, null);
    }
}
