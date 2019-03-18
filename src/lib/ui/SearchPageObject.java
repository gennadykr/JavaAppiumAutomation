package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_INPUT2 = "org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL =
                    "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                            "//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT =
                    "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                    "/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULTS_ELEMENT = "//*[@text='No results found']",
            SEARCH_ARTICLE_TITLE = "org.wikipedia:id/page_list_item_title";


    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /*TEMPLATE METHODS*/

    public void initSearchInput() {
        this.waitForElementAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find 'Search Wikipedia'",
                15
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                By.xpath(SEARCH_INPUT),
                search_line,"Cannot find 'Search…'",
                5
        );
    }

    public void clearSearchLine() {
        this.waitForElementAndClear(
                By.id(SEARCH_INPUT2),
                "Cannot find search input field",
                5
        );
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot click 'X'",
                5
        );
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "'X' didn't disappear",
                5
        );
    }


    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);

        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "Cannot find search result with substring " + substring,
                15
        );
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);

        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "Cannot find and click search result with substring " + substring,
                15
        );
    }

    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find any results",
                15
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public List<String> getTitlesOfFoundArticles(){
        return waitForElementsAndGetAttribute(
                By.id(SEARCH_ARTICLE_TITLE),
                "text",
                "Cannot find articles titles",
                15
        );
    }

    public void waitForEmptyResultLabel(){
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULTS_ELEMENT),
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultsOfSearch(){
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
    }
}
