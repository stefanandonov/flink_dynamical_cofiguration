package high_level_windowing;

import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TimeWindowCollectionFactory {

    private static final String TUMBLING = "tumbling";
    private static final String SLIDING = "sliding";

    public static <T extends IConfigurableWindow> Collection<TimeWindow> create(T element, long timestamp, String windowType) {
        long windowSize = element.getWindowSize();
        long start;
        switch (windowType) {
            case TUMBLING:
                start = (timestamp / windowSize) * windowSize;
                long end = start + windowSize;
                TimeWindow timeWindow =  new TimeWindow(start, end);
                return Collections.singletonList(timeWindow);
            case SLIDING:
                long slide = element.getWindowSlide();
                List<TimeWindow> windows = new ArrayList<>();
                start = ((timestamp + slide - windowSize) / slide) * slide;
                while (start <= timestamp) {
                    windows.add(new TimeWindow(start, start + windowSize));
                    start += slide;
                }
                return windows;
            default:
                return Collections.emptyList();
        }
    }
}
