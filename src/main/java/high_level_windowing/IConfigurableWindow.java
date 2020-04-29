package high_level_windowing;

public interface IConfigurableWindow<T> {
    T getElement();
    Long getWindowSize();
    String getWindowType();
    Long getWindowSlide();
    long getTimestamp();
    void setTimestamp(long timestamp);
}
