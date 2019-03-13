package indiaMail;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.ExtentReports;

public class Mail {
	public static void main( String args[] ) throws FilloException, InterruptedException {

		int Testcount= FetchTestcount("Credentials");
		System.out.println("Totalcount"+Testcount);
		for (int i = 0; i < Testcount; i++) {	
			String TestCase= FetchTestID( "Credentials");
			System.out.println("Testcase is"+TestCase);/// test case
			String Userid= FetchValue( "Credentials",TestCase,"Username");
			String Userpwd= FetchValue( "Credentials",TestCase,"Pswd");
			System.out.println("Phone number is "+Userid);
			UpdateTestcase("Credentials","E",TestCase);//updating test case as E
			System.setProperty("webdriver.chrome.driver", "C:/Users/slingala/chromedriver.exe");
			WebDriver driver=new ChromeDriver();
			driver.get("https://mail.india.com/account/login");	
			driver.manage().window().maximize();
			TimeUnit.SECONDS.sleep(10);
			System.out.println(Userid);
			driver.findElement(By.xpath("//*[@name='user[email]']")).sendKeys(Userid);
			System.out.println(Userpwd);
			driver.findElement(By.xpath("//*[@name='user[password]']")).sendKeys(Userpwd);
			driver.findElement(By.xpath("//*[@name='Submit']")).click();
			TimeUnit.SECONDS.sleep(10);
			if (driver.findElement(By.xpath("//div[@class='user']")).isDisplayed()) 
				
			{   
				UpdateTestcase("Credentials","P",TestCase);


			

			}

			else{
				
				UpdateTestcase("Credentials","F",TestCase);}

			//ExtentReports reports = new ExtentReports("C:/Users/slingala");
			//reports.endTest(TestCase);
			}
			//webDriver.Dispose() ;
		
			}
				
		
	
		
		public static int FetchTestcount(String Tablename) throws FilloException 
	       {
	Fillo fillo=new Fillo();
	com.codoid.products.fillo.Connection connection=fillo.getConnection("C:/Users/slingala/MailCheck.xlsx");
	String strQuery="Select count(*) from"+" " + Tablename +" "+ "where ExecutionFlag='Y'"  ;
	Recordset recordset=connection.executeQuery(strQuery);
	 int count = recordset.getCount();
	 return count;           
	        }
		
		
		public static String FetchValue(String Tablename, String Testid,String Feild) throws FilloException 
	      {
	Fillo fillo=new Fillo();
	com.codoid.products.fillo.Connection connection=fillo.getConnection("C:/Users/slingala/MailCheck.xlsx");
	String strQuery="Select * from"+" " + Tablename +" "+ "where TestID='"+Testid+"'"  ;
	Recordset recordset=connection.executeQuery(strQuery);
	while(recordset.next())   {
	String PHN = (recordset.getField(Feild)); 
	strQuery= PHN;
		                       }
	return strQuery;
	  }
		
		public static String FetchTestID(String Tablename)
	     {
	try {
		Fillo fillo=new Fillo();
		com.codoid.products.fillo.Connection connection=fillo.getConnection("C:/Users/slingala/MailCheck.xlsx");
		String strQuery="Select Distinct TestID from"+" " + Tablename +" "+ "where ExecutionFlag='Y' and Status=''"  ;
		Recordset recordset=connection.executeQuery(strQuery);
		while(recordset.next())   {
		String PHN = (recordset.getField("TestID")); 
		Tablename= PHN;
			                       }
		//return strQuery;
	} 
	catch (Exception e) {
		
		System.out.println("No tc to execute");
		System.exit(0);
	}
	return Tablename ;
	  }
	      

	//below code is to update status  of test_id
	public static void UpdateTestcase(String Tablename,String Expstatus, String Testid) throws FilloException 
	     {
		String query="Update"+" "+Tablename+" "+ "set Status='"+Expstatus+"' where TestID='"+Testid+"' ";
		String 	fileName="C:/Users/slingala/MailCheck.xlsx";
			 Fillo fillo = new Fillo();
		        com.codoid.products.fillo.Connection connection = fillo.getConnection(fileName);
		        connection.executeUpdate(query);
		        connection.close();
	         
	      }

}