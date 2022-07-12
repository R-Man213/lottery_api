package com.mygroup.lottery_api;

import com.mygroup.lottery_api.models.LotteryGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotteryRepository extends JpaRepository<LotteryGame, Long> {

}
