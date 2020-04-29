package low_level_windowing;

import high_level_windowing.IConfigurableWindow;

import java.util.Collection;
import java.util.Collections;

public class TumblingWindowExtractor <T extends IConfigurableWindow> extends WindowExtractor<T> {

    @Override
    public Collection<Window> getWindows(T element, long timestamp) {
        long windowSize = element.getWindowSize();
        long start = (timestamp / windowSize) * windowSize;
        long end = start + windowSize;

        return Collections.singletonList(new Window(start, end));
    }
}
