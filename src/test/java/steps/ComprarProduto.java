package steps;

import io.cucumber.java.pt.Entao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;

public class ComprarProduto {

    private AndroidDriver driver;

    private URL getUrl() {
        try {
            return new URL(
                    "https://oauth-bah.rodrigues222-f4867:5c2bdc74-0354-45fb-8c72-8f26fa6b93c8@ondemand.us-west-1.saucelabs.com:443/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void arrastaParaCima(Integer xInicio, Integer yInicio, Integer xFim, Integer yFim ){

        final var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        var start = new Point(xInicio, yInicio);
        var end = new Point (xFim, yFim);
        var swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
            PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
            PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    @Before
    public void iniciar() {
        var options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:platformVersion", "9.0")
                .amend("appium:deviceName", "Samsung Galaxy S9 FHD GoogleAPI Emulator")
                .amend("appium:deviceOrientation", "portrait")
                .amend("appium:app", "storage:filename=mda-2.2.0-25.apk")
                .amend("appium:appPackage", "com.saucelabs.mydemoapp.android")
                .amend("appium:appActivity", "com.saucelabs.mydemoapp.android.view.activities.SplashActivity")
                .amend("appium:automationName", "UiAutomator2")
                .amend("browserName", "")
                .amend("appium:ensureWebviewsHavePages", true)
                .amend("appium:nativeWebScreenshot", true)
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);

        driver = new AndroidDriver(this.getUrl(), options);
    }

    @After
    public void finalizar() throws InterruptedException {
        driver.quit();
       // Thread.sleep(20000);
    }

    @Dado("que acesso o My Demo App")
    public void que_acesso_o_my_demo_app() {
        // o aplicativo foi aberto no final do metodo Before
    }

    @Entao("verifico o logo e nome da secao")
    public void verifico_o_logo_e_nome_da_secao() {
        // logo: com.saucelabs.mydemoapp.android:id/mTvTitle
        // secao: com.saucelabs.mydemoapp.android:id/productTV
        var imgLogo = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/mTvTitle"));
        assertEquals(imgLogo.isDisplayed(), true);

        var lblTituloSecao = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"));
        assertEquals("Products", lblTituloSecao.getText());
    }

    @E("localizo o {string} que na {int} esta na posicao {int} por {string}")
    public void localizo_o_produto_que_esta_por_preco(String produto, Integer rolagem, Integer num_produto, String preco) {
        // produto home: //android.widget.TextView[@content-desc="Product Title" and @text="Sauce Labs Backpack"]
        // preco home: //android.widget.TextView[@content-desc="Product Price"])[1]

        for (int i = 0; i < rolagem; i++) {
            arrastaParaCima(525, 1698, 530, 563);

        }

        assertEquals(produto,driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Product Title\" and @text=\"" + produto + "\"]")).getText());
        assertEquals(preco,driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@content-desc=\"Product Price\"])[" + num_produto + "]")).getText());
    }

    @Quando("clico na imagem do {int}")
    public void clico_na_imagem_do_num_produto(Integer num_produto) {
        driver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[" + num_produto + "]")).click();
    }

    @Entao("na tela do produto visualizo o {string} e o {string}")
    public void na_tela_do_produto_visualizo_o_produto_e_o_preco(String produto, String preco) {
        // tela de detalhes do produto escolhido
        assertEquals(produto,driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV")).getText());
        assertEquals(preco, driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV")).getText());
    }

    @Quando("arrasto para cima e clico Add Cart")
    public void arrasto_para_cima_e_clico_add_cart() {
        //arrasta para cima
        arrastaParaCima(525, 1698, 530, 563);
 
        //clicar no botao adicionar no carrinho
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt")).click();

        //verificar se o numero de produtos no carrinho esta para 1
        assertEquals("1", driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV")).getText());

        //ir para o carrinho de compras
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartIV")).click();
    }

    @Entao("na tela do carrinho visualizo o {string} {string} e {int}")
    public void na_tela_do_carrinho_visualizo_o_produto_preco_e_quantidade(String produto, String preco,
            Integer quantidade) throws InterruptedException {
        // produto carrinho: com.saucelabs.mydemoapp.android:id/titleTV
        // preco carrinho: com.saucelabs.mydemoapp.android:id/priceTV
        // quantidade carrinho: com.saucelabs.mydemoapp.android:id/itemsTV
        Thread.sleep(3000); //pausa forcada
        assertEquals("My Cart", driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV")).getText());
        assertEquals(produto, driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/titleTV")).getText());
        assertEquals(preco, driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV")).getText());
        assertEquals(quantidade + " Items", driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/itemsTV")).getText());
        //preco total
        assertEquals(preco, driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/totalPriceTV")).getText());

    }

}
