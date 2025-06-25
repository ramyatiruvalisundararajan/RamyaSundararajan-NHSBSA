package com.nhsbsa.test.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

public class NHSJobsSearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String URL = "https://www.jobs.nhs.uk/candidate/search";

    private final By jobTitleField = By.id("keyword");
    private final By locationField = By.id("location");
    private final By searchButton = By.id("search");
    private final By jobTitles = By.cssSelector("a[data-test='search-result-job-title']");
    private final By datePosted = By.cssSelector("li[data-test='search-result-publicationDate'] strong");
    private final By sortByDropdown = By.id("sort");
    private final By noResultsMessage = By.id("no-result-title");
    private final By locationHelpMessage = By.cssSelector("div.nhsuk-grid-row div:nth-of-type(3)");
    private final By locationNotFoundHeader = By.cssSelector("h2[data-test='search-result-query']");
    private final By closingDate = By.cssSelector("li[data-test='search-result-closingDate'] strong");

    public NHSJobsSearchPage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get(URL);
    }

    public void enterJobTitle(final String title) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitleField));
        element.clear();
        element.sendKeys(title);
    }

    public void enterLocation(final String location) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locationField));
        element.clear();
        element.sendKeys(location);
    }

    public void clickSearch() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        element.click();
    }

    public List<WebElement> getJobTitles() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitles));
        return driver.findElements(jobTitles);
    }

    public List<WebElement> getDatePostedElements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(datePosted));
        return driver.findElements(datePosted);
    }

    public String getSelectedSortOption() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortByDropdown));
        Select select = new Select(dropdown);
        return select.getFirstSelectedOption().getText();
    }

    public void selectSortOption(final String option) {
        Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(sortByDropdown)));
        select.selectByVisibleText(option);
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitles)); // wait for updated results
    }

    public String getNoResultsMessage() {
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage));
        return msg.getText();
    }

    public boolean hasJobListings() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitles));
            return !driver.findElements(jobTitles).isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean hasNoResultsMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getLocationHelpMessage() {
        try {
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(locationHelpMessage));
            return msg.getText().trim();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public String getLocationNotFoundHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locationNotFoundHeader)).getText().trim();
    }

    public List<WebElement> getClosingDateElements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(closingDate));
        return driver.findElements(closingDate);
    }
}
