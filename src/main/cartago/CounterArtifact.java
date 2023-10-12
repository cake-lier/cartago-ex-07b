import cartago.Artifact;
import cartago.OPERATION;

public class CounterArtifact extends Artifact {

    void init(final int count) {
        defineObsProperty("count", count);
    }

    @OPERATION
    void increment() {
        final var observableCount = getObsProperty("count");
        observableCount.updateValue(observableCount.intValue() + 1);
    }
}
