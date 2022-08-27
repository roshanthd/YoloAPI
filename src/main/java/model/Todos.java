package model;

//getters and setters generators through Lombok plugin to reduce code redundancy
@Getter
@Setter
public class Todos {

    private int user_id;
    private String title;
    private String due_on;
    private String status;

    public Todos(int user_id, String title, String due_on, String status) {
        this.user_id = user_id;
        this.title = title;
        this.due_on = due_on;
        this.status = status;
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"user_id\" : \"" + user_id + "\",\n" +
                "    \"title\" : \"" + title + "\",\n" +
                "    \"due_on\" : \"" + due_on + "\",\n" +
                "    \"status\" : \"" + status + "\"\n" +
                "}";
    }
}