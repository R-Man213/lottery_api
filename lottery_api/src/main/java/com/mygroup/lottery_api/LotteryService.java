package com.mygroup.lottery_api;

import com.mygroup.lottery_api.models.LotteryGame;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LotteryService {

    private static boolean initialWebScrape = false;

    @Autowired
    private LotteryRepository lotteryRepository;

    public List<LotteryGame> getGames() {
        if (!initialWebScrape) {
            webScrape();
        }
        return lotteryRepository.findAll();
    }

    //Scrapes all active games from PA Lottery
    private void webScrape() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.palottery.state.pa.us/Scratch-Offs/Active-Games.aspx");
        String originalWindow = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //searches through the page and clicks on the active games section
        WebElement menu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("subshown")));
        String searchText = "Active Games";
        List<WebElement> menuOptions = menu.findElements(By.tagName("li"));
        for (WebElement option : menuOptions)
        {
            if (option.getText().equals(searchText))
            {
                option.click();
                break;
            }
        }

        List<WebElement> pageList;
        pageList = driver.findElements(By.className("pager"));

        //Loops through all the pages of the active games
        for(int i = 0; i < pageList.size(); i++) {
            WebElement nextButton = driver.findElement(By.className("next"));
            WebElement activeGamesTable = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("paginate")));
            List<WebElement> activeGames = activeGamesTable.findElements(By.tagName("a"));

            //Loops through list of active games on each page
            for(WebElement w : activeGames) {

                LotteryGame lotteryGame = new LotteryGame();
                lotteryGame.setGameName(w.findElement(By.className("info")).getText());
                lotteryGame.setPrice(Integer.parseInt(w.findElement(By.className("price"))
                        .getText().substring(1).trim()));

                String gameLink = w.getAttribute("href");

                driver.switchTo().newWindow(WindowType.TAB);
                driver.get(gameLink);

                WebElement prizesRemainingTable = driver.findElement(By.tagName("tbody"));
                List<WebElement> prizesRemaining = prizesRemainingTable.findElements(By.tagName("tr"));

                //get all prize info
                for(WebElement tableEntry : prizesRemaining) {
                    try {
                        lotteryGame.setRewards(Integer.parseInt(tableEntry.findElements
                                (By.tagName("td")).get(0).getText().replaceAll("[^\\d]", "")));
                    } catch(Exception e) {
                        System.out.println("No Prize");
                    }
                    try {
                        lotteryGame.setWinsRemaining(Integer.parseInt(tableEntry.findElements
                                (By.tagName("td")).get(1).getText().replaceAll("[^\\d]", "")));
                    } catch(Exception e){
                        System.out.println("No Wins Remaining");
                    }

                }

                //get date info
                String docURL = driver.findElement(By.xpath("//a[text()='Complete Game Rules']")).getAttribute("href");
                driver.get(docURL);
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
                String value = (String)javascriptExecutor.executeScript("return document.evaluate(\"/html/body/div[2]/div/div[2]/div[1]/div[4]/div/div/blockquote/h4/center\", document, null, XPathResult.STRING_TYPE, null ).stringValue;");
                lotteryGame.setStartDate(extractDate(value));

                //games.add(lotteryGame);
                lotteryRepository.save(lotteryGame);
                driver.close();
                driver.switchTo().window(originalWindow);
            }

            nextButton.click();
        }
        initialWebScrape = true;
        driver.quit();
    }



    //Takes the web-scraped date info and converts it into YYYY-MM-DD format
    private String extractDate(String bulletinInfo) {

        StringBuilder dateInTextForm = new StringBuilder();

        int bracketCount = 0;
        for(int i = 0; i < bulletinInfo.length(); i++) {
            if(bulletinInfo.charAt(i) == '[') {
                bracketCount++;
            }

            if (bracketCount > 1) {
                dateInTextForm.append(bulletinInfo.charAt(i));
            }
        }
        dateInTextForm.deleteCharAt(dateInTextForm.length()-1);
        dateInTextForm.deleteCharAt(0);

        LocalDate localDate = LocalDate.parse(dateInTextForm.toString(), DateTimeFormatter.ofPattern("EEEE, MMMM d, uuuu"));
        return localDate.toString();

    }
}