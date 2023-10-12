package io.github.cakelier;

import cartago.Tuple;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

/** Utility class for parsing a single "traceroute" command hop line. */
public class TracerouteParser {
    private static final Pattern INITIAL_COUNT_REGEX = Pattern.compile("^\\s*([0-9]+).+$");
    private static final Pattern TRACEROUTE_PROBE_REGEX =
        Pattern.compile("\\s+(?:(?:(\\S+)\\s+\\((\\S+)\\)\\s+)?(\\S+)\\s+ms|\\*)");

    /** Default constructor, not used. */
    private TracerouteParser() {}

    /** Parses the single "traceroute" command hop line which is given. If the parsing fails, an {@link Optional#empty()} is
     * returned, otherwise an {@link Optional} containing the parsed {@link TracerouteHop}.
     *
     * @param hop the hop line produced by the output of the "traceroute" command that needs to be parsed
     * @return an {@link Optional#empty()} if the parsing fails, an {@link Optional} containing the parsed {@link TracerouteHop}
     */
    public static Optional<TracerouteHop> parse(final String hop) {
        final var initialCountMatcher = INITIAL_COUNT_REGEX.matcher(hop);
         if (initialCountMatcher.matches()) {
             final var hopCount = Integer.parseInt(initialCountMatcher.group(1));
             final var probeMatcher = TRACEROUTE_PROBE_REGEX.matcher(hop);
             final var probes = new ArrayList<Tuple>();
             while (probeMatcher.find()) {
                 if (
                     probeMatcher.group(1) == null
                     && probeMatcher.group(2) == null
                     && probeMatcher.group(3) == null
                 ) {
                     probes.add(new Tuple("probe"));
                 } else if (
                     probeMatcher.group(1) == null
                     && probeMatcher.group(2) == null
                     && probeMatcher.group(3) != null
                 ) {
                     probes.add(new Tuple(
                         "probe",
                         Double.parseDouble(probeMatcher.group(3))
                     ));
                 } else if (
                     probeMatcher.group(1) != null
                     && probeMatcher.group(2) != null
                     && probeMatcher.group(3) != null
                 ) {
                     probes.add(new Tuple(
                         "probe",
                         probeMatcher.group(1),
                         probeMatcher.group(2),
                         Double.parseDouble(probeMatcher.group(3))
                     ));
                 }
             }
             if (probes.size() == 3) {
                 return Optional.of(new TracerouteHop(hopCount, probes.get(0), probes.get(1), probes.get(2)));
             } else {
                 return Optional.empty();
             }
         } else {
             return Optional.empty();
         }
    }

    /** The format for the output of a parsed "traceroute" command hop line. It contains the hop count for that specific hop and
     * the result of the first, second and third probe as a {@link Tuple}, to simplify the matching made by the "shell_user_agent"
     * agent.
     *
     * @param hopCount the count of the hop in the sequence
     * @param firstProbe the result of the first probe as a {@link Tuple}
     * @param secondProbe the result of the second probe as a {@link Tuple}
     * @param thirdProbe the result of the third probe as a {@link Tuple}
     */
    public record TracerouteHop(int hopCount, Tuple firstProbe, Tuple secondProbe, Tuple thirdProbe) {}
}
