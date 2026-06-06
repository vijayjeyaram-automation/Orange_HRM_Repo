package pkg_login;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_Orange_HRM_Valid_Login {

    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeTest
    public void setup() {
        try {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();

            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Username']")));

        } catch (Exception e) {
            Assert.fail("Setup failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void validLoginTest() {
        try {
            driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
            driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
            driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

            // Wait for dashboard/home page to load
            wait.until(ExpectedConditions.titleContains("OrangeHRM"));

            Assert.assertTrue(driver.getTitle().contains("OrangeHRM"),
                    "Login failed: Title mismatch");

        } catch (Exception e) {
            Assert.fail("Login test failed due to exception: " + e.getMessage());
        }
    }

    @AfterTest
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.out.println("Driver cleanup failed: " + e.getMessage());
        }
    }
}