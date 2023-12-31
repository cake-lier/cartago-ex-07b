package io.github.cakelier;

import cartago.*;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Shell extends Artifact {
    @OPERATION
    public void whoami(final OpFeedbackParam<String> username) {
        /* Add your code here for launching the "whoami" command and collecting its output. */
    }

    @OPERATION
    public void traceroute(final String host) {
        /* Add your code here for launching the "traceroute" command and collecting its output. */
    }

    /** Helper class to be used for running a process on the current machine. */
    private class Process {

        /** Creates a new {@link Process} instance, given the sequence of strings that make up the command to launch and a
         * function to be executed on the lines of the output while they are produced as a {@link Stream} of strings.
         *
         * @param command the sequence of strings making up the command to launch
         * @param andThen the function to be called for manipulating the output of the command, seen as a {@link Stream} of strings
         */
        private Process(final List<String> command, final Consumer<Stream<String>> andThen) {
            try (final var reader = new ProcessBuilder().command(command).start().inputReader()) {
                andThen.accept(reader.lines());
            } catch (final IOException e) {
                Shell.this.failed(e.getMessage());
            }
        }
    }
}
