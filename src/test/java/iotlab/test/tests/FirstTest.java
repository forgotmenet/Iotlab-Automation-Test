package iotlab.test.tests;

import org.testng.annotations.Test;
import iotlab.test.base.BaseTest;

public class FirstTest extends BaseTest {

    static String loginUrl =
            "http://qtmt.iotlab.net.vn/";

    @Test
    public void verifyTitle() {
        page.navigate(loginUrl);
        System.out.println(page.title());
    }
}
