package elagin.dmitrii.front.views;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.shared.Registration;
import elagin.dmitrii.front.dto.UserRequest;
import elagin.dmitrii.front.entities.User;
import elagin.dmitrii.front.service.ImageService;
import elagin.dmitrii.front.service.UserService;

import java.io.IOException;

public class UserForm extends FormLayout {
    private final H2 title;

    private final boolean editing;
    private TextField username;
    private PasswordField password;

    private PasswordField passwordConfirmation;
    private TextField firstName;
    private TextField lastName;
    private Avatar avaPreview;
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

        add(this.title, username, password, passwordConfirmation, firstName,
            lastName, role, enabled, locked, createAvatarLayout(),
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
    }

    private HorizontalLayout createAvatarLayout() {
        avaPreview = new Avatar();
        avaPreview.setWidth("70px");
        avaPreview.setHeight("70px");
        if (userRequest != null) {
            avaPreview.setName(userRequest.getUsername());
            avaPreview.setAbbreviation(userRequest.getUsername().substring(0, 3));
        } else {
            avaPreview.setAbbreviation("usr");
        }

        var layout = new HorizontalLayout(getAvaUpload(), avaPreview);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        return layout;
    }

    private Upload getAvaUpload() {
        UploadI18N u18n = new UploadI18N();
        u18n.setDropFiles(new UploadI18N.DropFiles().setOne("Перетащите файл сюда"));
        u18n.setAddFiles(new UploadI18N.AddFiles().setOne("Выбрать аватар..."));

        UploadI18N.Error error = new UploadI18N.Error();
        error.setFileIsTooBig("Размер файла превышает допустимый!");
        error.setIncorrectFileType("Некорректный тип файла!");
        u18n.setError(error);

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setMaxFileSize(1000000);
        upload.setI18n(u18n);


        upload.setAcceptedFileTypes("image/*");
        upload.addFileRejectedListener(event -> Notification.show(event.getErrorMessage(), 5000,
            Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR));
        upload.addFailedListener(event -> Notification.show(event.getReason().getMessage(), 5000,
            Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR));

        upload.addSucceededListener(event -> {
            try {
                byte[] data = ImageService.inputStreamToByteArray(buffer.getInputStream());
                userRequest.setAvatar(data);
                avaPreview.setImageResource(ImageService.byteArrayToStreamResource(data));
            } catch (IOException e) {
                Notification.show("Ошибка загрузки файла!", 5000,
                    Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                throw new RuntimeException(e);
            }
        });

        upload.setWidth("500px");

        return upload;
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
