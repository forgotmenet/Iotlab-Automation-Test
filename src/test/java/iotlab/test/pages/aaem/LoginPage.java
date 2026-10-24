package iotlab.test.pages.aaem;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import iotlab.test.base.BasePage;
import iotlab.test.utils.ConfigReader;

public class LoginPage extends BasePage {

    // ===== LOCATORS =====
    private final Locator usernameInput = page.locator("#username");
    private final Locator passwordInput = page.locator("#password");
    private final Locator loginButton = page.locator("#kc_submit");

    public LoginPage(Page page) {
        super(page);
    }

    @Step("Open login page and redirect to Keycloak")
    public LoginPage open() {
        page.navigate(ConfigReader.get("AAEM_BASE_URL"));
        usernameInput.waitFor();
        return this;
    }

    @Step("Login with account: {username}")
    public void login(String username, String password) {
        fill(usernameInput, username);
        fill(passwordInput, password);
        click(loginButton);
    }

    public boolean isOnLoginForm() {
        return isVisible(usernameInput) && isVisible(passwordInput);
    }

    public Locator getUsernameInput() {
        return usernameInput;
    }

    public Locator getLoginButton() {
        return loginButton;
    }
}

