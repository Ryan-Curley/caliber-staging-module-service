package com.revature.backend.endtoend.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class HomePage {
	public final String url = "http://localhost:4200/home";
	
	@FindBy(xpath = "//*[@class='btn btn-primary logout-btn']")
	private WebElement logoutButton;
	
	@FindAll({
		@FindBy(xpath = "//button[text()='Create SWOT']"),
//		@FindBy(linkText = "Create SWOT")
	})
	private List<WebElement> createSwotButtons;
	
	@FindAll({
		@FindBy(xpath = "//button[text()='View SWOTs']")
	})
	private List<WebElement> viewSwotButtons;
	
	@FindBy(id = "type")
	private WebElement SwotTypeDropdown;
	
	@FindBy(id = "content")
	private WebElement contentField;
	
	@FindBy(xpath = "//button[text()='ADD ITEM']")
	private WebElement addItem;
	
	@FindBy(id = "description")
	private WebElement descriptionField;
	
	@FindBy(xpath = "//button[text()='SUBMIT']")
	private WebElement submitSwots;
	
	@FindBy(xpath = "//button[text()='View SWOTs']")
	private WebElement viewSwots;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void clickCreateSwotForAssociate(int rowId) {
		WebElement createButton = this.createSwotButtons.get(rowId);
		createButton.click();
	}
	
	public void clickViewSwotForAssociate(int rowId) {
		WebElement viewButton = this.viewSwotButtons.get(rowId);
		viewButton.click();
	}
	
	public void selectSwotType(String type) {
		Select swotOption = new Select(SwotTypeDropdown);
		swotOption.selectByValue(type);
	}
	
	public void enterContentField(String content) {
		this.contentField.clear();
		this.contentField.sendKeys(content);
	}
	
	public void enterDescriptionField(String description) {
		this.descriptionField.clear();
		this.descriptionField.sendKeys(description);
	}
	
	public void addSwotItem() {
		this.addItem.click();
	}
	
	public void submitSwotItems() {
		this.submitSwots.click();
	}
	
	public void clickViewSwots() {
		this.viewSwots.click();
	}
	
	
	
}