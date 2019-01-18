package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import step_definitions.Base;

public class ZippopotamMainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css="a[href='#where']")
    private WebElement countries;

    @FindBy(id="what")
    private WebElement menuBody;

    @FindBy(id="where")
    private WebElement countriesBody;

    public ZippopotamMainPage(Base base){
        this.driver = base.getDriver();
        this.wait = base.getWebDriverWait();
        PageFactory.initElements(driver, this);
    }

    public void isHomepageDisplayed(){
        wait.until(ExpectedConditions.visibilityOf(menuBody));
    }

    public void isCountriesDisplayed(){
        wait.until(ExpectedConditions.visibilityOf(countriesBody));
    }

    public void clickCountries(){
        countries.click();
    }

    public void tearDown(){
        driver.quit();
    }

}
