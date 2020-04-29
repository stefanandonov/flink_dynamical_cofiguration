package low_level_windowing;

import high_level_windowing.IConfigurableWindow;

abstract public  class WindowExtractor<T extends IConfigurableWindow>
        implements IWindowExtractor<T> {

    public static <T extends IConfigurableWindow> WindowExtractor<T> of (String windowType) {
        if (windowType.equals("sliding"))
            return new SlidingWindowExtractor<>();
        else
            return new TumblingWindowExtractor<>();
    }
}
