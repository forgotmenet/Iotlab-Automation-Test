package iotlab.test.pages.aaem;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import iotlab.test.base.BasePage;

public class HomePage extends BasePage {
    private final Locator userMenu = page.locator("[aria-label=Username]");
    private final Locator logoutButton = page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Logout"));

    public HomePage(Page page) {
        super(page);
    }

    public Locator getUserMenu() {
        return userMenu;
    }

    @Step("Log out")
    public void logout() {
        click(userMenu);
        click(logoutButton);
    }
}
