package com.mygroup.lottery_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/allGames")
    ResponseEntity<?> getAllGames() {
        HttpHeaders header = new HttpHeaders();
        if(lotteryService.getRepoStatus() == RepoStatus.IN_USE) {
            header.set("scraping-status", "Processing");
           return ResponseEntity.accepted().headers(header).body("Processing");
        }

        header.set("scraping-status", "Finished");
        return ResponseEntity.ok().headers(header).body(lotteryService.getGames());
    }

    @PutMapping("/refresh")
    ResponseEntity<?> refresh() {
        HttpHeaders header = new HttpHeaders();
        if(lotteryService.getRepoStatus() == RepoStatus.IN_USE) {
            header.set("scraping-status", "Processing");
            return ResponseEntity.accepted().headers(header).body("Processing");
        }

        header.set("scraping-status", "Finished");
        return ResponseEntity.ok().headers(header).body(lotteryService.refreshGames());
    }

    @GetMapping("/game")
    ResponseEntity<?> getByPrice(@RequestParam int price) {
        HttpHeaders header = new HttpHeaders();
        if(lotteryService.getRepoStatus() == RepoStatus.IN_USE) {
            header.set("scraping-status", "Processing");
            return ResponseEntity.accepted().headers(header).body("Processing");
        }

        header.set("scraping-status", "Finished");
        return ResponseEntity.ok().headers(header).body(lotteryService.getByPrice(price));
    }

    @GetMapping("/dateDesc")
    ResponseEntity<?> getByDateDesc() {
        HttpHeaders header = new HttpHeaders();
        if(lotteryService.getRepoStatus() == RepoStatus.IN_USE) {
            header.set("scraping-status", "Processing");
            return ResponseEntity.accepted().headers(header).body("Processing");
        }

        header.set("scraping-status", "Finished");
        return ResponseEntity.ok().headers(header).body(lotteryService.getByDateDesc());
    }

    @GetMapping("/dateAsc")
    ResponseEntity<?> getByDateAsc() {
        HttpHeaders header = new HttpHeaders();
        if(lotteryService.getRepoStatus() == RepoStatus.IN_USE) {
            header.set("scraping-status", "Processing");
            return ResponseEntity.accepted().headers(header).body("Processing");
        }

        header.set("scraping-status", "Finished");
        return ResponseEntity.ok().headers(header).body(lotteryService.getByDateAsc());
    }

}
