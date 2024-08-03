package Assignment;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FitPeoTest {
    public static void main(String[] args) {
        
        // Initialize WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver-win64\\chromedriver.exe");
     // Initialize WebDriver
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
        	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Navigate to the FitPeo Homepage
            driver.get("www.fitpeo.com");

            // Step 2: Navigate to the Revenue Calculator Page
            WebElement revenueCalculatorLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("/revenue-calculator")));
            revenueCalculatorLink.click();

            // Step 3: Scroll Down to the Slider Section
            WebElement sliderSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='range']"))); 
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sliderSection);

            // Step 4: Adjust the Slider
            WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='range']"))); 
            WebElement sliderValueField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='range']"))); 

            // Set slider value to 820
            Actions actions = new Actions(driver);
            actions.clickAndHold(slider).moveByOffset(100, 0).release().perform(); // Adjust offset
            wait.until(ExpectedConditions.textToBePresentInElement(sliderValueField, "820"));

            // Step 5: Update the Text Field
            WebElement textField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='number']"))); 
            textField.clear();
            textField.sendKeys("560");
            wait.until(ExpectedConditions.textToBePresentInElement(sliderValueField, "560"));

            // Step 6: Select CPT Codes
            String[] cptCodes = {"#p.MuiTypography-body1:contains('CPT-99091') + div input[type='checkbox']", "#p.MuiTypography-body1:contains('CPT-99453') + div input[type='checkbox']", "#p.MuiTypography-body1:contains('CPT-99454') + div input[type='checkbox']", "#p.MuiTypography-body1:contains('CPT-99474') + div input[type='checkbox']"}; 
            for (String code : cptCodes) {
                WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(code)));
                checkbox.click();
            }

            // Step 7: Validate Total Recurring Reimbursement
            WebElement totalReimbursementHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body[@class='__variable_469f07 __variable_122d8d __variable_5f3c75 __variable_4a931b __variable_be5ae6']"))); 
            String headerText = totalReimbursementHeader.getText();
            if (headerText.contains("$110700")) {
                System.out.println("Total Recurring Reimbursement validation passed.");
            } else {
                System.out.println("Total Recurring Reimbursement validation failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
