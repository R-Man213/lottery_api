package com.mygroup.lottery_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LotteryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotteryApiApplication.class, args);
		LotteryService lotteryService = new LotteryService();
		lotteryService.getGames().stream().forEach((s) -> System.out.println(s));
	}

}
