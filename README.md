# BlackBox
A Java implementation of Eric Solomon's 2 player game
## How it's played (full detail)
BLACK BOX is a game for two players who take different roles. One player
(the 'setter') secretly sets up a 'molecule' of four 'atoms' by drawing circles in
cells on the paper scoring pad. Five atoms may be used to give a more demanding game.The other playerrepresents an 'experimenter' who triesto deduce the
positions of the atoms by sending 'rays' into the BLACK BOX and observing
how they are affected.
During play the experimenter tells his opponent where he issending a ray into
the box by announcing its numbered position at the edge of the BLACK BOX.
The setterthen secretly works out what happensto the ray, and ifit emergesfrom
the BLACK BOX he announces the number of the output position. The experimenter uses the cylindrical ray markers to record the result of each ray at the
edge ofthemain board. Hemay also place some or all of the red 'spherical' atoms
inside the BLACK BOX to help him test his hypotheses. His object isto deduce
the atompositions by use ofthe smallest number ofraymarkers. When the experimenter believes he has located all the atoms he first checks
that the atoms on the main board represent his conclusions, then announces the
end of the round. His score is the number of ray markers on the board plus 5
points for every misplaced atom. By reference to the setter's pad. the ray paths
should be checked, and for every error made in reporting the result of a ray the
experimenter's score is reduced by 5 points. At the end of the firstround the players exchange roles. The winneristhe player with the lower score obtained when he wasthe experimenter. Very accurate description can be found here [BlackBoxPlus-Rules.pdf](https://github.com/user-attachments/files/16716800/BlackBoxPlus-Rules.pdf)
## How it's played (long story short)
One player sets atoms and the other player guesses where they are, roles reverse and the player who makes the worse guesses loses. The guesses are made by shooting rays and getting hints from the setter based on the ray's VERY complex deflection. 
## Process of creating the game
Very rigorous process which involved learning and utilising Java's FX library. The most challenging part of creating the game was calculating the ray's deflection pattern
## GamePlay
![WhatsApp Image 2024-05-07 at 23 25 21_6d7cb928](https://github.com/user-attachments/assets/cb218430-b760-4c32-ab5b-b5f9616320a0)

![WhatsApp Image 2024-05-05 at 15 47 11_139a54cd](https://github.com/user-attachments/assets/322da3a2-099a-4827-85dd-b029f894664c)

![WhatsApp Image 2024-04-19 at 18 25 54_26d3de83](https://github.com/user-attachments/assets/c49c856d-af42-483a-84fe-c3efe0960e08)
![WhatsApp Image 2024-05-05 at 21 05 02_a4ff79a4](https://github.com/user-attachments/assets/40fb7144-6ddb-4f6d-8eea-30142d2f7c87)
