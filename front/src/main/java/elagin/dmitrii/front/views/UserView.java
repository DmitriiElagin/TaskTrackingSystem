package elagin.dmitrii.front.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import elagin.dmitrii.front.entities.User;
import elagin.dmitrii.front.service.ImageService;
import elagin.dmitrii.front.service.UserService;

import javax.annotation.security.RolesAllowed;

/**
 * @author Dmitrii
 * Date 16.05.2023 17:52
 */
@Route(value = "/users", layout = MainLayout.class)
@PageTitle("Пользователи")
@RolesAllowed(value = {"ROLE_USER", "ROLE_ADMINISTRATOR"})
public class UserView extends VerticalLayout {
  private final UserService service;
  private ListBox<User> userList;

  public UserView(UserService service) {
    this.service = service;
    setSizeFull();
    configureListBox();
    add(getContent());
    updateList();
  }

  private Component getContent() {
    HorizontalLayout content = new HorizontalLayout();
    content.add(userList);
    content.setSizeFull();
    content.addClassName("content");
    return content;
  }

  private void configureListBox() {
    userList = new ListBox<>();

    userList.setRenderer(new ComponentRenderer<>(user -> {
      Avatar avatar = new Avatar();
      avatar.addThemeVariants(AvatarVariant.LUMO_XLARGE);
      avatar.setName(user.getFirstName() + " " + user.getLastName());
      byte[] userAvatar = user.getAvatar();
      if (userAvatar != null) {
        avatar.setImageResource(ImageService.byteArrayToStreamResource(userAvatar));
      }

      VerticalLayout verticalLayout = new VerticalLayout(new H4(user.getUsername()),
          new H4(user.getFirstName() + " " + user.getLastName()));

      verticalLayout.setSizeFull();

      verticalLayout.addClassName("user-list-item-content");


      HorizontalLayout layout = new HorizontalLayout(avatar, verticalLayout);
      layout.setSizeFull();
      layout.setAlignItems(Alignment.CENTER);
      layout.addClassName("user-list-item");

      return layout;
    }));

    userList.setSizeFull();
  }

  private void updateList() {
    userList.setItems(service.findAll());
  }
}
