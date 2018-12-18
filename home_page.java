package amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class home_page {
    WebDriver driver;
    PrintWriter writer;
    WebDriverWait wait;
    Actions action;
    String HomePageURL = "https://www.amazon.com/";
    By searchInputField = By.name("field-keywords");
    By goBtn = By.cssSelector("[value='Go']");
    By results = By.id("s-result-count");
    By showResultsFor = By.xpath("//*[contains(text(),'Show results for')]");
    String resultName = "//*[contains(@data-attribute,'%s')]";
    String productRating = "//*[contains(@data-attribute,'%s')]/../../../..//span[@class='a-icon-alt']";

    //  Constructor method initializes driver and writer inside the home page instance
    public home_page(WebDriver driver, PrintWriter writer)
    {
        this.driver = driver;
        this.writer = writer;
    }
    //  Open the Home page and maximize the window
    public void get_home()
    {
        driver.get(HomePageURL);
        driver.manage().window().maximize();
    }
    // Insert the search text string into the search input field and press the Go button
    public void searchForProduct(String productName)
    {
        wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(searchInputField));
        driver.findElement(searchInputField).sendKeys(productName);
        driver.findElement(goBtn).click();
    }
    // Find the total amount of found results for the input string query
    // Method throws NoResultsFoundException special exception in case no results found for the input string query
    // The result is outputted to the console and is written to the file
    public void findTotalResults() throws NoResultsFoundException {
        String rawResults;
        int firstSpaceIndex;
        int secondSpaceIndex = 0;
        int resultsIndex;
        wait = new WebDriverWait(driver,10);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(showResultsFor));
        }
        catch (Exception e)
        {
            throw new NoResultsFoundException();
        }
        rawResults = driver.findElement(results).getText();
        resultsIndex = rawResults.indexOf("results");
        firstSpaceIndex = rawResults.indexOf(" ");
        if (firstSpaceIndex+1 >= resultsIndex)
        {
            System.out.println(rawResults.substring(0,firstSpaceIndex)+ " results found");
            writer.println(rawResults.substring(0,firstSpaceIndex)+ " results found");
        }
        else {
            secondSpaceIndex = rawResults.indexOf(" ", firstSpaceIndex + 1);
            while ((secondSpaceIndex + 1) < resultsIndex) {
                firstSpaceIndex = secondSpaceIndex;
                secondSpaceIndex = rawResults.indexOf(" ", firstSpaceIndex + 1);
            }
            System.out.println(rawResults.substring(firstSpaceIndex+1,secondSpaceIndex)+ " results found");
            writer.println(rawResults.substring(firstSpaceIndex+1,secondSpaceIndex)+ " results found");
        }
    }
    // Method finds longest product name from the results displayed for the input string query
    // The result is outputted to the console and is written to the file
    public void findLongestProductName(String productName)
    {
        String [] partialName = productName.split(" ");
        String productNameXpath = String.format(resultName,partialName[0].substring(1));
        List<WebElement> productNameElements = driver.findElements(By.xpath(productNameXpath));
        List<String> productNames = new ArrayList<String>();
        for (WebElement element : productNameElements)
        {
            productNames.add(element.getText());
        }
        String longestName="";
        for(String name : productNames)
        {
            if(name.length() > longestName.length())
            {
                longestName=name;
            }
        }
        System.out.println("The longest name is search results is: " + longestName);
        writer.println("The longest name is search results is: " + longestName);
    }
    // Method finds lowest rated product from the results displayed for the input string query
    // The result is outputted to the console and is written to the file
    public void findLowestRatedProduct(String productName)
    {
        action = new Actions(driver);
        String [] partialName = productName.split(" ");
        String productRatingXpath = String.format(productRating,partialName[0].substring(1));
        List<WebElement> productRatingElements = driver.findElements(By.xpath(productRatingXpath));
        List<String> productRatings = new ArrayList<String>();
        for (WebElement element : productRatingElements)
        {
            productRatings.add(element.getAttribute("textContent"));
        }
        double minRating=5;
        double currentRating;
        for(String rating : productRatings)
        {
            currentRating = Double.parseDouble(rating.substring(0,rating.indexOf(" ")));
            if(currentRating < minRating)
            {
                minRating = currentRating;
            }
        }
        System.out.println("The lowest product rating found is " + minRating + " out of 5 stars");
        writer.println("The lowest product rating found is " + minRating + " out of 5 stars");
    }
}