package com.mygroup.lottery_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/games")
    ResponseEntity<?> getAllGames() {
        HttpHeaders header = new HttpHeaders();
        if(lotteryService.getRepoStatus() == RepoStatus.IN_USE) {
            header.set("scraping-status", "Processing");
           return ResponseEntity.accepted().headers(header).body("Processing");
        }

        header.set("scraping-status", "Finished");
        return ResponseEntity.ok().headers(header).body(lotteryService.getGames());

    }

}
