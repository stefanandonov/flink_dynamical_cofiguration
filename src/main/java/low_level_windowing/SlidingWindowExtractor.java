package low_level_windowing;

import high_level_windowing.IConfigurableWindow;

import java.util.ArrayList;
import java.util.Collection;

public class SlidingWindowExtractor<T extends IConfigurableWindow> extends WindowExtractor<T> {

    @Override
    public Collection<Window> getWindows(T element, long timestamp) {
        long windowSize = element.getWindowSize();
        long slide = element.getWindowSlide();
        Collection<Window> windows = new ArrayList<>();
        long start = ((timestamp + slide - windowSize) / slide) * slide;

        while (start <= timestamp) {
            windows.add(new Window(start, start + windowSize));
            start += slide;
        }
        return windows;
    }
}
