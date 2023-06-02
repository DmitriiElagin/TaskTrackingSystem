package elagin.dmitrii.front.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import elagin.dmitrii.front.dto.ProjectDTO;
import elagin.dmitrii.front.dto.TaskResponse;
import elagin.dmitrii.front.entities.User;
import elagin.dmitrii.front.service.ProjectService;
import elagin.dmitrii.front.service.TaskService;
import elagin.dmitrii.front.service.UserService;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

/**
 * @author Dmitrii Elagin
 * Date 23.05.2023 12:28
 */
@Route(value = "/tasks", layout = MainLayout.class)
@PageTitle("Задачи")
@RolesAllowed(value = {"ROLE_USER", "ROLE_ADMINISTRATOR"})
public class TaskView extends VerticalLayout {
  public static String FILTER_BY_PROJECT = "по проекту";
  public static String FILTER_BY_USER = "по пользователю";

  public static String NO_FILTER = "без фильтра";


  private final TaskService taskService;
  private final ProjectService projectService;
  private final UserService userService;

  private Select<ProjectDTO> projectList;
  private Select<User> userList;

  private Grid<TaskResponse> taskGrid;
  private RadioButtonGroup<String> radioFilterBy;

  private Span info;

  public TaskView(TaskService taskService, ProjectService projectService, UserService userService) {
    this.taskService = taskService;
    this.projectService = projectService;
    this.userService = userService;

    setSizeFull();

    add(createFilterBar(), createToolBar(), createContent());
    updateList();
  }

  private VerticalLayout createContent() {
    info = new Span();
    configureTaskGrid();

    VerticalLayout content = new VerticalLayout(info);
    content.setJustifyContentMode(JustifyContentMode.CENTER);
    content.setAlignItems(Alignment.CENTER);

    HorizontalLayout horizontalLayout = new HorizontalLayout(taskGrid);
    horizontalLayout.setFlexGrow(2, taskGrid);
    horizontalLayout.setSizeFull();
    content.add(horizontalLayout);
    content.setSizeFull();

    return content;
  }

  private void configureTaskGrid() {
    taskGrid = new Grid<>(TaskResponse.class, false);
    taskGrid.setSizeFull();
    taskGrid.addColumn(task -> task.getProject().getTitle()).setHeader(new H4("Проект"));
    taskGrid.addColumn(TaskResponse::getType).setHeader(new H4("Тип"));
    taskGrid.addColumn(task -> task.getPriority().getLabel()).setHeader(new H4("Приоритет"));
    taskGrid.addColumn(TaskResponse::getTheme).setHeader(new H4("Тема"));
    taskGrid.addColumn(task -> task.getResponsible().getFirstName() + " "
        + task.getResponsible().getLastName()).setHeader(new H4("Ответственный"));
    taskGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    taskGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    taskGrid.addClassName("tasks");

  }

  private HorizontalLayout createToolBar() {
    HorizontalLayout toolbar = new HorizontalLayout();
    toolbar.setPadding(true);

    Button btnRefresh = new Button();
    btnRefresh.setIcon(VaadinIcon.REFRESH.create());
    btnRefresh.addClickListener(event -> updateList());
    btnRefresh.setTooltipText("Обновить список");

    Button btnCreate = new Button();
    btnCreate.setIcon(VaadinIcon.FILE_ADD.create());
    btnCreate.setTooltipText("Создать задачу");

    toolbar.add(btnRefresh, btnCreate);

    return toolbar;
  }

  private HorizontalLayout createFilterBar() {
    HorizontalLayout filterBar = new HorizontalLayout();
    filterBar.setPadding(true);
    filterBar.setAlignItems(Alignment.BASELINE);

    userList = new Select<>();
    userList.setLabel("Пользователи");
    Collection<User> users = userService.findAll();
    userList.setItems(users);
    userList.setValue(users.iterator().next());
    userList.setRenderer(new ComponentRenderer<>(user -> new Text(user.getFirstName() + " " + user.getLastName())));
    userList.setEmptySelectionAllowed(false);
    userList.addClassName("select-users");
    userList.addValueChangeListener(user -> updateList());

    userList.setVisible(false);


    projectList = new Select<>();
    projectList.setLabel("Проекты");
    ProjectDTO[] projects = projectService.findAll();
    projectList.setItems(projects);
    projectList.setValue(projects[0]);
    projectList.setEmptySelectionAllowed(false);
    projectList.addValueChangeListener(project -> updateList());
    projectList.setVisible(false);

    radioFilterBy = new RadioButtonGroup<>();
    radioFilterBy.setItems(NO_FILTER, FILTER_BY_PROJECT, FILTER_BY_USER);
    radioFilterBy.addValueChangeListener(event -> {
      if (FILTER_BY_PROJECT.equals(event.getValue())) {
        projectList.setVisible(true);
        userList.setVisible(false);
      } else if (FILTER_BY_USER.equals(event.getValue())) {
        projectList.setVisible(false);
        userList.setVisible(true);
      } else {
        projectList.setVisible(false);
        userList.setVisible(false);
      }
    });

    radioFilterBy.setValue(NO_FILTER);

    filterBar.add(radioFilterBy, projectList, userList);

    return filterBar;
  }

  private void updateList() {
    TaskResponse[] tasks;

    if (FILTER_BY_USER.equals(radioFilterBy.getValue())) {
      tasks = taskService.findByResponsibleId(userList.getValue().getId());

    } else if (FILTER_BY_PROJECT.equals(radioFilterBy.getValue())) {
      tasks = taskService.findByProjectId(projectList.getValue().getId());


    } else {
      tasks = taskService.requestAll();
    }

    taskGrid.setItems(tasks);

    if (tasks.length == 0) {
      info.setText("Список проектов пуст");
      info.setVisible(true);
    } else {
      info.setText("");
      info.setVisible(false);
    }
  }
}
