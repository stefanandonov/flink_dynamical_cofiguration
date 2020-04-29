package low_level_windowing;


import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class WindowManager {

    ValueState<TreeSet<Window>> windows;

    public WindowManager(RuntimeContext runtimeContext) {
        ValueStateDescriptor<TreeSet<Window>> windowsStateDescriptor = new ValueStateDescriptor<TreeSet<Window>>(
                "windows-descriptor",
                TypeInformation.of(new TypeHint<TreeSet<Window>>() {
                })
        );

        windows = runtimeContext.getState(windowsStateDescriptor);
    }

    public void add(Window window) throws IOException {
        TreeSet<Window> windowSet = windows.value();

        if (windowSet == null) {
            windowSet = new TreeSet<>(Comparator.comparing(Window::getMaxTimestamp));
        }

        windowSet.add(window);

        windows.update(windowSet);
    }

    public List<Window> windowsToFire(long currentTime) throws IOException {

        TreeSet<Window> windowSet = windows.value();

        if (windowSet==null)
            return Collections.emptyList();

        List<Window> selectedWindows = windowSet.stream()
                .filter(w -> w.getMaxTimestamp() < currentTime)
                .collect(Collectors.toList());

        selectedWindows.forEach(windowSet::remove);

        return selectedWindows;
    }
}
