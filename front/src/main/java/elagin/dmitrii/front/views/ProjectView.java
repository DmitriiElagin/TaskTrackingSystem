package elagin.dmitrii.front.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import elagin.dmitrii.front.dto.ProjectDTO;
import elagin.dmitrii.front.service.ProjectService;

import javax.annotation.security.RolesAllowed;


@Route(value = "/projects", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Проекты")
@RolesAllowed(value = {"ROLE_USER", "ROLE_ADMINISTRATOR"})
public class ProjectView extends VerticalLayout {
    private final ProjectService service;
    private ListBox<ProjectDTO> projectListBox;
    private Span info;

    private ProjectForm form;

    public ProjectView(ProjectService service) {
        this.service = service;
        addClassName("project-view");
        setSizeFull();
        configureListBox();
        configureForm();
        add(getToolbar(), getContent());
        updateList();

        closeForm();
    }

    private HorizontalLayout getToolbar() {
        Button btnCreate = new Button();
        btnCreate.setIcon(VaadinIcon.FILE_ADD.create());
        btnCreate.setTooltipText("Создать проект");
        btnCreate.addClickListener(event -> showForm(new ProjectDTO("Новый проект"), false));
        Button btnRefresh = new Button();
        btnRefresh.setIcon(VaadinIcon.REFRESH.create());
        btnRefresh.addClickListener(event -> updateList());
        btnRefresh.setTooltipText("Обновить список");

        return new HorizontalLayout(btnRefresh, btnCreate);
    }

    private Component getContent() {
        info = new Span();
        VerticalLayout content = new VerticalLayout(info);
        content.setJustifyContentMode(JustifyContentMode.CENTER);
        content.setAlignItems(Alignment.CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout(projectListBox, form);
        horizontalLayout.setFlexGrow(2, projectListBox);
        horizontalLayout.setFlexGrow(1, form);
        horizontalLayout.setSizeFull();
        content.addClassName("content");
        content.add(horizontalLayout);
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new ProjectForm();
        form.addListener(ProjectForm.SaveEvent.class, this::saveProject);
        form.addListener(ProjectForm.CloseEvent.class, event -> closeForm());
        form.addListener(ProjectForm.DeleteEvent.class, this::deleteProject);
    }

    private void saveProject(ProjectForm.SaveEvent event) {
        service.save(event.getProject());
        updateList();
        closeForm();
    }

    private void closeForm() {
        form.setProject(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void deleteProject(ProjectForm.DeleteEvent event) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setCancelable(true);
        dialog.setHeader("Удаление проекта");
        dialog.setText("Удалить проект " + "\"" + event.getProject().getTitle() + "\" ?");
        dialog.setConfirmText("Удалить");
        dialog.setCancelText("Отмена");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(ev -> {
            service.delete(event.getProject().getId());
            updateList();
        });

        dialog.open();
    }

    private void configureListBox() {
        projectListBox = new ListBox<>();
        projectListBox.setRenderer(new ComponentRenderer<>(projectDTO -> {
            H4 h4 = new H4(projectDTO.getTitle());
            h4.setClassName("project-list-item");
            return h4;
        }));
        projectListBox.addClassNames("project-list");
        projectListBox.setSizeFull();
        projectListBox.addValueChangeListener(event -> showForm(event.getValue(), true));
    }

    private void showForm(ProjectDTO project, boolean canDelete) {
        if (project == null) {
            closeForm();
        } else {
            form.setProject(project);
            form.show(canDelete);
            addClassName("editing");
        }
    }

    private void updateList() {
        ProjectDTO[] projects = service.findAll();

        projectListBox.setItems(projects);

        if (projects.length == 0) {
            info.setText("Список проектов пуст");
            info.setVisible(true);
        } else {
            info.setText("");
            info.setVisible(false);
        }
    }
}
