# SmallEconomy
A small light economy plugin

## Structure
```
Bank -> User -> Main Account (With the name of the player)

             -> Sub Account (A name set by the player)
             
     -> User
     
             ->...
             
     -> User
     
             ->...
```

## Features

```
/acc create
```
Creates a main account for a player.

```
/acc create [NAME]
```
Creates a sub account with the name '[NAME]'.

```
/acc del [NAME]
```
Deletes an account with the name '[NAME]'.

```
/acc share [NAME] [PLAYER_NAME]
```

Shares the account '[NAME]' with player '[PLAYER_NAME]'

```
/acc bal
```

Returns the balance of the main account.

```
/acc bal [NAME]
```

Returns the balance of the account with the name '[NAME]'




