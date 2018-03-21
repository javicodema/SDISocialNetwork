package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {
	static public void fillForm(WebDriver driver, String user, String pswd) {
		WebElement username = driver.findElement(By.name("username"));
		username.click();
		username.clear();
		username.sendKeys(user);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(pswd);
		// Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
	
	static public void fillFormAdmin(WebDriver driver, String user, String pswd) {
		WebElement username = driver.findElement(By.id("email"));
		username.click();
		username.clear();
		username.sendKeys(user);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(pswd);
		// Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
	
}
