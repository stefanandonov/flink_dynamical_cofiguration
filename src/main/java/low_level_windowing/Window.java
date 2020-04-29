package low_level_windowing;



import high_level_windowing.IConfigurableWindow;

import java.util.Objects;

public class Window {

    private long start;
    private long end;

    public Window(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getMaxTimestamp() {
        return end - 1;
    }

    public boolean belongs(IConfigurableWindow element) {
        return element.getTimestamp() >= start && element.getTimestamp() < end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Window window = (Window) o;
        return start == window.start &&
                end == window.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
