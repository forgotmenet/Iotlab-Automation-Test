package iotlab.test.tests.aaem.farmer;

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
@Feature("Farmer")
@Story("Đăng nhập")
public class FarmerLoginTest extends BaseTest {

    @Test(description = "Farmer đăng nhập thành công với tài khoản hợp lệ")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithValidAccount() {
        new LoginPage(page).open().login(Role.FARMER.username(), Role.FARMER.password());

        assertThat(new HomePage(page).getUserMenu()).isVisible();
    }

    @Test(description = "Farmer đăng nhập sai mật khẩu thì vẫn ở lại form đăng nhập")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithWrongPassword() {
        LoginPage loginPage = new LoginPage(page).open();
        loginPage.login(Role.FARMER.username(), ConfigReader.get("INVALID_PASSWORD"));

        assertThat(page).hasURL(java.util.regex.Pattern.compile(".*/realms/.*"));
        assertThat(loginPage.getUsernameInput()).isVisible();
    }

    @Test(description = "Farmer đăng xuất thì quay về form đăng nhập")
    @Severity(SeverityLevel.NORMAL)
    public void logout() {
        new LoginPage(page).open().login(Role.FARMER.username(), Role.FARMER.password());
        new HomePage(page).logout();

        assertThat(new LoginPage(page).getLoginButton()).isVisible();
    }
}
