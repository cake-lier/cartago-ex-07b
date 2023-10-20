# CArtAgO by exercises — Exercise 07b — Doing I/O with the Operating System - Shell artifact

In this exercise,
you'll implement an artifact that makes it possible for agents to execute shell commands and get their results.
The "Shell" artifact you need to implement provides the "whoami" and "traceroute" shell commands directly as operations.
The first command produces its output as action feedback, the second one as observable events.
More specifically, you need to implement the two operations providing these two commands,
noting that a helper "Process" class is given inside the artifact.
This class already handles everything about launching a command and handling its output;
you need to provide what to do with that output as a function.
Another thing to notice is the fact that a "TracerouteParser" helper class is provided,
which helps parse each single line produced as output by the "traceroute" command.
You need to pass it the string to parse, then the parsed output is returned,
from which you need to get the different values to build the signal to be sent to the agent waiting for it.

## Solution

All solution files are marked with the "solution" suffix, don't open them before solving the exercise!
