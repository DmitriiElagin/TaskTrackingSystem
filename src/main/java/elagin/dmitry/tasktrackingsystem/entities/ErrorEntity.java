package elagin.dmitry.tasktrackingsystem.entities;

public class ErrorEntity {
    private final String message;

    public ErrorEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorEntity{" +
                "message='" + message + '\'' +
                '}';
    }
}
