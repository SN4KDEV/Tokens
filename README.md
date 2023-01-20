# Tokens Plugin

Plugin for custom economy or premium currency.


# Commands

**/tokens help** - display help menu
**/tokens** - display your amount of tokens
**/tokens get [player]** - get player token balance
**/tokens add [player] [amount]** - add tokens to a player
**/tokens remove [player] [amount]** - remove tokens toa player
**/tokens set [player] [amount]** - set tokens to a player

## API

Very simple :

```java
TokenAPI.getTokens(Player player)
TokenAPI.addTokens(Player player, int tokens)
TokenAPI.removeTokens(Player player, int tokens)
TokenAPI.setTokens(Player player, int tokens)
```

You can make a scoreboard and get players that have the most tokens.

exemple if i want to add a top 10 of players in a custom scoreboard:
```java
TokenAPI.getLeaderboard().entrySet().stream().limit(10).forEach(e -> {  
    scoreboard.newLine(e.getKey().getName() + " = " + e.getValue());  
});
```
Where `limit(10)` is my filter for sorting the best 10 players...
