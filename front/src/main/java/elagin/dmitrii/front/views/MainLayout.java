package elagin.dmitrii.front.views;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import elagin.dmitrii.front.components.appnav.AppNav;
import elagin.dmitrii.front.components.appnav.AppNavItem;
import elagin.dmitrii.front.service.SecurityService;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {
    private H2 viewTitle;
    private Text username;
    private Icon userIcon;
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();

        userIcon = new Icon(VaadinIcon.USER);

        username = new Text("Гость");

        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        var optionalUser = securityService.getAuthenticatedUser();
        optionalUser.ifPresent(user -> {
            username.setText(user.getUsername());
            System.out.println(user.getIconName());
            userIcon = VaadinIcon.valueOf(user.getIconName()).create();
        });

        var btnLogout = new Button();
        btnLogout.setIcon(VaadinIcon.EXIT.create());
        btnLogout.addClickListener(event -> securityService.logout());
        btnLogout.setTooltipText("Выйти");

        var userPanel = new HorizontalLayout(userIcon, username, btnLogout);

        userPanel.addClassName("user-panel");
        userPanel.setAlignItems(FlexComponent.Alignment.CENTER);

        var header = new HorizontalLayout(toggle, viewTitle, userPanel);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(viewTitle);
        header.setWidth("100%");

        addToNavbar(true, header);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Task Tracking System ");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();
        nav.addItem(new AppNavItem("Проекты", ProjectView.class, VaadinIcon.CHART_GRID.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
