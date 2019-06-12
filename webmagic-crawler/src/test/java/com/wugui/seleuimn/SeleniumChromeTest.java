package com.wugui.seleuimn;

import com.wugui.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 使用selenium+ chrome
 *
 * 测试参考官方文档：https://sites.google.com/a/chromium.org/chromedriver/getting-started
 *
 * 安装步骤：
 * 1. 在mac中安装好谷歌浏览器
 * 2. 根据谷歌浏览器中的版本号下载chromedriver。不知道版本的可以选择帮助-关于google chrome查看。
 *      下载地址：https://sites.google.com/a/chromium.org/chromedriver/downloads
 * 3. java项目中引入maven依赖
 *      <dependency>
 * 			<groupId>org.seleniumhq.selenium</groupId>
 * 			<artifactId>selenium-java</artifactId>
 * 			<version>3.4.0</version>
 * 		</dependency>
 *
 * todo 学习使用stand
 **/
@ActiveProfiles({"pg"})// 选择激活的profile
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class SeleniumChromeTest {
    @Test
    public void testGoogleSearch() throws InterruptedException {
        // 指定你下载的chromedriver即可
        System.setProperty("webdriver.chrome.driver", "/Users/huzekang/opt/chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.com/xhtml");
        Thread.sleep(5000);  // Let the user actually see something!
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("ChromeDriver");
        searchBox.submit();
        Thread.sleep(5000);  // Let the user actually see something!
        driver.quit();
    }
}
