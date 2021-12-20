// Based on the example code at http://www.seleniumhq.org/docs/03_webdriver.jsp
package edu.drexel.se320;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SeleniumTest {

    protected final String uiPath = "file:///Users/ibrahimkamal/Desktop/Fall%202020/SE320/Assignments/Assignment%205/hw5/web/index.html";

    @Test
    public void testPlusOnInitialScreen() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();
            WebElement input1 = driver.findElement(By.id("controls1plus"));
            String style = input1.getCssValue("display");
            assertEquals(style, "none");

            WebElement input2 = driver.findElement(By.id("controls1minus"));
            String style1 = input2.getCssValue("display");
            assertEquals(style1, "inline");

            WebElement input3 = driver.findElement(By.id("controls1"));
            String style2 = input3.getCssValue("display");
            assertEquals(style2, "block");

        } finally {
            driver.quit();
        }
    }

    @Test
    public void testMinusOnInitialScreen() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();
            WebElement elt1 = driver.findElement(By.id("controls1minus"));
            elt1.click();
            WebElement input1 = driver.findElement(By.id("controls1plus"));
            String style = input1.getCssValue("display");
            assertEquals(style, "inline");

            WebElement input2 = driver.findElement(By.id("controls1minus"));
            String style1 = input2.getCssValue("display");
            assertEquals(style1, "none");

            WebElement input3 = driver.findElement(By.id("controls1"));
            String style2 = input3.getCssValue("display");
            assertEquals(style2, "none");

        } finally {
            driver.quit();
        }
    }

    //test the addition of one element to the initial screen
    @Test
    public void testOneItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the form to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            /* The first element added to the list will have id "item1"
             * Subsequent list items will have IDs item2, item3, etc.
             * Arguably this is too brittle, but rather than forcing you
             * all to become experts on the DOM, you may assume this is done
             * correctly, and/or you're testing this functionality implicitly. */
            WebElement li = driver.findElement(By.id("item1"));
            // We use startsWith because getText includes the text of the Delete button
            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");
        } finally {
            driver.quit();
        }
    }

    //test whether we can delete the only item present in the list
    @Test
    public void testDeleteOneItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Something to do");

            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement li = driver.findElement(By.id("item1"));
            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");

            WebElement deleteButton = driver.findElement(By.id("button1"));
            deleteButton.click();

            List<WebElement> li2 = driver.findElements(By.id("item1"));
            assertEquals(li2.size(), 0);

        } finally {
            driver.quit();
        }
    }

    //testing what happens if we hit the - sign after adding one element and whether we can delete the item
    //after hitting the - sign
    @Test
    public void testHitMinus() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Something to do");

            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement li = driver.findElement(By.id("item1"));
            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");

            WebElement elt1 = driver.findElement(By.id("controls1minus"));
            elt1.click();

            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");

            WebElement input1 = driver.findElement(By.id("controls1plus"));
            String style = input1.getCssValue("display");
            assertEquals(style, "inline");

            WebElement input2 = driver.findElement(By.id("controls1minus"));
            String style1 = input2.getCssValue("display");
            assertEquals(style1, "none");

            WebElement input3 = driver.findElement(By.id("controls1"));
            String style2 = input3.getCssValue("display");
            assertEquals(style2, "none");

            WebElement deleteButton = driver.findElement(By.id("button1"));
            deleteButton.click();

            List<WebElement> li2 = driver.findElements(By.id("item1"));
            assertEquals(li2.size(), 0);

        } finally {
            driver.quit();
        }
    }

    //testing what happens if we hit the + sign after adding one element and hitting the - sign
    @Test
    public void testHitPlus() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Something to do");

            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement li = driver.findElement(By.id("item1"));
            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");

            WebElement elt1 = driver.findElement(By.id("controls1minus"));
            elt1.click();

            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");

            WebElement input1 = driver.findElement(By.id("controls1plus"));
            String style = input1.getCssValue("display");
            assertEquals(style, "inline");

            WebElement input2 = driver.findElement(By.id("controls1minus"));
            String style1 = input2.getCssValue("display");
            assertEquals(style1, "none");

            WebElement input3 = driver.findElement(By.id("controls1"));
            String style2 = input3.getCssValue("display");
            assertEquals(style2, "none");

            elt.click();

            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");

            input1 = driver.findElement(By.id("controls1plus"));
            style = input1.getCssValue("display");
            assertEquals(style, "none");

            input2 = driver.findElement(By.id("controls1minus"));
            style1 = input2.getCssValue("display");
            assertEquals(style1, "inline");

            input3 = driver.findElement(By.id("controls1"));
            style2 = input3.getCssValue("display");
            assertEquals(style2, "block");

        } finally {
            driver.quit();
        }
    }

    //testing the addition of 2 items
    @Test
    public void testTwoItems() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Item 1");
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement li = driver.findElement(By.id("item1"));

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 2");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement li1 = driver.findElement(By.id("item2"));

            assertTrue(li.getText().startsWith("Item 1"), "Checking correct text for added element");
            assertTrue(li1.getText().startsWith("Item 2"), "Checking correct text for added element");
        } finally {
            driver.quit();
        }
    }

    //testing what happens if we hit the - sign after adding two elements and whether we can delete the item
    //after hitting the - sign
    @Test
    public void testHitMinusAndDelete() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Item 1");
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            WebElement li = driver.findElement(By.id("item1"));

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 2");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            WebElement li1 = driver.findElement(By.id("item2"));

            assertTrue(li.getText().startsWith("Item 1"), "Checking correct text for added element");
            assertTrue(li1.getText().startsWith("Item 2"), "Checking correct text for added element");

            WebElement elt1 = driver.findElement(By.id("controls1minus"));
            elt1.click();

            assertTrue(li.getText().startsWith("Item 1"), "Checking correct text for added element");
            assertTrue(li1.getText().startsWith("Item 2"), "Checking correct text for added element");

            WebElement input1 = driver.findElement(By.id("controls1plus"));
            String style = input1.getCssValue("display");
            assertEquals(style, "inline");

            WebElement input2 = driver.findElement(By.id("controls1minus"));
            String style1 = input2.getCssValue("display");
            assertEquals(style1, "none");

            WebElement input3 = driver.findElement(By.id("controls1"));
            String style2 = input3.getCssValue("display");
            assertEquals(style2, "none");

            WebElement deleteButton = driver.findElement(By.id("button2"));
            deleteButton.click();

            List<WebElement> li2 = driver.findElements(By.id("item2"));

            assertTrue(li.getText().startsWith("Item 1"), "Checking correct text for added element");
            assertEquals(li2.size(), 0);

        } finally {
            driver.quit();
        }
    }

    //testing what happens if we hit the + sign after adding two items and hitting the - sign
    @Test
    public void testHitPlus1() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Item 1");
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            WebElement li = driver.findElement(By.id("item1"));

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 2");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            WebElement li1 = driver.findElement(By.id("item2"));

            assertTrue(li.getText().startsWith("Item 1"), "Checking correct text for added element");
            assertTrue(li1.getText().startsWith("Item 2"), "Checking correct text for added element");

            WebElement elt1 = driver.findElement(By.id("controls1minus"));
            elt1.click();

            elt.click();

            assertTrue(li.getText().startsWith("Item 1"), "Checking correct text for added element");
            assertTrue(li1.getText().startsWith("Item 2"), "Checking correct text for added element");

            WebElement input1 = driver.findElement(By.id("controls1plus"));
            String style = input1.getCssValue("display");
            assertEquals(style, "none");

            WebElement input2 = driver.findElement(By.id("controls1minus"));
            String style1 = input2.getCssValue("display");
            assertEquals(style1, "inline");

            WebElement input3 = driver.findElement(By.id("controls1"));
            String style2 = input3.getCssValue("display");
            assertEquals(style2, "block");

        } finally {
            driver.quit();
        }
    }

    //testing whether we can delete the second item and checking whether the other elements are in place or not
    @Test
    public void testDeleteSecondItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Item 1");
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            WebElement li = driver.findElement(By.id("item1"));

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 2");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement deleteButton = driver.findElement(By.id("button2"));
            deleteButton.click();

            List<WebElement> li2 = driver.findElements(By.id("item2"));

            assertTrue(li.getText().startsWith("Item 1"), "Checking correct text for added element");
            assertEquals(li2.size(), 0);

        } finally {
            driver.quit();
        }
    }

    //testing whether we can delete the second item and checking whether the other elements are in place or not
    @Test
    public void testDeleteFirstItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Item 1");
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 2");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement deleteButton = driver.findElement(By.id("button1"));
            deleteButton.click();

            List<WebElement> li2 = driver.findElements(By.id("item1"));
            assertEquals(li2.size(), 0);

            WebElement li = driver.findElement(By.id("item2"));
            assertTrue(li.getText().startsWith("Item 2"), "Checking correct text for added element");

        } finally {
            driver.quit();
        }
    }

    //testing the add functionality after deleting the first item
    @Test
    public void testAddItemAfterDeletingFirstItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Item 1");
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 2");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement deleteButton = driver.findElement(By.id("button1"));
            deleteButton.click();

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 3");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement li = driver.findElement(By.id("item2"));
            WebElement li1 = driver.findElement(By.id("item3"));

            assertTrue(li.getText().startsWith("Item 2"), "Checking correct text for added element");
            assertTrue(li1.getText().startsWith("Item 3"), "Checking correct text for added element");

        } finally {
            driver.quit();
        }
    }

    //testing the add functionality after deleting the second item
    @Test
    public void testAddItemAfterDeletingSecondItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Item 1");
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 2");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement deleteButton = driver.findElement(By.id("button2"));
            deleteButton.click();

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 3");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement li = driver.findElement(By.id("item1"));
            WebElement li1 = driver.findElement(By.id("item3"));

            assertTrue(li.getText().startsWith("Item 1"), "Checking correct text for added element");
            assertTrue(li1.getText().startsWith("Item 3"), "Checking correct text for added element");

        } finally {
            driver.quit();
        }
    }

    //test deleting both items
    @Test
    public void testDeleteFirstAndSecondItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            WebElement elt = driver.findElement(By.id("controls1plus"));
            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            input.sendKeys("Item 1");
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            input = driver.findElement(By.id("itemtoadd"));
            input.clear();
            input.sendKeys("Item 2");
            addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement deleteButton = driver.findElement(By.id("button1"));
            deleteButton.click();

            deleteButton = driver.findElement(By.id("button2"));
            deleteButton.click();

            List<WebElement> li2 = driver.findElements(By.id("item1"));
            assertEquals(li2.size(), 0);

            li2 = driver.findElements(By.id("item2"));
            assertEquals(li2.size(), 0);


        } finally {
            driver.quit();
        }
    }
}
