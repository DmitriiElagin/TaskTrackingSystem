package elagin.dmitry.tasktrackingsystem.dto;

import elagin.dmitry.tasktrackingsystem.entities.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserDTO implements Serializable {
    @Min(0)
    private int id;

    @NotBlank
    @Size(max = 25)
    private String firstName;
    @NotBlank
    @Size(max = 50)
    private String lastName;

    public UserDTO(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName) {
        this(0, firstName, lastName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User toUser() {
        return new User(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
