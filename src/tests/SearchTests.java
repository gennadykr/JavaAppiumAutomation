package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");

        // Encountered a problem searching by id:
        // https://github.com/appium/java-client/issues/1047
        // The latest java-client is required to prevent id->css => unsupported error
        // Appium was downgraded from 1.10 -> 1.7 (appium-desktop-Setup-1.2.7.exe)

        SearchPageObject.clearSearchLine();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch(){
        String search_line = "Green Day discography";
        search_line = "Java";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        System.out.println(amount_of_search_results);
        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "zxcvzxcv";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoResultsOfSearch();
    }


    @Test
    public void testCheckSearchAndDiscard() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        int amountOfFoundArticles = SearchPageObject.getAmountOfFoundArticles();
        System.out.println(amountOfFoundArticles);
        assertTrue(
                "Cannot find few articles for the search",
                amountOfFoundArticles > 2 // 3 is already "few"
        );
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultsOfSearch();
    }

    @Test
    public void testCheckFoundContent() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");

        List<String> titles = SearchPageObject.getTitlesOfFoundArticles();

        long numberOfArticlesFound = titles.stream().peek(
                (e) -> {
                    System.out.println(e);
                    assertTrue(
                            "Article's title doesn't contain Java (case-insensitive)",
                            e.toLowerCase().contains("java")
                    );
                }
        ).count();
        System.out.println(numberOfArticlesFound);
    }

    @Test
    public void testCheckTextInSearchField() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        assertEquals(
                "We don't see Search…",
                "Search…",
                SearchPageObject.getTextOfSearchLine()
        );
    }

    @Test
    public void testFirstThreeResultsPast(){
        Map<String, String> expected_articles = new HashMap();
        expected_articles.put("Java", "Island of Indonesia");
        expected_articles.put("JavaScript", "Programming language");
        expected_articles.put("Java (programming language)", "Object-oriented programming language");

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");

        expected_articles.forEach((key, value) -> SearchPageObject.waitForElementByTitleAndDescription(key, value));
    }
}
