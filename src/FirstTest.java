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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String textOfTheSearchField = SearchPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/search_src_text"),
                "text",
                "Cannot find the search field",
                5
        );
        assertEquals(
                "We don't see Search…",
                "Search…",
                textOfTheSearchField
        );
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
    public void testSwipeArticle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        SearchPageObject.swipeUp(2000);
        SearchPageObject.swipeUp(2000);
        SearchPageObject.swipeUp(2000);
        SearchPageObject.swipeUp(2000);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        // Adding Java
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String articleTitle = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        // Adding Python
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Python");
        SearchPageObject.clickByArticleWithSubstring("General-purpose, high-level programming language");
        String articleTitle2  = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToMyAlreadyCreatedList(name_of_folder);
        backgroundApp(5);
        ArticlePageObject.closeArticle();

        // Removing the second article from the folder
        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(articleTitle2);

        // Check the title of the remaining (1st) article
        MyListsPageObject.clickByArticleWithTitle(articleTitle);
        String articleTitleOfSavedArticle = ArticlePageObject.getArticleTitle();
        assertEquals(
                "We see unexpected title",
                articleTitle,
                articleTitleOfSavedArticle
        );
    }

    @Test
    public void testArticleTitlePresence(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.assertTitlePresent();
    }
}
