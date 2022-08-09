package elagin.dmitry.tasktrackingservice.entities;

public class ErrorEntity {
    private final String message;

    public ErrorEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
