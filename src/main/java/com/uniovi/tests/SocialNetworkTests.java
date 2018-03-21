package com.uniovi.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SocialNetworkTests {
	static String PathFirefox = "C:\\Users\\david\\Downloads\\Firefox46.win\\FirefoxPortable.exe";
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	@BeforeClass
	static public void begin() {
	}

	@AfterClass
	static public void end() {
		driver.quit();
	}

	// 1.1. Prueba del formulario de registro. registro con datos correctos
	@Test
	public void PR1_1RegVal() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "NuevoUsuario@gmail.com", "Prueba", "Fernández", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_View.checkKey(driver, "homeP.message", PO_Properties.getSPANISH());
	}
	// 1.2. Prueba del formulario de registro. email repetido en la BD, Nombre
	// corto,
	// .... pagination pagination-centered,Error.signup.dni.length

	@Test
	public void PR1_2RegInval() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Peter@gmail.com", "Josefo", "Perez", "77777", "77777");
		PO_View.getP();
		// COmprobamos el error de username repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.username.duplicate", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Prueba@gmail.com", "Jose", "Perez", "77777", "77777");
		// COmprobamos el error de Nombre corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Prueba@gmail.com", "Josefo", "Per", "77777", "77777");
		// COmprobamos el error de apellido corto .
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Prueba@gmail.com", "Josefo", "Perez", "7", "7");
		// COmprobamos el error de contraseña corta .
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "Prueba@gmail.com", "Josefo", "Perez", "77777", "88888");
		// COmprobamos el error de que ambas contraseñas no coinciden .
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// PRN. Loguearse con exito con usuario, Peter@gmail.com, 123456
	@Test
	public void PR2_1InVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
	}

	// Identificación inválida con usuario no existente
	@Test
	public void PR2_2InInVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "UsuarioNoExistente@gmail.com", "123456");
		PO_View.checkKey(driver, "errorLog.message", PO_Properties.getSPANISH());
	}

	// Login y acceso a lista de usuarios
	@Test
	public void PR3_1LisUsrVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "UsuarioNoExistente@gmail.com", "123456");
		PO_View.checkKey(driver, "errorLog.message", PO_Properties.getSPANISH());
	}

	// Acceso a lista de usuarios mediante URL sin identificación
	@Test
	public void PR3_1LisUsrInVal() {

	}
}