package lib.tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

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
        SearchPageObject.waitForCancelButtonToDissapear();
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

}
