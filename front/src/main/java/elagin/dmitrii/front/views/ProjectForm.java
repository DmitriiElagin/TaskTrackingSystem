package elagin.dmitrii.front.views;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import elagin.dmitrii.front.dto.ProjectDTO;

public class ProjectForm extends FormLayout {
    private final TextField title;
    private final Button btnSave;
    private final Button btnClose;
    private final Button btnDelete;

    private final Binder<ProjectDTO> binder;

    private ProjectDTO project;

    public ProjectForm() {
        this.title = new TextField("Наименование");
        this.btnSave = new Button("Сохранить");
        this.btnClose = new Button("Закрыть");
        this.btnDelete = new Button("Удалить");

        binder = new BeanValidationBinder<>(ProjectDTO.class);
        binder.bindInstanceFields(this);

        add(title, createButtonsLayout());

        addClassName("project-form");
    }

    public HorizontalLayout createButtonsLayout() {
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnSave.addClickShortcut(Key.ENTER);
        btnSave.addClickListener(event -> validateAndSave());

        btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnClose.addClickShortcut(Key.ESCAPE);
        btnClose.addClickListener(event -> fireEvent(new CloseEvent(this, project)));

        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnDelete.addClickListener(event -> fireEvent(new DeleteEvent(this, project)));

        HorizontalLayout layout = new HorizontalLayout(btnClose, btnDelete, btnSave);

        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        return layout;
    }

    private void validateAndSave() {
        try {
            binder.writeBean(project);
            fireEvent(new SaveEvent(this, project));

        } catch (ValidationException e) {
            System.err.println(e.getValidationErrors());
        }
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
        binder.readBean(project);
    }

    public static abstract class ProjectFormEvent extends ComponentEvent<ProjectForm> {
        private final ProjectDTO project;

        public ProjectFormEvent(ProjectForm source, ProjectDTO project) {
            super(source, false);
            this.project = project;
        }

        public ProjectDTO getProject() {
            return project;
        }
    }

    public static class SaveEvent extends ProjectFormEvent {

        public SaveEvent(ProjectForm source, ProjectDTO project) {
            super(source, project);
        }
    }

    public static class DeleteEvent extends ProjectFormEvent {

        public DeleteEvent(ProjectForm source, ProjectDTO project) {
            super(source, project);
        }
    }

    public static class CloseEvent extends ProjectFormEvent {
        public CloseEvent(ProjectForm source, ProjectDTO project) {
            super(source, project);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void show(boolean delVisible) {
        btnDelete.setVisible(delVisible);
        setVisible(true);
    }


}
