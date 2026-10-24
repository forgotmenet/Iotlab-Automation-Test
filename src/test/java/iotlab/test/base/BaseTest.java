package iotlab.test.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import iotlab.test.utils.ConfigReader;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeMethod
    public void launchBrowser() {
        Map<String, String> env = new HashMap<>(System.getenv());

        if (System.getenv("CI") == null) {
            env.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
        }

        playwright = Playwright.create(new Playwright.CreateOptions().setEnv(env));

        boolean isHeadless = ConfigReader.getBoolean("HEADLESS", true);

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(isHeadless).setSlowMo(ConfigReader.getInt("SLOW_MO_MS"));

        if (System.getenv("CI") == null) {
            options.setChannel("chrome");
        }

        browser = playwright.chromium().launch(options);
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(ConfigReader.getInt("TIMEOUT_MS"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && page != null) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.attachment("Failure Screenshot", new ByteArrayInputStream(screenshot));
        }

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
