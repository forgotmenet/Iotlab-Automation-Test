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

@Epic("Quan trắc môi trường")
@Feature("DomainAdmin")
@Story("Đăng nhập")
public class DomainAdminLoginTest extends BaseTest {

    @Test(description = "DomainAdmin đăng nhập thành công với tài khoản hợp lệ")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithValidAccount() {
        new LoginPage(page).open().login(Role.DOMAIN_ADMIN.username(), Role.DOMAIN_ADMIN.password());

        assertThat(new HomePage(page).getUserMenu()).isVisible();
    }

    @Test(description = "DomainAdmin đăng nhập sai mật khẩu thì vẫn ở lại form đăng nhập")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithWrongPassword() {
        LoginPage loginPage = new LoginPage(page).open();
        loginPage.login(Role.DOMAIN_ADMIN.username(), ConfigReader.get("INVALID_PASSWORD"));

        assertThat(page).hasURL(java.util.regex.Pattern.compile(".*/realms/.*"));
        assertThat(loginPage.getUsernameInput()).isVisible();
    }

    @Test(description = "DomainAdmin để trống tài khoản và mật khẩu thì không đăng nhập được")
    @Severity(SeverityLevel.MINOR)
    public void loginWithEmptyFields() {
        LoginPage loginPage = new LoginPage(page).open();
        loginPage.login("", "");

        assertThat(loginPage.getLoginButton()).isVisible();
    }

    @Test(description = "DomainAdmin đăng xuất thì quay về form đăng nhập")
    @Severity(SeverityLevel.NORMAL)
    public void logout() {
        new LoginPage(page).open().login(Role.DOMAIN_ADMIN.username(), Role.DOMAIN_ADMIN.password());
        new HomePage(page).logout();

        assertThat(new LoginPage(page).getLoginButton()).isVisible();
    }
}
