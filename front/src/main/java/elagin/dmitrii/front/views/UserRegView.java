package elagin.dmitrii.front.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import elagin.dmitrii.front.dto.UserRequest;
import elagin.dmitrii.front.entities.User;
import elagin.dmitrii.front.service.UserService;


@Route(value = "/users/registration", layout = MainLayout.class)
@PageTitle("Регистрация пользователя | Task Tracking System")
@AnonymousAllowed
public class UserRegView extends VerticalLayout {
    private UserForm userForm;
    private final UserService userService;

    public UserRegView(UserService service) {
        userService = service;

        setSizeFull();

        configureForm();

        add(userForm);
    }

    private void saveUser(UserForm.SaveEvent event) {
        var dto = userService.save(event.getUserRequest().toUser());
        if (dto != null) {
            var notification = Notification.show("Пользователь успешно зарегистрирован", 5000, Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            UI.getCurrent().getPage().getHistory().back();
        }
    }

    private void cancel(UserForm.CancelEvent event) {
        UI.getCurrent().getPage().getHistory().back();
    }

    private void configureForm() {
        var request = new UserRequest();
        request.setEnabled(true);
        request.setIconName(VaadinIcon.USER.name());
        request.setRole(User.UserRole.ROLE_USER);

        userForm = new UserForm("Регистрация пользователя", userService);
        userForm.setUserRequest(request);
        userForm.setWidth("35em");
        userForm.addListener(UserForm.CancelEvent.class, this::cancel);
        userForm.addListener(UserForm.SaveEvent.class, this::saveUser);
    }
}
