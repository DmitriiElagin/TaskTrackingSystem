package elagin.dmitrii.front.views;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.shared.Registration;
import elagin.dmitrii.front.dto.UserRequest;
import elagin.dmitrii.front.entities.User;
import elagin.dmitrii.front.service.UserService;

import java.util.Arrays;

public class UserForm extends FormLayout {
    private final H2 title;

    private final boolean editing;
    private TextField username;
    private PasswordField password;

    private PasswordField passwordConfirmation;
    private TextField firstName;
    private TextField lastName;

    private Select<String> iconName;


    private RadioButtonGroup<User.UserRole> role;
    private Checkbox enabled;
    private Checkbox locked;

    private Button btnDelete;

    private Binder<UserRequest> binder;

    private UserRequest userRequest;

    private final UserService userService;

    public UserForm(String title, UserService service, boolean editing, boolean adminMode, boolean deleteButtonVisible) {
        this.title = new H2(title);

        this.editing = editing;

        this.userService = service;

        initFields();

        configureBinder();

        enabled.setVisible(adminMode);
        locked.setVisible(adminMode);
        role.setVisible(adminMode);

        add(this.title, username, password, passwordConfirmation, firstName, lastName, iconName, role, enabled, locked,
            createButtonPanel(deleteButtonVisible));

        addClassName("user-form");

    }

    public UserForm(String title, UserService service) {
        this(title, service, false, false, false);
    }

    private void initFields() {
        username = new TextField("Имя пользователя");
        password = new PasswordField("Пароль");
        passwordConfirmation = new PasswordField("Повтор пароля");

        firstName = new TextField("Имя");
        lastName = new TextField("Фамилия");
        role = new RadioButtonGroup<>("Роль", User.UserRole.ROLE_USER, User.UserRole.ROLE_ADMINISTRATOR);
        enabled = new Checkbox("Доступен");
        locked = new Checkbox("Блокирован");

        iconName = new Select<>();
        iconName.setLabel("Иконка пользователя");
        iconName.setRenderer(new ComponentRenderer<>(name -> VaadinIcon.valueOf(name).create()));

        iconName.setItems(Arrays.stream(VaadinIcon.values())
            .map(Enum::name)
            .toArray(String[]::new));
    }

    private void configureBinder() {
        binder = new BeanValidationBinder<>(UserRequest.class);
        binder.bindInstanceFields(this);

        if (!editing) {
            binder.forField(username)
                .withValidator((SerializablePredicate<String>) username ->
                        !userService.existsByUsername(username),
                    "Пользователь с таким именем уже существует!")
                .bind(UserRequest::getUsername, UserRequest::setUsername);
        }


        binder.forField(passwordConfirmation)
            .withValidator((SerializablePredicate<String>) value ->
                value.equals(password.getValue()), "Пароли должны совпадать!")
            .bind(UserRequest::getPasswordConfirmation, UserRequest::setPasswordConfirmation);
    }

    public boolean isEditing() {
        return editing;
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void setDeleteButtonVisible(boolean visible) {
        btnDelete.setVisible(visible);
    }

    public void setAdminMode(boolean mode) {
        enabled.setVisible(mode);
        locked.setVisible(mode);
        role.setVisible(mode);
    }

    private HorizontalLayout createButtonPanel(boolean deleteButtonVisible) {
        var btnSave = new Button("Сохранить");
        var btnCancel = new Button("Отменить");

        btnDelete = new Button("Удалить");
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnSave.addClickShortcut(Key.ENTER);
        btnSave.addClickListener(ev -> validateAndSave());


        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnCancel.addClickShortcut(Key.ESCAPE);
        btnCancel.addClickListener(ev -> fireEvent(new UserForm.CancelEvent(this, userRequest)));

        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnDelete.addClickListener(ev -> fireEvent(new UserForm.DeleteEvent(this, userRequest)));
        btnDelete.setVisible(deleteButtonVisible);

        HorizontalLayout layout = new HorizontalLayout(btnCancel, btnDelete, btnSave);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        return layout;

    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;

        binder.readBean(this.userRequest);

    }

    private void validateAndSave() {
        try {
            binder.writeBean(userRequest);
            fireEvent(new UserForm.SaveEvent(this, userRequest));

        } catch (ValidationException e) {
            System.err.println(e.getValidationErrors());
        }
    }

    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private final UserRequest userRequest;

        public UserFormEvent(UserForm source, UserRequest request) {
            super(source, false);
            this.userRequest = request;
        }

        public UserRequest getUserRequest() {
            return userRequest;
        }
    }

    public static class SaveEvent extends UserFormEvent {

        public SaveEvent(UserForm source, UserRequest request) {
            super(source, request);
        }
    }

    public static class CancelEvent extends UserFormEvent {

        public CancelEvent(UserForm source, UserRequest request) {
            super(source, request);
        }
    }

    public static class DeleteEvent extends UserFormEvent {

        public DeleteEvent(UserForm source, UserRequest request) {
            super(source, request);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
