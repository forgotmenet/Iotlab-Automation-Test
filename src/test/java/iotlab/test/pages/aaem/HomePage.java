package iotlab.test.pages.aaem;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import iotlab.test.base.BasePage;

public class HomePage extends BasePage {

    // ===== LOCATORS (kiểm tra/sửa bằng codegen) =====
    private final Locator userMenu = page.locator("[aria-label=Username]"); // avatar góc phải, chỉ có sau khi đăng nhập
    private final Locator logoutButton = page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Logout"));
    // ===== HẾT LOCATORS =====

    public HomePage(Page page) {
        super(page);
    }

    public Locator getUserMenu() {
        return userMenu;
    }

    @Step("Đăng xuất")
    public void logout() {
        click(userMenu);
        click(logoutButton);
    }
}
