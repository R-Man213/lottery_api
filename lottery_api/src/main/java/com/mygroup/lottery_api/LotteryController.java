package com.mygroup.lottery_api;

import com.mygroup.lottery_api.models.LotteryGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;


    @GetMapping("/games")
    public List<LotteryGame> getAllGames() {

        return lotteryService.getGames();
    }

}
