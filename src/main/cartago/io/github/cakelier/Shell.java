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
        new Process(List.of("whoami"), l -> username.set(l.collect(Collectors.joining())));
    }

    @OPERATION
    public void traceroute(final String host) {
        new Process(
            List.of("traceroute", host),
            l -> l.skip(1)
                  .forEach(s -> TracerouteParser.parse(s).ifPresentOrElse(
                      h -> signal("hop", h.hopCount(), h.firstProbe(), h.secondProbe(), h.thirdProbe()),
                      () -> failed("Missing a hop in traceroute, skipping it...")
                  ))
        );
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
