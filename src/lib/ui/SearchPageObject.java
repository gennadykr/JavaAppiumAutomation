package lib.ui;

import io.appium.java_client.AppiumDriver;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]",
            SEARCH_INPUT2 = "id:org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL =
                    "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                            "//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT =
                    "xpath://*[@resource-id='org.wikipedia:id/search_results_list']" +
                    "/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULTS_ELEMENT = "xpath://*[@text='No results found']",
            SEARCH_ARTICLE_TITLE = "id:org.wikipedia:id/page_list_item_title",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL =
                    "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']/*[@class='android.widget.LinearLayout']" +
                            "[" +
                            "*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{ARTICLE_TITLE}'] " +
                            "and " +
                            "*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{ARTICLE_DESCRIPTION}']" +
                            "]";


    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getXpathForArticleWith(String article_title, String article_description) {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL
                .replace("{ARTICLE_TITLE}", article_title)
                .replace("{ARTICLE_DESCRIPTION}",article_description);
    }
    /*TEMPLATE METHODS*/

    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_xpath = getXpathForArticleWith(title,description);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with title '" + title + "' and description '" + description + "'",
                15
        );
    }

    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find 'Search Wikipedia'",
                15
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,"Cannot find 'Search…'",
                5
        );
    }

    public void clearSearchLine() {
        this.waitForElementAndClear(
                SEARCH_INPUT2,
                "Cannot find search input field",
                5
        );
    }

    public String getTextOfSearchLine(){
        return this.waitForElementAndGetAttribute(
                SEARCH_INPUT2,
                "text",
                "Cannot find the search field",
                5
        );
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot click 'X'",
                5
        );
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "'X' didn't disappear",
                5
        );
    }


    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);

        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with substring " + substring,
                15
        );
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);

        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                15
        );
    }

    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find any results",
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public List<String> getTitlesOfFoundArticles(){
        return waitForElementsAndGetAttribute(
                SEARCH_ARTICLE_TITLE,
                "text",
                "Cannot find articles titles",
                15
        );
    }

    public void waitForEmptyResultLabel(){
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULTS_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultsOfSearch(){
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }
}
