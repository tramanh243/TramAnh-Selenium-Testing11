package selenium;


//<input data-v-1f99f73c="" class="oxd-input oxd-input--active" name="username" placeholder="Username" autofocus="">
// flow tao test case
// B1: khoi tao trinh duyet
// B2: Dieu huong toi website muon test
// B3: tim cac element, locator (input, button, label,...)
// de phuc vu viet cac step test
// B4: nhap du lieu, thao tac tren element
// B5: Verify ket qua (expect, actual)
// B6: Dong trinh duyet va giai phong tai nguyen

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigUtils;

import java.time.Duration;

public class OrangeHRMLoginTest {
    //    B1: khoi tao trinh duyet
//    driver la class giup tuong tac tren page
    private WebDriver driver;

    private WebDriverWait wait;

    //    URL cua page Login
//    private static final String LOGIN_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
//
//    //    username
//    private static final String USERNAME = "Admin";
//    private static final String PASSWORD = "admin123";

//    define cac element, locators
//    private static final By USERNAME_iNPUT = By.xpath("//input[@name='username']");
//    Cach 2:
    private static final By USERNAME_iNPUT = By.name("username");
//    By.cssSelector("input[name='username']");
//    By.cssSelector("input[placeholder='username']");

//    private static final By PASSWORD_INPUT = By.xpath("//input[@name='password']");
    private static final By PASSWORD_INPUT = By.cssSelector("input[name='password']");
//    By.name("password");

    private static final By LOGIN_BUTTON = By.xpath("//button[@type='submit']");
//    By.cssSelector("button[type='submit']");
//    By.name("login");
//    By.cssSelector("button.oxd-button--main");

    private static final By USER_DROPDOWN = By.xpath("//li[@class='oxd-userdropdown']");
//    By.cssSelector("li.oxd-userdropdown");
//    By.name("user-dropdown");
//    By.id("user-dropdown");
//    By.className("oxd-userdropdown");

    private static final By LOGOUT_BUTTON = By.linkText("Logout");
//    By.xpath("//a[text()='Logout']");
//    By.xpath("//ahref='/web/index.php/auth/logout'");

    //    setup moi truong test
//    before method: chay trước môỗi test case
//    VD: khoi tao driver
    @BeforeMethod
    public void setUp() {
//        B1: Setup chrome driver voi webdriver manager
        WebDriverManager.chromedriver().setup();

        //        B2: cau hinh chrome
        ChromeOptions options = new ChromeOptions();
//        mo browser o che do full screen
//        - Dam bao tat ca các elements deu hien thi
//        - Test nhat quan tren moi man hinh
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        driver = new ChromeDriver(options);
//        B3: Co thoi gian doi chrome setup
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test(description = "Test login thành công")
    public void testLoginSuccess() throws InterruptedException {
//        B1: truy cap trang web Login
        driver.get(ConfigUtils.getLoginUrl());
//        Thread.sleep(10000);
//        Doi co dieu kien: doi den khi username input xuat hienj tren UI
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_iNPUT));
//        B2: tim element input username va fill username vao
        WebElement usernameField = driver.findElement(USERNAME_iNPUT);
        usernameField.sendKeys(ConfigUtils.getUsername());
        Thread.sleep(2000);

//        B3: tim element input password va fill password
        WebElement passwordField = driver.findElement(PASSWORD_INPUT);
        passwordField.sendKeys(ConfigUtils.getPassword());
        Thread.sleep(2000);
//        B4: tim element button Login va click vao button
        WebElement loginBtn = driver.findElement(LOGIN_BUTTON);
        loginBtn.click();
//        Thread.sleep(2000);
//        Doi co dieu kien: doi den khi xuat hien url co chua "dashboard"
        wait.until(ExpectedConditions.urlContains("dashboard"));

//        B5: verify ket qua sau khi thao tac login
//        TH1: kiem tra URL co chua "dashboard" khong
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("dashboard"),
                "URL phải chứa 'dashboard' sau khi login"
        );
    }

    @Test
    public void testLogout() throws InterruptedException {
        //        B1: truy cap trang web Login
        driver.get(ConfigUtils.getLoginUrl());
//        Thread.sleep(10000);
//        Doi co dieu kien: doi den khi username input xuat hienj tren UI
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_iNPUT));
//        B2: tim element input username va fill username vao
        WebElement usernameField = driver.findElement(USERNAME_iNPUT);
        usernameField.sendKeys(ConfigUtils.getUsername());
        Thread.sleep(2000);

//        B3: tim element input password va fill password
        WebElement passwordField = driver.findElement(PASSWORD_INPUT);
        passwordField.sendKeys(ConfigUtils.getPassword());
        Thread.sleep(2000);
//        B4: tim element button Login va click vao button
        WebElement loginBtn = driver.findElement(LOGIN_BUTTON);
        loginBtn.click();
//        Thread.sleep(2000);
//        Doi co dieu kien: doi den khi xuat hien url co chua "dashboard"
        wait.until(ExpectedConditions.urlContains("dashboard"));

//        Logout
//        Click vao avatar de mo dropdown
        wait.until(ExpectedConditions.elementToBeClickable(USER_DROPDOWN)).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON)).click();

//        Doi ve trang login
        wait.until(ExpectedConditions.urlContains("/auth/login"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("/auth/login"),
                "sau khi logout phai tro ve trang login"
        );
    }

    //    after method: cleanup - dong browser, giai phong tai nguyen
    @AfterMethod
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }
}