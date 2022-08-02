# Lottery API
A Lottery API created using Spring Boot and Selenium

## Overview and Usage
This program first scrapes the active games section of the PA state lottery (https://www.palottery.state.pa.us/Scratch-Offs/Active-Games.aspx) using Selenium (Chrome browser is required). Each game is stored in a SQL database and is converted to a JSON response depending on the GET request.

## Current Requests
### All of these requests should be directed to localhost:8080
* /allGames
  * Type: GET
  * Usage: Returns all the active games
* /refresh
  * Type: PUT
  * Usage: Returns all the active games (requires web-scrape)
* /game
  * Type: GET
  * Parameter: price
  * Usage: Returns all the active games with a certain price
* /dateDesc
  * Type: GET
  * Usage: Returns all the active games ordered by descending dates
* /dateAsc
  * Type: GET
  * Parameter: price
  * Usage: Returns all the active games ordered by ascending dates

## Example JSON Response
{
        "gameId": 55,
        "gameName": "Bolt Bucks",
        "price": 3,
        "rewards": [
            50000,
            3000,
            1000,
            300,
            150,
            100
        ],
        "winsRemaining": [
            2,
            3,
            7,
            194,
            800,
            2861
        ],
        "startDate": "2021-04-17"
    }




