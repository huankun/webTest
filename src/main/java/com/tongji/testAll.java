package com.tongji;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class testAll{

    private static WebDriver driver = null;
    String projectName="project"+(new Random().nextInt(10000));
//    @BeforeClass
    public void beforeClass()throws InterruptedException{
//          没有打包时的目录
//        System.setProperty("webdriver.gecko.driver","src/main/resources/geckodriver.exe");
        System.setProperty("webdriver.gecko.driver","classes/geckodriver.exe");
        driver=new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

//    @Test()
    public void testAll() throws Exception{
        beforeClass();
        String url="http://localhost:8083/testlink/login.php";
        driver.get(url);
        Assert.assertTrue(driver.getTitle().indexOf("TestLink") > -1);

    }

//    @Test(dependsOnMethods="testAll")
    public void testCreateProject()throws Exception{
        testAll();
        driver.findElement(By.id("login")).clear();
        driver.findElement(By.id("login")).sendKeys("admin");
        driver.findElement(By.name("tl_password")).clear();
        driver.findElement(By.name("tl_password")).sendKeys("admin");
        driver.findElement(By.name("login_submit")).click();
        Thread.sleep(2000);
//        http://localhost/testlink/lib/project/projectview.php
        driver.get("http://localhost:8083/testlink/lib/project/projectview.php");
        Thread.sleep(1000);
//        driver.findElement(By.xpath("//a[@href=['lib/project/projectView.php']")).click();
        driver.findElement(By.id("create")).click();
        Thread.sleep(2000);
//        假设没有任意项目，则需要进入frame
//        driver.switchTo().frame("mainframe");
        driver.findElement(By.name("tprojectName")).clear();
//        String projectName="project"+(new Random().nextInt(10000));
        driver.findElement(By.name("tprojectName")).sendKeys(projectName);
        driver.findElement(By.name("tcasePrefix")).clear();
        driver.findElement(By.name("tcasePrefix")).sendKeys(projectName);
        driver.findElement(By.name("doActionButton")).click();
        Thread.sleep(1000);

    }

//    @Test(dependsOnMethods={"testAll","testCreateProject"})
    public void testDeleteProject()  throws Exception{
        testCreateProject();
        driver.get("http://localhost:8083/testlink/lib/project/projectView.php");
        Thread.sleep(1000);
        System.out.println(projectName);
        String xpathDeleteButton ="(//tr/td[8])[last()]";
        driver.findElement(By.xpath(xpathDeleteButton)).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ext-gen20")).click();

    }

    public static void main(String[] args) throws Exception{
        new testAll().testDeleteProject();
    }
}

