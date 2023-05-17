package elagin.dmitrii.front.dto;

import com.vaadin.flow.component.icon.VaadinIcon;
import elagin.dmitrii.front.entities.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


public class UserRequest {
    private int id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 20)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 6)
    private String password;


    @NotNull
    @NotBlank
    private String passwordConfirmation;

    @NotNull
    private User.UserRole role;

    @NotNull
    @NotBlank
    @Size(max = 32)
    private String firstName;


    @NotNull
    @NotBlank
    @Size(max = 64)
    private String lastName;

    private String iconName;


    private boolean enabled;

    private boolean locked;

    public UserRequest(int id, String username, String password, String passwordConfirmation, User.UserRole role,
                       String iconName, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.iconName = iconName;
        enabled = true;
    }

    public UserRequest() {
        enabled = true;
        role = User.UserRole.ROLE_USER;
        iconName = VaadinIcon.USER.name();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setRole(User.UserRole role) {
        this.role = role;
    }

    public void setIconName(String iconName) {
        this.iconName = Objects.requireNonNullElseGet(iconName, VaadinIcon.USER::name);
    }

    public int getId() {
        return id;
    }

    public User.UserRole getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIconName() {
        return iconName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


    public boolean isLocked() {
        return locked;
    }


    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }

    public User toUser() {
        return new User(id, username, password, role, iconName, firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequest that = (UserRequest) o;
        return id == that.id && enabled == that.enabled && locked == that.locked && username.equals(that.username) && password.equals(that.password) && passwordConfirmation.equals(that.passwordConfirmation) && role == that.role && firstName.equals(that.firstName) && lastName.equals(that.lastName) && iconName.equals(that.iconName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, passwordConfirmation, role, firstName, lastName, iconName, enabled, locked);
    }
}
