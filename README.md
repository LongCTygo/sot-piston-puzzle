# THIS PROJECT IS DEPRECATED. PLEASE REFER TO [HERE](https://github.com/LongCTygo/sot-piston-puzzle-gui) FOR THE LATEST VERSION
# Sands Of Time's Piston Puzzle Solver
A java program that can solve MCC's Sands Of Time's Piston Puzzle.
What is the puzzle? Check out this [detailed document](https://docs.google.com/document/d/1ZbfKo57hn-H5eb_VkiYvJ5Ib-VdNfRtCjPyNy1HkyK8) made by edihau.

## How do I use this?
Clone this project into your IDE of choice (I use IntelliJ for this project), then do what you want with it.
Alternatively, you can download the .jar file in the releases page. Once downloaded:

- Open your console on your OS, then change your directory to the folder containing the downloaded jar.
- Run `java -jar PistonPuzzleSolver.jar (args)`
- For the arguments, supply an integer seed (range from 0 to 2^23 - 1, or 8388607), or multiple of them. The program will go through all the provided seeds and either solve them or provide an Exception and skip it.
- Read below to see how seeds work.
- Alternatively, have your first argument be '-loop', and the program will instead wait for you to supply the seeds whilst running. It will end once you give it an empty input.
 - Open your console on your OS, then change your directory to the folder containing the downloaded jar.
 - Run `java -jar PistonPuzzleSolver.jar (args)`
 - For the arguments, supply an integer seed (range from 0 to 2^23 - 1, or 8388607), or multiple of them. The program will go through all of the provided seeds and either solve them or provide an Exception and skip it.
 - Read below to see how seeds work.
 - Alternatively, have your first argument be '-loop', and the program will instead wait for you to supply the seeds whilst running. It will end once you give it an empty input.

**Common Exceptions:**

- java.lang.NumberFormatException: if you provided a seed that is not an integer.
- java.lang.IllegalArgumentException: if you provided a seed that is out of the range given above. This should not happen in the latest release, as the program now accept full 32-bit integer as a valid seed. The program will still only use the 23 bits it needs.
- java.lang.RuntimeException: Should usually come with a message that said that you did not provide an argument. Happens when you run the jar file without any arguments.
## What does the program print out after solving the puzzle?
- If a valid solution is found, the program will print out an in depth step-by-step instruction on how to solve it, with what move to do, and what the board looks like after that move.
- If none is found, the program will print out the provided board, with the message "No solutions"
- The '@' represents the player, 'o' represents a box, 'x' represents an empty goal, or it will be '#' instead if something is occupying that spot. (Either a box, or the player, the latter means it is solved).
- Additionally, its runtime in milliseconds is also provided.
## How does the seed system work?
#### **WARNING: INCOMPLETE EXPLANATIONS AND BROKEN ENGLISH DOWN BELOW**

The seed is a 24-bit unsigned integer (0 - 2^23 - 1). Out of the 24 bits, only 23 are used, the first (left-most) bit is ignored. The seed can be either stored as just an integer, or a 6-characters hexadecimal seed.
**For example:**

The seed 244532 in binary is:

    0000 0011 1011 1011 0011 0100

Let's go through all the bits one by one. Remember, the first bit is ignored.

0**00**0 0011 1011 1011 0011 0100 **Bit 2 & 3**

The 2nd and 3rd left-most bit are used to store the *rotation value* of the board. If the value of these 2 bits are both 0, then the position of the player is always inside the III quarter of the board (the bottom-left quarter). These 2 bits represent how many times the board should be rotated *counter-clockwise* to reach the position where the player is in that quarter.
When generating the board, the board will generate a board with the player in the bottom-left quarter, then rotate it clockwise that many times to get the board of that seed.

000**0 0**011 1011 1011 0011 0100 **Bit 4 & 5**

These two bits represent the position of the player. As the player is always  located in the III quarter when the board generated (prior to rotation), these 2 bits represent where in that quarter the player is. The top-left corner is 0, then to the right, then to the next row. Imagine it like this:
| 00 | 01 |
|----|----|
| **10** | **11** |

0000 0**011 1011 1011 0011** 0100 **Bit 6 - 20**

These 15 bits represent the remaining 15 slots on the board. A 0 represents that that slot is empty, while a 1 represents that a *box* is inside that slot.
Similar to the player, it starts in the top left corner of the **board**, going to the right of the row, then to the next row. It skips the slot the player is in.


0000 0011 1011 1011 0011 **0100** **Bit 21 - 24**

The last 4 bits are the location of the goal. It starts in the top left corner of the **board**, going to the right of the row, then to the next row, like the two above.

As an example, this is the board with the seed 100000. Try to convert the seed into a board.
| x | . | o | o |
|---|---|---|---|
| **.** | **.** | **.** | **.** |
| **@** | **o** | **o** | **.** |
| **o** | **.** | **o** | **.** |
## The Algorithm (WIP)
The algorithm aims to solve the puzzle with a breadth-first search approach. Starting from the root, it generates all possible board states, then decide whether the board is 'on the right track' and add it to the queue, or else it will ignore that board.

From a board, it will try out all 16 different moves, ignoring moves that does not advance the board state. From there, it determines if a move is advantageous or not, and continue from there.
## Additional Info (WIP)
## To-do List (WIP)
- Make a mod that does all of these. It can go with a map for practicing purposes. Ideally just a map, but I don't think mcfunctions alone can achieve randomly generated levels (that are solvable, at least). One way to achieve this is likely to load all levels template into that map, then use /clone and some random scoreboard functions to pick one, but there are A LOT of puzzles, and I'm not sure how viable that is.
- Alternatively, a web-based game/app for practicing could be achieved.
- A simple board-to-seed converter GUI for easier use.
- Improving the algorithms. And there are a lot to be improved.
## Note
This program is designed as a way to study and do research. Through the use of this, you can learn how to tackle a puzzle like this as a human, find potential strategy and common patterns. This is **NOT** a program made to be used as a mean of cheating inside an environment like the Minecraft Championship, or to be expanded into programs that enable such acts. Please do not use this for those purposes.

I am not a member of the Noxcrew, nor am I affiliated to the Noxcrew or the Minecraft Championship (MCC) in any way. This program is a fan-project.
## License
Read [LICENSE](https://github.com/LongCTygo/sot-piston-puzzle/blob/master/LICENSE).
