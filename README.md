# Sands Of Time's Piston Puzzle Solver
A java program that can solve MCC's Sands Of Time's Piston Puzzle.
What is the puzzle? Check out this [detailed document](https://docs.google.com/document/d/1ZbfKo57hn-H5eb_VkiYvJ5Ib-VdNfRtCjPyNy1HkyK8) made by edihau.

## How do I use this?
Clone this project into your IDE of choice (I use IntelliJ for this project), then do what you want with it.
Alternatively, you can download the .jar file in the releases page. Once downloaded:

 - Open your console on your OS, then change your directory to the folder containing the downloaded jar.
 - Run `java -jar PistonPuzzleSolver.jar (args)`
 - For the arguments, supply an integer seed (range from 0 to 2^23 - 1, or 8388607), or multiple of them. The program will go through all of the provided seeds and either solve them or provide an Exception and skip it.
 - Read below to see how seeds work.

**Common Exceptions:** 

 - java.lang.NumberFormatException: if you provided a seed that is not an integer. The code provides a way to use a 6-character hexadecimal seed for this, but it is not supported in the .jar file.
 - java.lang.IllegalArgumentException: if you provided a seed that is out of the range given above.
 - java.lang.RuntimeException: Should usually come with a message that said that you did not provide an argument. Happens when you run the jar file without any arguments.
## What does the program print out after solving the puzzle?
- If a valid solution is found, the program will print out an in depth step-by-step instruction on how to solve it, with what move to do, and what the board looks like after that move.
- If none is found, the program will print out the provided board, with the message "No solutions"
- The '@' represents the player, 'o' represents a box, 'x' represents an empty goal, or it will be '#' instead if something is occupying that spot. (Either a box, or the player, the latter means it is solved).
- Additionally, its runtime in miliseconds is also provided.
## How does the seed system work?
**WARNING: JANKY EXPLAINATIONS AND BROKEN ENGLISH DOWN BELOW**

The seed is a 24-bit unsigned integer (0 - 2^23 - 1). Out of the 24 bits, only 23 are used, the first (left-most) bit is ignored. The seed can be either stored as just an integer, or a 6-characters hexadecimal seed.
**For example:**
03BB34 in hex
244532 in decimal
In binary they should look like this

    0000 0011 1011 1011 0011 0100

Let's go through all of the bits one by one. Remember, the first bit is ignored.
0**00**0 0011 1011 1011 0011 0100 **Bit 2 & 3**
The 2nd and 3rd left-most bit are used to store the *rotation value* of the board. If the value of these 2 bits are both 0, then the position of the player is always inside the III quarter of the board (the bottom-left quarter). These 2 bits represent how many time the board should be rotated *counter-clockwise* to reach the position where the player is in that quarter.
When generating the board, the board will generate a board with the player in the bottom-left quarter, then rotate it clockwise that many times to get the board of that seed.

000**0 0**011 1011 1011 0011 0100 **Bit 4 & 5**
These two bits represent the position of the player. As the player is always  located in the III quarter when the board generated (prior to rotation), these 2 bits represent where in that quarter the player is. The top-left corner is 0, then to the right, then to the next row. Imagine it like this:
| 00 | 01 |
|----|----|
| **10** | **11** |

0000 0**011 1011 1011 0011** 0100 **Bit 6 - 20**
These 15 bits represent the remaining 15 slots on the board. A 0 represents that that slot is empty, while a 1 represents that a *box* is inside that slot.
Similar to the player, it starts at the top left corner of the **board**, going to the right of the row, then to the next row. It skips the slot the player is in.


0000 0011 1011 1011 0011 **0100** **Bit 21 - 24**
The last 4 bits are the location of the goal. It starts at the top left corner of the **board**, going to the right of the row, then to the next row, like the two above.

As an example, this is the board with the seed 100000. Try to convert the seed into the board.
| x | . | o | o |
|---|---|---|---|
| **.** | **.** | **.** | **.** |
| **@** | **o** | **o** | **.** |
| **o** | **.** | **o** | **.** |
## License
Read [LICENSE](https://github.com/LongCTygo/sot-piston-puzzle/blob/master/LICENSE).
