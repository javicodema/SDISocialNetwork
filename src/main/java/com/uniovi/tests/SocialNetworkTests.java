package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SocialNetworkTests {
	static String PathFirefox = "C:\\Users\\David\\Downloads\\Firefox46.win\\FirefoxPortable.exe";
	// static String PathFirefox =
	// "C:\\Users\\Javier\\Downloads\\Firefox46.0.win\\Firefox46.win\\FirefoxPortable.exe";
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

	// Registro de Usuario con datos válidos.
	@Test
	public void PR01_1RegVal() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "NuevoUsuario@gmail.com", "Prueba", "Fernández", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_View.checkKey(driver, "homeP.message", PO_Properties.getSPANISH());
	}

	// Registro de Usuario con datos inválidos.
	@Test
	public void PR01_2RegInval() {
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

	// Inicio de sesión con datos válidos.
	@Test
	public void PR02_1InVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
	}

	// Inicio de sesión con datos inválidos (usuario no existente en la aplicación)
	@Test
	public void PR02_2InInVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "UsuarioNoExistente@gmail.com", "123456");
		PO_View.checkKey(driver, "errorLog.message", PO_Properties.getSPANISH());
	}

	// Acceso al listado de usuarios desde un usuario en sesión.
	@Test
	public void PR03_1LisUsrVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// Al logearte ya te lleva a la lista de usuarios asi que comprobamos que
		// estamos alli
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
	}

	// Intento de acceso con URL desde un usuario no identificado al listado de
	// usuarios desde un usuario en sesión. Debe producirse un acceso no permitido a
	// vistas privadas.

	@Test
	public void PR03_2LisUsrInVal() {
		// Intentamos entrar a la lista mediante URL
		driver.navigate().to(URL + "/user/list");
		// Comprobamos que no nos deja y estamos en el login
		PO_View.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}

	// Realizar una búsqueda valida en el listado de usuarios desde un usuario en
	// sesión
	@Test
	public void PR04_1BusUsrVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
		// Realizamos la búsqueda del usuario "Julio@gmail.com"
		driver.findElement(By.name("searchText")).sendKeys("Julio@gmail.com");
		driver.findElement(By.id("searchButton")).click();
		// Comprobamos que aparece correctamente
		PO_View.checkElement(driver, "text", "Julio@gmail.com");
	}

	// Intento de acceso con URL a la búsqueda de usuarios desde un usuario no
	// identificado. Debe producirse un acceso no permitido a vistas privadas.
	@Test
	public void PR04_2BusUsrInVal() {
		// Intentamos hacer una búsqueda mediante URL
		driver.navigate().to(URL + "/user/list?searchText=Pablo");
		// Comprobamos que no nos deja y estamos en el login
		PO_View.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}

	// Enviar una invitación de amistad a un usuario de forma valida.
	@Test
	public void PR05_1InvVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Dromir@gmail.com", "123456");
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
		// Agregamos al usuario de id 1 (Peter@gmail.com)
		PO_View.checkElement(driver, "id", "sendButton1").get(0).click();
	}

	// Enviar una invitación de amistad a un usuario al que ya le habíamos invitado
	// la invitación previamente. No debería dejarnos enviar la invitación, se
	// podría ocultar el botón de enviar invitación o notificar que ya había sido
	// enviada previamente.
	@Test
	public void PR05_2InvInVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
		// Comprobamos que no podemos agregar al usuario 2 (existe por defecto una
		// peticion) y que se muestra el botón de pendiente
		PO_View.checkKey(driver, "requestSent.message", PO_Properties.getSPANISH());
		PO_View.checkElement(driver, "id", "pendingButton2");
	}

	// Listar las invitaciones recibidas por un usuario, realizar la comprobación
	// con una lista que al menos tenga una invitación recibida.
	@Test
	public void PR06_1LisInvVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
		// Vamos a la lista de solicitudes de amigo
		driver.findElement(By.id("requests-menu")).click();
		driver.findElement(By.id("requestsList")).click();
		// Comprobamos que tenemos 3 peticiones (2 por defecto y la que enviamos antes)
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
	}

	// Aceptar una invitación recibida.
	@Test
	public void PR07_1AcepInvVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
		// Vamos a la lista de solicitudes de amigo
		driver.findElement(By.id("requests-menu")).click();
		driver.findElement(By.id("requestsList")).click();
		// Comprobamos que tenemos 3 peticiones (2 por defecto y la que enviamos antes)
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//td/button",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
		// Aceptamos la primera
		elementos.get(0).click();
	}

	// Listar los amigos de un usuario, realizar la comprobación con una lista que
	// al menos tenga un amigo.
	@Test
	public void PR08_1LisAmiVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
		// Vamos a la lista de amigos
		driver.findElement(By.id("requests-menu")).click();
		driver.findElement(By.id("friendsList")).click();
		// Comprobamos que tenemos 1 amigo (aceptado en el caso anterior)
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		// Vamos a la lista de solicitudes de amigo
		driver.findElement(By.id("requests-menu")).click();
		driver.findElement(By.id("requestsList")).click();
		// Comprobamos que tenemos 2 peticiones
		List<WebElement> elementos2 = SeleniumUtils.EsperaCargaPagina(driver, "free", "//td/button",
				PO_View.getTimeout());
		assertTrue(elementos2.size() == 2);
		// Aceptamos la primera
		elementos2.get(0).click();
		// Vamos a la lista de amigos
		driver.findElement(By.id("requests-menu")).click();
		driver.findElement(By.id("friendsList")).click();
		// Comprobamos que tenemos 2 amigos
		List<WebElement> elementos3 = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos3.size() == 2);
	}

	// Crear una publicación con datos válidos.
	@Test
	public void PR09_1PubVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
		// Vamos a añadir un post
		driver.findElement(By.id("posts-menu")).click();
		driver.findElement(By.id("addPost")).click();
		// Rellenamos la publicación
		PO_View.checkElement(driver, "id", "title").get(0).sendKeys("Test");
		PO_View.checkElement(driver, "id", "message").get(0).sendKeys("amo a testearno");
		// La enviamos
		PO_View.checkElement(driver, "id", "submitButton").get(0).click();
		// Comprobamos que estamos en la lista de posts
		PO_View.checkKey(driver, "postp1.message", PO_Properties.getSPANISH());
	}

	// Acceso al listado de publicaciones desde un usuario en sesión.
	@Test
	public void PR10_1LisPubVal() {
		// Vamos al formulario de log.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
		// Vamos a la lista de posts del usuario
		driver.findElement(By.id("posts-menu")).click();
		driver.findElement(By.id("listPost")).click();
		// Comprobamos que tenemos un post
		assertTrue(PO_View.checkElement(driver, "id", "post1").size() == 1);
	}

	// Listar las publicaciones de un usuario amigo
	@Test
	public void PR11_1LisPubAmiVal() {
	}

	// Utilizando un acceso vía URL tratar de listar las publicaciones de un usuario
	// que no sea amigo del usuario identificado en sesión.
	@Test
	public void PR11_2LisPubAmiInVal() {
	}

	// Crear una publicación con datos válidos y una foto adjunta.
	@Test
	public void PR12_1PubFot1Val() {
	}

	// Crear una publicación con datos válidos y sin una foto adjunta.
	@Test
	public void PR12_2PubFot2Val() {
	}

	// Inicio de sesión como administrador con datos válidos.
	@Test
	public void PR13_1AdInVal() {
		// accedemos a la url del login del admin
		driver.navigate().to("http://localhost:8090/admin/login");
		// rellenamos el formulario con datos de un admin
		PO_LoginView.fillFormAdmin(driver, "Dromir@gmail.com", "123456");
		// accedemos al listado
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
	}

	// Inicio de sesión como administrador con datos inválidos (usar los datos de un
	// usuario que no tenga perfil administrador).
	@Test
	public void PR13_2AdInInVal() {
		driver.navigate().to("http://localhost:8090/admin/login");
		// rellenamos el formulario con datos de un usuario normal y corriente
		PO_LoginView.fillFormAdmin(driver, "Peter@gmail.com", "123456");
		// leemos el mensaje de error en la pantalla
		PO_View.checkKey(driver, "errorAdmLog.message", PO_Properties.getSPANISH());
	}

	// Desde un usuario identificado en sesión como administrador listar a todos los
	// usuarios de la aplicación.
	@Test
	public void PR14_1AdLisUsrVal() {
		driver.navigate().to("http://localhost:8090/admin/login");
		PO_LoginView.fillFormAdmin(driver, "Dromir@gmail.com", "123456");
		// accedemos directamente al listado
		PO_View.checkKey(driver, "userp1.message", PO_Properties.getSPANISH());
	}

	// Desde un usuario identificado en sesión como administrador eliminar un
	// usuario existente en la aplicación.
	@Test
	public void PR15_1AdBorUsrVal() {
		driver.navigate().to("http://localhost:8090/admin/login");
		PO_LoginView.fillFormAdmin(driver, "Dromir@gmail.com", "123456");
		// comprobamos que existe el usuario Peter
		PO_View.checkElement(driver, "text", "Peter@gmail.com");
		// lo eliminamos
		PO_View.checkElement(driver, "id", "deleteButton1").get(0).click();
		// nos desconectamos e intentamos acceder como Peter
		PO_PrivateView.clickOption(driver, "/logout", "text", "Email");
		// driver.findElement(By.id("logout")).click(); no funcionaba por el timeout
		PO_LoginView.fillForm(driver, "Peter@gmail.com", "123456");
		// efectivamente no nos deja entrar ya que el usuario ya no existe
		PO_View.checkKey(driver, "errorLog.message", PO_Properties.getSPANISH());
	}

	// Intento de acceso vía URL al borrado de un usuario existente en la
	// aplicación. Debe utilizarse un usuario identificado en sesión pero que no
	// tenga perfil de administrador.
	@Test
	public void PR15_2AdBorUsrInVal() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "Javier@gmail.com", "123456");
		// intentamos eliminar a un usuario desde un acceso que no es de administrador
		driver.navigate().to("http://localhost:8090/admin/delete/2");
		// Comprobamos que nos salta el error de acceso denegado
		PO_View.checkElement(driver, "text", "Access is denied");
	}
}