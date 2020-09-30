package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Utils.utility;

public class base {
	public static WebDriver driver;
	public static Properties prop;
	public static WebElement element;
	public static ExtentReports report;
	public static ExtentTest testReport;
	public static SoftAssert softAssert = new SoftAssert();
	public static String str = System.getProperty("user.dir");

	public base() { // constractor
		prop = new Properties();
		try {
			String str = System.getProperty("user.dir");
			System.out.println(str);
			// Loading config properties
			FileInputStream configProperties = new FileInputStream(
					str + "\\src\\test\\java\\Repository\\config.properties");
			prop.load(configProperties);
			// ToolsQA home page
			FileInputStream toolsQAHome = new FileInputStream(
					str + "\\src\\test\\java\\Repository\\toolsQAhome.properties");
			prop.load(toolsQAHome);
			// practiceForm.properties
			FileInputStream practiceForm = new FileInputStream(
					str + "\\src\\test\\java\\Repository\\practiceForm.properties");
			prop.load(practiceForm);
			/////////////////////////// Create
			/////////////////////////// reports////////////////////////////////////////////
			String pth;
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
			LocalDateTime now = LocalDateTime.now();
			pth = now.format(fmt);
			System.out.println(pth);

			String path = System.getProperty("user.dir") + "\\Reports\\" + pth + ".html";
			System.out.println(path);

			ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);

			reporter.config().setReportName("Tools QA Report");
			reporter.config().setDocumentTitle("Test Result");

			report = new ExtentReports();
			report.attachReporter(reporter);
			report.setSystemInfo("Tester", "Rajib Deb");

		} catch (Exception e) {
			System.out.println("issue in properties file");
		}
	}

	public static void initialize() { // define the browser
		String str = System.getProperty("user.dir");
		String browserName = prop.getProperty("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--disable-notifications");

			System.setProperty("webdriver.chrome.driver", str + "//Drivers//chromedriver.exe");
			driver = new ChromeDriver(option);
		} else if (browserName.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.gecko.driver", str + "//Drivers//geckodriver.exe");
			driver = new FirefoxDriver();
		}

	}

	public static void launchBrowser() {
		try {
			driver.get("https://demoqa.com/");
			System.out.println("The page title is: " + driver.getTitle());
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		} catch (Exception e) {
			System.out.println("Failed in launchBrowser");
		}

	}

	public static WebElement getElement(String locatorKey) {
		try {
			if (locatorKey.endsWith("_id")) {
				element = driver.findElement(By.id(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("name")) {
				element = driver.findElement(By.name(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("classname")) {
				element = driver.findElement(By.className(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("linktext")) {
				element = driver.findElement(By.linkText(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_xpath")) {
				element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("cssselector")) {
				element = driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
			} else {
				System.out.println("Element not found");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return element;
	}

	public static void verticalScrollPage(String dis) { // vertical scorlling
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + dis + ")");

	}

	public static void click(String locatorKey) {

		getElement(locatorKey).click();

	}

	public static String getText(String locatorKey) {
		String locatorText = getElement(locatorKey).getText();
		return locatorText;
	}

	public static void impliciteWait(int timeInSecs) {
		driver.manage().timeouts().implicitlyWait(timeInSecs, TimeUnit.SECONDS);
	}

	/*
	 * public void extentReport() {
	 * 
	 * String pth; DateTimeFormatter fmt =
	 * DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"); LocalDateTime now =
	 * LocalDateTime.now(); pth = now.format(fmt); System.out.println(pth);
	 * 
	 * String path = System.getProperty("user.dir") + "\\Reports\\"+pth+".html";
	 * System.out.println(path);
	 * 
	 * ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);
	 * 
	 * reporter.config().setReportName("Tools QA Report");
	 * reporter.config().setDocumentTitle("Test Result");
	 * 
	 * report = new ExtentReports(); report.attachReporter(reporter);
	 * report.setSystemInfo("Tester", "Rajib Deb");
	 * 
	 * }
	 */

	public static void createTest(String testNumber) {

		testReport = report.createTest(testNumber);

	}

	public static void expliciteClickableWait(String locatorKey) {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.elementToBeClickable(getElement(locatorKey)));
	}

	public static void input(String locatorKey, String value) {
		getElement(locatorKey).sendKeys(value);
	}

	public static void captureScreenshot(String testCaseName, String caption) throws Exception {

		
		try {
		///////////// create word document///////////////////////////////////////
		XWPFDocument docx = new XWPFDocument();
		FileOutputStream out = new FileOutputStream(str + "//Test Evidence//" + testCaseName + ".docx");
		XWPFParagraph tmpParagraph = docx.createParagraph();
		XWPFRun tmpRun = tmpParagraph.createRun();
		
		

		////////////// taking image in .png format////////////////////////////////

		TakesScreenshot sch = (TakesScreenshot) driver;
		File source = sch.getScreenshotAs(OutputType.FILE);
		File des = new File(str + "//Test Evidence//" + testCaseName + ".png");
		FileUtils.copyFile(source, des);
		InputStream pic = new FileInputStream(str + "//Test Evidence//" + testCaseName + ".png");

		
		tmpRun.addBreak(BreakType.TEXT_WRAPPING);
		tmpRun.setText(caption);
		tmpRun.setFontSize(20);
		tmpRun.setBold(true);
		tmpRun.addPicture(pic, XWPFDocument.PICTURE_TYPE_JPEG, testCaseName, Units.toEMU(500), Units.toEMU(500));

		pic.close();
		des.delete();
		docx.write(out);
		out.close();
		docx.close();
		}catch(Exception e) {
			System.out.println("Error in word document");
		}

	}
	
	public static String images(String title) throws IOException {
		TakesScreenshot sch = (TakesScreenshot) driver;
		File source = sch.getScreenshotAs(OutputType.FILE);
		File des = new File(str + "//Test Evidence//" + title + ".png");
		FileUtils.copyFile(source, des);
		String destination= str + "//Test Evidence//" + title + ".png";
		return destination;
		
	}

}
