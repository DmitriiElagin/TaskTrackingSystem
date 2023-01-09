package elagin.dmitrii.front.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import elagin.dmitrii.front.entities.User;
import elagin.dmitrii.front.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {
    private static final String LOGOUT_SUCCESS_URL = "/";

    private final UserRepository userRepository;

    public SecurityService(UserRepository repository) {
        this.userRepository = repository;
    }

    public Optional<User> getAuthenticatedUser() {
        Optional<User> optional = Optional.empty();
        var context = SecurityContextHolder.getContext();
        var principal = context.getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            optional = userRepository.findByUsername(((UserDetails) principal).getUsername());
        }

        return optional;
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        var logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
