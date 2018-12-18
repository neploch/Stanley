package amazon;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class search_harry_potter {

    WebDriver driver;
    public home_page home;
    public utils util;
    public PrintWriter writer;
    String productToSearchFor = "harry potter books";
    String screenShotPath;
    String projectBasePath = "C:\\Users\\Eliyahu\\Downloads\\Stanley\\";

    @Before
    // Defines webdriver.chrome.driver property in Windows OS system
    // Creates timestamped folder to store test results
    // Creates timestamped results.txt file to store test run results
    // Defines the timestamped path for the screenshot
    // Initializes home_page and utils classes instances
    // Opens the Home page
    public void setup() throws FileNotFoundException, UnsupportedEncodingException {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Eliyahu\\Downloads\\chromedriver_win32\\chromedriver.exe");
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        new File(projectBasePath + timestamp).mkdirs();
        writer = new PrintWriter(projectBasePath + timestamp + "\\results_" + timestamp + ".txt", "UTF-8");
        screenShotPath = projectBasePath + timestamp+ "\\screenShot_" + timestamp + ".jpg";
        driver = new ChromeDriver();
        home = new home_page(driver, writer);
        home.get_home();
        util = new utils();
    }
    @Test
    // Searches the amazon home page for parametrized product text string
    // Finds the total results amount for the queried product text string
    // Finds longest product name between the presented search results
    // Finds lowest rated product between the presented search results
    // Finally performs the screenshot
    public void search_harry_potter_book() throws IOException, AWTException {
        try {
            home.searchForProduct(productToSearchFor);
            home.findTotalResults();
            home.findLongestProductName(productToSearchFor);
            home.findLowestRatedProduct(productToSearchFor);
        }
        catch (NoResultsFoundException e)
        {
            System.out.println("No results found for the input string \"" + productToSearchFor + "\".");
            writer.println("No results found for the input string \"" + productToSearchFor + "\".");
        }
        util.captureScreen(screenShotPath);
    }
    @After
    public void tearDown()
    {
        writer.close();
        driver.quit();
    }
}