# Minesweeper
Minesweeper that always have a solution

# Installation

Just download the "exec" folder and run the .jar file.

# Background

Uploaded small project from about 2010 (not touched since).

I was playing minesweeper back then and was annoyed that in a lot of situation I got into unresolvable solution.

Therefore I have created minesweeper that always have a solution (and also "help" button that shows you which fields based on what you see must have mines and which not).

The solution is semi-optimal, it guarantees the solution always exist, but to ensure it, it can show more fields than necessary required.

The alghoritm has 2 phases, first phase runs in linear time and finds out the obvious solutions. If some solution is found in first phase, the second phase is not executed.

The second phase has 2^n complexity. To compensate it, if possible, it splits the board into several smaller components and resolves independetly each.
