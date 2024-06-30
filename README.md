# BlackJack

This project is designed to calculate the best move in the game of blackjack and calculate the probability of winning.

## Best Move
Using this program, a table of the best moves was built.

| | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10, J, Q, K | A |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| 5-8 (Hard 5-8) | Hit | Hit | Hit | Hit | Hit | Hit | Hit | Hit | Hit | Hit |
| 9 (Hard 9) | Double | Double | Double | Double | Double | Double | Hit | Hit | Hit | Hit |
| 10 (Hard 10) | Double | Double | Double | Double | Double | Double | Double | Double | Hit | Hit |
| 11 (Hard 11) | Double | Double | Double | Double | Double | Double | Double | Double | Double | Hit |
| 12 (Hard 12) | Hit | Hit | Stand | Stand | Stand | Hit | Hit | Hit | Hit | Hit |
| 13-16 (Hard 13-16) | Stand | Stand | Stand | Stand | Stand | Hit | Hit | Hit | Hit | Hit |
| 17-20 (Hard 17-20) | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand |
| A, 2 (Soft 13) | Double | Double | Double | Double | Double | Double | Hit | Hit | Hit | Hit |
| A, 3 (Soft 14) | Double | Double | Double | Double | Double | Double | Hit | Hit | Hit | Hit |
| A, 4 (Soft 15) | Double | Double | Double | Double | Double | Double | Hit | Hit | Hit | Hit |
| A, 5 (Soft 16) | Double | Double | Double | Double | Double | Double | Hit | Hit | Hit | Hit |
| A, 6 (Soft 17) | Double | Double | Double | Double | Double | Double | Hit | Hit | Hit | Hit |
| A, 7 (Soft 18) | Stand | Double | Double | Double | Double | Stand | Stand | Stand | Stand | Stand |
| A, 8 (Soft 19) | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand |
| A, 9 (Soft 20) | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand |
| 2, 2 | Split | Split | Split | Split | Split | Split | Split | Hit | Hit | Hit |
| 3, 3 | Hit | Split | Split | Split | Split | Split | Hit | Hit | Hit | Hit |
| 4, 4 | Hit | Hit | Hit | Split | Split | Hit | Hit | Hit | Hit | Hit |
| 5, 5 | Double | Double | Double | Double | Double | Double | Double | Double | Hit | Hit |
| 6, 6 | Split | Split | Split | Split | Split | Hit | Hit | Hit | Hit | Hit |
| 7, 7 | Split | Split | Split | Split | Split | Split | Hit | Hit | Hit | Hit |
| 8, 8 | Split | Split | Split | Split | Split | Split | Split | Split | Hit | Hit |
| 9, 9 | Split | Split | Split | Split | Split | Stand | Split | Split | Stand | Stand |
| (10, J, Q, K), (10, J, Q, K) | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand | Stand |
| A, A | Split | Split | Split | Split | Split | Split | Split | Split | Split | Split |
