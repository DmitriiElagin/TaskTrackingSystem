package elagin.dmitrii.front.entities;

import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "sys_user")
public class User implements UserDetails {
    public enum UserRole {
        ROLE_USER("Пользователь"), ROLE_ADMINISTRATOR("Администратор");


        private final String text;

        UserRole(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    @Column(name = "user_id", nullable = false, unique = true)
    private int id;

    @Id
    @Column(length = 20, nullable = false)
    @NotBlank
    @Size(min = 2, max = 20)
    private String username;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 6)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    @Column(name = "first_name", nullable = false)
    @NotBlank
    @Size(max = 32)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank
    @Size(max = 64)
    private String lastName;

    @Column(name = "icon_name")
    private String iconName;

    @Column
    private boolean enabled;

    @Column
    private boolean expired;

    @Column
    private boolean locked;

    @Column(name = "credentials_expired")
    private boolean credentialsExpired;

    public User(int id, String username, String password, UserRole role,
                String iconName, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.iconName = iconName;
        enabled = true;
    }

    public User() {
        enabled = true;
        role = UserRole.ROLE_USER;
        iconName = VaadinIcon.USER.name();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setIconName(String iconName) {
        if (iconName == null) {
            this.iconName = VaadinIcon.USER.name();
        } else {
            this.iconName = iconName;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    public int getId() {
        return id;
    }

    public UserRole getRole() {
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

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && enabled == user.enabled && expired == user.expired && locked == user.locked
                && credentialsExpired
                == user.credentialsExpired && username.equals(user.username) && password.equals(user.password)
                && role
                == user.role && firstName.equals(user.firstName) && lastName.equals(user.lastName)
                && iconName.equals(user.iconName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, firstName, lastName,
                iconName, enabled, expired, locked, credentialsExpired);
    }
}
