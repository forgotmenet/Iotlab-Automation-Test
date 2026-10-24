package iotlab.test.tests.aaem.domainadmin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import iotlab.test.base.BaseTest;
import iotlab.test.pages.aaem.HomePage;
import iotlab.test.pages.aaem.LoginPage;
import iotlab.test.utils.ConfigReader;
import iotlab.test.utils.Role;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("AAEM")
@Feature("DomainAdmin")
@Story("Login")
public class DomainAdminLoginTest extends BaseTest {

    @Test(description = "Verify DomainAdmin logs in successfully with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithValidAccount() {
        new LoginPage(page).open().login(Role.DOMAIN_ADMIN.username(), Role.DOMAIN_ADMIN.password());

        assertThat(new HomePage(page).getUserMenu()).isVisible();
    }

    @Test(description = "Verify DomainAdmin remains on login form when entering wrong password")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithWrongPassword() {
        LoginPage loginPage = new LoginPage(page).open();
        loginPage.login(Role.DOMAIN_ADMIN.username(), ConfigReader.get("INVALID_PASSWORD"));

        assertThat(page).hasURL(java.util.regex.Pattern.compile(".*/realms/.*"));
        assertThat(loginPage.getUsernameInput()).isVisible();
    }

    @Test(description = "Verify DomainAdmin cannot login with empty username and password")
    @Severity(SeverityLevel.MINOR)
    public void loginWithEmptyFields() {
        LoginPage loginPage = new LoginPage(page).open();
        loginPage.login("", "");

        assertThat(loginPage.getLoginButton()).isVisible();
    }

    @Test(description = "Verify DomainAdmin is redirected to login form after logging out")
    @Severity(SeverityLevel.NORMAL)
    public void logout() {
        new LoginPage(page).open().login(Role.DOMAIN_ADMIN.username(), Role.DOMAIN_ADMIN.password());
        new HomePage(page).logout();

        assertThat(new LoginPage(page).getLoginButton()).isVisible();
    }
}
