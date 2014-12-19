package com.king.test;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.king.util.GlobalContext;

public class Test {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", GlobalContext.CHROME_DRIVER);
		//example();
		yihaodian();
	}

	private static void example() {
		// for firefox
		// WebDriver driver = new FirefoxDriver();
		// for chrome
		WebDriver driver = new ChromeDriver();
		// for IE
		// WebDriver driver = new InternetExplorerDriver();
		WebDriverWait w = new WebDriverWait(driver, 10);
		driver.get("http://www.baidu.com/");
		// 等价于 driver.navigate().to("http://www.baidu.com/");
		w.until(ExpectedConditions.visibilityOfElementLocated(By.id("kw")));
		w.until(ExpectedConditions.elementToBeClickable(By.id("su")));
		// Find the text input element by its name
		WebElement element = driver.findElement(By.id("kw"));
		element.sendKeys("liaoxiangui");
		element.submit();
		// WebDriver会自动的找element对应的form，并提交. 等价于：
		// driver.findElement(By.id("su")).click();
		System.out.println("Page title is: " + driver.getTitle());
		// baidu's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		w.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("liaoxiangui");
			}
		});
		System.out.println("Page title is: " + driver.getTitle());
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
		}
		// Close the browser
		driver.quit();
	}

	private static void yihaodian() {
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("--disable-images");
		WebDriver driver = new ChromeDriver(options);
		try {
			driver.get("http://www.yhd.com/ctg/s2/c21289-0-60761/b/a-s1-v0-p15-price-d0-f0-m1-rt0-pid-mid0-k/#page=1&sort=1");

			Boolean first = true;
			int products = 0;
			WebDriverWait ww = new WebDriverWait(driver, 10);

			while (true) {
				System.out.println("processing filter page:" + driver.getCurrentUrl());

				if (first) {
					first = false;
					ww.until(ExpectedConditions.presenceOfElementLocated(By.id("startShopping")));
					WebElement s = driver.findElement(By.id("selectProvince"));
					s.click();
					s = driver.findElement(By.id("p_13"));
					s.click();
					s = driver.findElement(By.id("startShopping"));
					s.click();
				}

				ww.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#itemSearchList > li")));
				// 窗口最大化。 必须使得那个按钮在屏幕上可见，否则会抛异常?
				driver.manage().window().maximize();
				final int liCounter = driver.findElements(By.cssSelector("#itemSearchList > li")).size();
				for (int kk = 0; kk < liCounter; kk++) {
					try {
						String cssStr = "#itemSearchList > li:nth-of-type("+ (kk + 1) + ")";
						WebElement li = driver.findElement(By.cssSelector(cssStr));
						products++;
						String t;
						try {
							ww.until(ExpectedConditions.visibilityOf(li.findElement(By.cssSelector(".owner > a"))));
							t = li.findElement(By.cssSelector(".owner > a")).getAttribute("title");
							System.out.println("vendor name=" + t);
						} catch (Exception e) {
							System.out.println("vendor=自营");
						}

						ww.until(ExpectedConditions.visibilityOf(li.findElement(By.className("electrical_item_box"))));
						t = li.findElement(By.className("electrical_item_box")).getAttribute("comproid");
						System.out.println("selfid=" + t);

						ww.until(ExpectedConditions.visibilityOf(li.findElement(By.cssSelector("div > .search_prod_img > img"))));
						t = li.findElement(By.cssSelector("div > .search_prod_img > img")).getAttribute("src");
						System.out.println("pic url=" + t);

						ww.until(ExpectedConditions.visibilityOf(li.findElement(By.cssSelector(".title > .title"))));
						t = li.findElement(By.cssSelector(".title > .title")).getText();
						System.out.println("title=" + t);

						t = li.findElement(By.cssSelector(".title > .title")).getAttribute("href");
						System.out.println("detailed url=" + t);

						ww.until(ExpectedConditions.visibilityOf(li.findElement(By.className("color_red"))));
						t = li.findElement(By.className("color_red")).getText();
						System.out.println("price=" + t);
					} catch (StaleElementReferenceException ex) {
						// see http://docs.seleniumhq.org/exceptions/stale_element_reference.jsp
						kk--;
						System.out.println("stale element. retry to get it.");
					}
					// break;
				}

				WebElement ne = null;
				while (true) {
					try {
						ww.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#rankOpDiv")));
						// 如果找不到指定的element，selenium的做法是抛异常。
						// throw  NoSuchElementException
						ne = driver.findElement(By.cssSelector("#rankOpDiv .select_page_btn a.next")); 
						ww.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#rankOpDiv a.next")));
						ne.click();
					} catch (StaleElementReferenceException ex) {
						System.out.println("retry  going to next page.");
						continue;
					} catch (NoSuchElementException e) {
						System.out.println("this category end!");
					}
					break;
				}
				if (ne == null)
					break;
			}
			System.out.println("get " + products + " products.");
		} catch (Exception ex) {
			System.out.println(ex);
		}
		// Close the browser
		driver.quit();
	}

}
