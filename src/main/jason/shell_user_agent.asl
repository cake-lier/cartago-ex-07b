!use_shell.

+!use_shell : true <-
    makeArtifact("shell", "io.github.cakelier.Shell", [], ShellId);
    focus(ShellId);
    print("Current user is: ");
    whoami(User);
    println(User, "\n\n");
    println("The route to the Google website is:\n");
    traceroute("www.google.com").

@plan1 [atomic]
+hop(N, Probe1, Probe2, Probe3) : true <-
    print(" ", N, " ");
    !print_probe(Probe1);
    print(" ");
    !print_probe(Probe2);
    print(" ");
    !print_probe(Probe3);
    println("").

+!print_probe(probe) : true <-
    print("*").

+!print_probe(probe(Time)) : true <-
    print(Time, " ms").

+!print_probe(probe(Address, Ip, Time)) : true <-
    print(Address, " (", Ip, ") ", Time, " ms").

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }