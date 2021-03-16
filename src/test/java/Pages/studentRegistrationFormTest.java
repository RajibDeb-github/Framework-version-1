package Pages;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;


import Base.base;

public class studentRegistrationFormTest extends base {
	
	@BeforeClass
	public static void preCond() {
		initialize();		
		launchBrowser("https://demoqa.com/");
	}
	@BeforeMethod
	public static void navigateToMainPage() {
		try {
		impliciteWait(2);
		verticalScrollPage("600");
		impliciteWait(2);
		click("forms_xpath");
		impliciteWait(3);
		verticalScrollPage("600");
		expliciteClickableWait("practiceForm_xpath");
		click("practiceForm_xpath");
		impliciteWait(4);
		//System.out.println("The page title is :"+driver.getTitle());
		}
		catch(Exception e) {
			System.out.println("Issue in before method");
			Assert.fail();
		}
	}
	
	/*@BeforeTest
	public void initiateReport() {
		extentReport();

	}*/
	
	@Test(priority=1,enabled=true)
	public static void TC1() {
		
		createTest("Test Case 1: Verify the page name in the Practice Form page");
		
		try {		
		impliciteWait(5);
		captureScreenshot("Test_Case_1", "Practice form landing page");
		impliciteWait(2);
		testReport.info("Navigate to Practice form");
		String pageName=getText("practiceFormTitle_xpath");
		testReport.info("Page name " +pageName);
		//testReport.log(LogStatus.PASS, "Snapshot below: " + testReport.addScreenCapture(screenShotPath));
		impliciteWait(2);
		softAssert.assertEquals(pageName, "Practice Form"); //hard assert
		
		testReport.pass("Page name is populated correctly : "+pageName);
		
		/*if(pageName.equalsIgnoreCase("practice form")) {
			//System.out.println("Pass");
			testReport.pass("Page name is populated correctly");
		}*/
		softAssert.assertAll();
		}
		catch(Exception e) {
			testReport.fail("Test Case 1 failed: Script Flow");
			//Assert.fail();
		}
	report.flush();

	}
	
	@Test(priority=2, enabled=true)
	public static void TC2() {
		createTest("Test Case 2: Field level verification in the Practice Form page");
		try {		
			impliciteWait(5);
			testReport.info("Navigate to Practice form");			
			input("firstName_id","Rajib");
			testReport.info("Insert first name");
			input("lastName_id","Deb");
			testReport.info("Insert last name");
			captureScreenshot("Test_Case_2", "username & password in application");
			//click("gender_xpath");
			testReport.info("Insert gender");
			input("email_id","rajib.deb1991@gmail.com");
			testReport.info("Insert email");
			input("mobile_id","8981603801");
			testReport.info("Insert mobile number");
			
			/*input("subject_id","Testing");
			testReport.info("Insert subject");*/
			captureScreenshot("Test_Case_2", "Input fields in practice forms");
			
		}
		catch(Exception e) {
			e.getMessage();
			testReport.fail("Testcase 2 failed: Script Flow");
		}
	report.flush();

	}
	@Test(priority=3, enabled=true)
	public static void TC3() {
		createTest("Test Case 3: Upload the window based file with AutoIT");
		try {
			impliciteWait(5);
			testReport.info("Navigate to Practice form");			
			input("firstName_id","Rajib");
			testReport.info("Insert first name");
			input("lastName_id","Deb");
			testReport.info("Insert last name");
			captureScreenshot("Test_Case_3", "Navigate to Practice form");
			verticalScrollPage("200");
			expliciteClickableWait("chooseFile_xpath");
			doubleClick("chooseFile_xpath");
			impliciteWait(5);
			Runtime.getRuntime().exec("c:\\Rajib\\Java\\ToolsQAAutoIT_uploadFile1.exe");
			impliciteWait(3);
			captureScreenshot("Test_Case_3", "The File is uploaded");
			testReport.pass("The file is uploaded");
			
			
		}catch(Exception E) {
			E.getMessage();
			testReport.fail("Testcase 3 failed: Script Flow");
		}
		report.flush();
	}
	
	@AfterMethod
	public static void methodEndTest(ITestResult result) throws IOException {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			String title="Failed Screenshot";
			testReport.fail("Failed in Data Validation");
			testReport.addScreenCaptureFromPath(images(title), title);
		}
		driver.navigate().back();
		driver.navigate().back();
		
		
	}
	
	@AfterClass
	public static void logout() {
		driver.quit();
	}
	

}
