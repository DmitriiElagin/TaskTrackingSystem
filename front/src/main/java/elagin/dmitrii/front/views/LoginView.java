package elagin.dmitrii.front.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "login", layout = MainLayout.class)
@PageTitle("Вход | Task Tracking System")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private final LoginForm loginForm;


    public LoginView() {
        var loginI18n = LoginI18n.createDefault();
        loginI18n.getForm().setTitle("Вход в систему");
        loginI18n.getErrorMessage().setTitle("Неверные имя пользователя или пароль!");
        loginForm = new LoginForm(loginI18n);
        loginForm.setForgotPasswordButtonVisible(false);

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");

        Button btnReg = new Button("Регистрация");
        btnReg.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        add(loginForm, btnReg);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("errors")) {
            loginForm.setError(true);
        }

    }
}
