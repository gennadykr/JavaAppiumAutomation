import lib.CoreTestCase;
import lib.ui.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception{
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testCheckTextInSearchField() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        WebElement searchElement = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search element",
                5
        );

        String searchText = searchElement.getAttribute("text");

        assertEquals(
                "We don't see Search…",
                "Search…",
                searchText
        );
    }

    @Test
    public void testCheckSearchAndDiscard() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search element",
                5
        );

        List<WebElement> searchTitles = MainPageObject.waitForAllElementsPresented(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find any search element",
                5
        );

        long numberOfArticlesFound = searchTitles.stream().peek(
                (e) -> System.out.println(e.getAttribute("text"))
        ).count();

        System.out.println(numberOfArticlesFound);

        assertTrue(
                "Cannot find few articles for the search",
                searchTitles.size()>2 // 3 is already "few"
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'X'",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Some search titles are still visible",
                5
        );
    }

    @Test
    public void testCheckFoundContent() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search element",
                5
        );

        List<WebElement> searchTitles = MainPageObject.waitForAllElementsPresented(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find any search element",
                5
        );

        long numberOfArticlesFound = searchTitles.stream().peek(
                (e) -> {
                    String title = e.getAttribute("text");
                    System.out.println(title);
                    assertTrue(
                            "Article's title doesn't contain Java (case-insensitive)",
                            //title.contains("Java")
                            //title.matches("(.*)[jJ]ava(.*)")
                            title.toLowerCase().contains("java")
                    );
                }
        ).count();

        System.out.println(numberOfArticlesFound);
    }

    @Test
    public void testSwipeArticle() {

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' item",
                15
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find title",
                30
        );

        MainPageObject.swipeUp(2000);
        MainPageObject.swipeUp(2000);
        MainPageObject.swipeUp(2000);
        MainPageObject.swipeUp(2000);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        // Adding Java
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_line = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find 'Search…'",
                5
        );

        String article_pattern = "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                "//*[@text='Object-oriented programming language']";
        MainPageObject.waitForElementAndClick(
                By.xpath(article_pattern),
                "Cannot find 'Object-oriented programming language' item",
                15
        );

        WebElement titleElement = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find title",
                30
        );

        String articleTitle = titleElement.getAttribute("text");

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set article folder",
                5
        );

        String name_of_folder = "Learning programming";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text to article folder name",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        // Adding Python
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        search_line = "Python";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find 'Search…'",
                5
        );

        article_pattern = "General-purpose, high-level programming language";
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + article_pattern + "']"),
                "Cannot find " + article_pattern +" searching " + search_line,
                15
        );

         titleElement = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find title",
                30
        );

        String articleTitle2 = titleElement.getAttribute("text");

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/design_bottom_sheet']//*[@text='" + name_of_folder + "']"),
                "Cannot find folder " + name_of_folder,
                5
        );

        driver.runAppInBackground(5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar']/android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        // Removing the second article from the folder
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_container']//*[@text='" + name_of_folder + "']"),
                "Cannot find crated folder",
                5
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='" + articleTitle2 + "']"),
                "Cannot find 2d saved article"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='" + articleTitle2 + "']"),
                "Cannot delete 2d saved article",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + articleTitle + "']"),
                "Cannot find 1st saved article",
                5
        );

        String articleTitleOfSavedArticle = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title",
                30
        );

        assertEquals(
                "We see unexpected title",
                articleTitle,
                articleTitleOfSavedArticle
        );
    }

    @Test
    public void testArticleTitlePresence(){
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_line = "Green Day discography";
        search_line = "Java";

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find 'Search…'",
                5
        );

        String search_result_locator =
                "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                        "/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        MainPageObject.waitForElementAndClick(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                15
        );


        //waitForElementPresent(
        //        By.id("org.wikipedia:id/view_page_title_text"),
        //        "Cannot find article title",
        //        15
        //);


        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );

    }
}
