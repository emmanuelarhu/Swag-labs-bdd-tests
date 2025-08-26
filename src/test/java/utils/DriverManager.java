package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.time.Duration;

/**
 * WebDriver management utility class
 * Handles driver initialization, configuration, and cleanup
 */
public class DriverManager {

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Initialize WebDriver based on browser type
     */
    public static void initializeDriver(String browserType) {
        WebDriver driver = createDriver(browserType.toLowerCase());
        configureDriver(driver);
        driverThreadLocal.set(driver);
    }

    /**
     * Create WebDriver instance based on browser type
     */
    private static WebDriver createDriver(String browserType) {
        WebDriver driver;

        switch (browserType) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = getChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = getFirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = getEdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;

            case "headless-chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions headlessChromeOptions = getChromeOptions();
                headlessChromeOptions.addArguments("--headless");
                driver = new ChromeDriver(headlessChromeOptions);
                break;

            default:
                throw new IllegalArgumentException("Browser type not supported: " + browserType);
        }

        return driver;
    }

    /**
     * Configure Chrome options
     */
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");

        // For CI environments
        if (isRunningInCI()) {
            options.addArguments("--headless");
            options.addArguments("--disable-background-timer-throttling");
            options.addArguments("--disable-backgrounding-occluded-windows");
            options.addArguments("--disable-renderer-backgrounding");
        }

        return options;
    }

    /**
     * Configure Firefox options
     */
    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();

        if (isRunningInCI()) {
            options.addArguments("--headless");
        }

        return options;
    }

    /**
     * Configure Edge options
     */
    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        if (isRunningInCI()) {
            options.addArguments("--headless");
        }

        return options;
    }

    /**
     * Configure common driver settings
     */
    private static void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    /**
     * Get current WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call initializeDriver() first.");
        }
        return driver;
    }

    /**
     * Check if driver is initialized
     */
    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }

    /**
     * Quit WebDriver and clean up
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Check if running in CI environment
     */
    private static boolean isRunningInCI() {
        String ci = System.getenv("CI");
        String githubActions = System.getenv("GITHUB_ACTIONS");
        String jenkins = System.getenv("JENKINS_URL");

        return "true".equalsIgnoreCase(ci) ||
                "true".equalsIgnoreCase(githubActions) ||
                jenkins != null;
    }

    /**
     * Get browser type from system property or environment variable
     */
    public static String getBrowserType() {
        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = System.getenv("BROWSER");
        }
        if (browser == null || browser.isEmpty()) {
            browser = "chrome"; // default browser
        }
        return browser;
    }
}