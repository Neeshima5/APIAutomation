package com.paytm.smartretail_api_automation;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.paytm.smartretail_api_automation.dto.orderapi.Invoice;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestClass {

//	@Test
	public void testRestAssuredJson() {
		RestAssured.baseURI = "http://localhost:8080/api/";
		RestAssured.basePath = "catalog/category/items";
		Response response = RestAssured.given().when().get();
		System.out.println(response.jsonPath().getList("$"));
	}

//	@Test
	public void testSelenium() {

		System.setProperty("webdriver.chrome.driver", "/home/neeshima/Documents/chromedriver_linux64/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.google.com");
		System.out.println("Successfully opened browser...............!!!!!!!!!!");
		driver.findElement(By.name("q")).sendKeys("amazon india");
		driver.findElement(By.name("q")).submit();
//		driver.findElement(By.name("q")).sendKeys(Keys.ENTER);      // same as element.submit()
		String title = driver.getTitle();
		System.out.println("Title : " + title);
//		driver.quit();
	}

}
