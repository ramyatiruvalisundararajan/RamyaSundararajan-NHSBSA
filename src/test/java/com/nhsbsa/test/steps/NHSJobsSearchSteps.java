package com.nhsbsa.test.steps;

import com.nhsbsa.test.pages.NHSJobsSearchPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class NHSJobsSearchSteps {

    private WebDriver driver;
    private NHSJobsSearchPage searchPage;
    private static final String SORT_DATE_NEWEST = "Date Posted (newest)";
    private static final String SORT_DATE_OLDEST = "Date Posted (oldest)";
    private static final String SORT_CLOSING_DATE = "Closing Date";

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        searchPage = new NHSJobsSearchPage(driver);
    }
    

    @Given("I am on the NHS Jobs search page")
    public void i_am_on_the_nhs_jobs_search_page() {
        searchPage.open();
    }

    @When("I enter {string} in the job title field")
    public void i_enter_in_the_job_title_field(final String jobTitle) {
        searchPage.enterJobTitle(jobTitle);
    }

    @When("I enter {string} in the location field")
    public void i_enter_in_the_location_field(final String location) {
        searchPage.enterLocation(location);
    }

    @When("I click on the Search button")
    public void i_click_on_the_search_button() {
        searchPage.clickSearch();
    }

    @When("I select {string} from the sort dropdown")
    public void i_select_from_the_sort_dropdown(final String option) {
        searchPage.selectSortOption(option);
    }

    @Then("I should see job listings related to {string}")
    public void i_should_see_job_listings_related_to(final String jobTitle) {
        Assert.assertTrue("No job listings found for: " + jobTitle, searchPage.hasJobListings());

        List<WebElement> titles = searchPage.getJobTitles();
        boolean matchFound = titles.stream()
                .anyMatch(e -> e.getText().toLowerCase().contains(jobTitle.toLowerCase()));
        Assert.assertTrue("No job listings matched the title", matchFound);
    }

    @Then("the search results are sorted by Date Posted newest")
    public void checkSortByDropdown() {
        String selected = searchPage.getSelectedSortOption();
        Assert.assertEquals(SORT_DATE_NEWEST, selected);
    }

    @Then("the job listings should be sorted by the newest date posted first")
    public void the_job_listings_should_be_sorted_by_newest_date_posted_first() {
        List<WebElement> dateElements = searchPage.getDatePostedElements();
        Assert.assertTrue("No date elements found", dateElements.size() > 1);

        List<LocalDate> dates = dateElements.stream()
                .map(e -> parseDatePosted(e.getText()))
                .toList();

        for (int i = 0; i < dates.size() - 1; i++) {
            Assert.assertTrue("Dates not sorted descending: " + dates.get(i) + " before " + dates.get(i + 1),
                    !dates.get(i).isBefore(dates.get(i + 1)));
        }
    }

    @Then("I should see a message {string}")
    public void i_should_see_a_message(final String expectedMessage) {
        Assert.assertTrue("Expected no results message but it was not displayed", searchPage.hasNoResultsMessage());
        String actual = searchPage.getNoResultsMessage().trim();
        Assert.assertEquals(expectedMessage, actual);
    }

    @Then("the search results are sorted by Date Posted oldest")
    public void checkSortByDropdownOldest() {
        String selected = searchPage.getSelectedSortOption();
        Assert.assertEquals(SORT_DATE_OLDEST, selected);
    }

    @Then("the job listings should be sorted by the oldest date posted first")
    public void the_job_listings_should_be_sorted_by_oldest_date_posted_first() {
        List<WebElement> dateElements = searchPage.getDatePostedElements();
        Assert.assertTrue("No date elements found", dateElements.size() > 1);

        List<LocalDate> dates = dateElements.stream()
                .map(e -> parseDatePosted(e.getText()))
                .toList();

        for (int i = 0; i < dates.size() - 1; i++) {
            Assert.assertTrue(
                    "Dates not sorted ascending",
                    !dates.get(i).isAfter(dates.get(i + 1))  
            );
        }
    }

    @Then("I should see job listings")
    public void i_should_see_job_listings() {
        Assert.assertTrue("Expected job listings, but none found", searchPage.hasJobListings());
    }

    @Then("the search results are sorted by Closing Date")
    public void checkSortByClosingDate() {
        String selected = searchPage.getSelectedSortOption();
        Assert.assertEquals(SORT_CLOSING_DATE, selected);

        List<WebElement> dateElements = searchPage.getClosingDateElements();
        Assert.assertTrue("No date elements found", dateElements.size() > 1);

        List<LocalDate> dates = dateElements.stream()
                .map(e -> parseDatePosted(e.getText()))
                .toList();

        for (int i = 0; i < dates.size() - 1; i++) {
            Assert.assertTrue(
                    "Dates not sorted ascending",
                    !dates.get(i).isAfter(dates.get(i + 1))
            );
        }
    }

    @Then("I should see the location not found header {string}")
    public void i_should_see_location_not_found_header(final String expectedHeader) {
        String actualHeader = searchPage.getLocationNotFoundHeader();
        Assert.assertEquals(expectedHeader, actualHeader);
    }

    @Then("I should see the location help message {string}")
    public void i_should_see_location_help_message(final String expectedMessage) {
        String actualMessage = searchPage.getLocationHelpMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Helper method to parse dates in the format "d MMMM yyyy", e.g. "25 June 2025".
     */
    private LocalDate parseDatePosted(final String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        return LocalDate.parse(dateString.trim(), formatter);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
