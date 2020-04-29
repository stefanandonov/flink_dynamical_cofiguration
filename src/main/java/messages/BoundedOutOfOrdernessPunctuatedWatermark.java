package messages;


import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * This is a {@link AssignerWithPeriodicWatermarks} used to emit Watermarks that lag behind the element with
 * the maximum timestamp (in event time) seen so far by a fixed amount of time, <code>t_late</code>. This can
 * help reduce the number of elements that are ignored due to lateness when computing the final result for a
 * given window, in the case where we know that elements arrive no later than <code>t_late</code> units of time
 * after the watermark that signals that the system event-time has advanced past their (event-time) timestamp.
 * */
public abstract class BoundedOutOfOrdernessPunctuatedWatermark<T> implements AssignerWithPunctuatedWatermarks<T> {
    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(T lastElement, long extractedTimestamp) {
        return new Watermark(extractedTimestamp - maxOutOfOrderness);
    }

    public abstract long extractTimestamp(T element);

    @Override
    public final long extractTimestamp(T element, long previousElementTimestamp) {
        long timestamp = extractTimestamp(element);
        if (timestamp > currentMaxTimestamp) {
            currentMaxTimestamp = timestamp;
        }
        return timestamp;
    }

    private static final long serialVersionUID = 1L;

    /** The current maximum timestamp seen so far. */
    private long currentMaxTimestamp;

    /** The timestamp of the last emitted watermark. */
    private long lastEmittedWatermark = Long.MIN_VALUE;

    /**
     * The (fixed) interval between the maximum seen timestamp seen in the records
     * and that of the watermark to be emitted.
     */
    private final long maxOutOfOrderness;

    public BoundedOutOfOrdernessPunctuatedWatermark(long maxOutOfOrderness) {
        if (maxOutOfOrderness < 0) {
            throw new RuntimeException("Tried to set the maximum allowed " +
                    "lateness to " + maxOutOfOrderness + ". This parameter cannot be negative.");
        }
        this.maxOutOfOrderness = maxOutOfOrderness;
        this.currentMaxTimestamp = Long.MIN_VALUE + this.maxOutOfOrderness;
    }

    public long getMaxOutOfOrderness() {
        return maxOutOfOrderness;
    }
}
