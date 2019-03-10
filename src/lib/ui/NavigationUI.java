package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject {

    private final static String
        MY_LIST_BUTTON = "//android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI (AppiumDriver driver){
        super(driver);
    }

    public void clickMyList(){
        this.waitForElementAndClick(
                By.xpath(MY_LIST_BUTTON),
                "Cannot find navigation button to My lists",
                5
        );
    }
}
