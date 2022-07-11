package com.mygroup.lottery_api.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LotteryGame {
    private long gameId;
    private String gameName;
    private int price;
    private List<Integer> rewards;
    private List<Integer> winsRemaining;
    private String startDate;

    public LotteryGame() {}

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Integer> getRewards() {
        return rewards;
    }

    public void setRewards(int reward) {
        if(rewards == null) {
            rewards = new ArrayList<>();
        }
        rewards.add(reward);
    }

    public List<Integer> getWinsRemaining() {
        return winsRemaining;
    }

    public void setWinsRemaining(int winRemaining) {
        if(winsRemaining == null) {
            winsRemaining = new ArrayList<>();
        }
        winsRemaining.add(winRemaining);
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "LotteryGame{" +
                ", gameName='" + gameName + '\'' +
                ", price=" + price +
                ", reward=" + rewards +
                ", winsRemaining=" + winsRemaining +
                ", startDate=" + startDate +
                '}';
    }
}


