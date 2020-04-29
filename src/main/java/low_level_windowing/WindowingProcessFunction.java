package low_level_windowing;


import high_level_windowing.IConfigurableWindow;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class WindowingProcessFunction
        <IN extends IConfigurableWindow<E>, E>
        extends KeyedProcessFunction<String, IN, Collection<IN>> {

    WindowManager windowManager;
    DataManager<IN> dataManager;
    boolean eventTime;

    public WindowingProcessFunction(boolean eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        windowManager = new WindowManager(getRuntimeContext());
        dataManager = new DataManager<>(10000, getRuntimeContext());

    }

    @Override
    public void processElement(IN element, Context context, Collector<Collection<IN>> collector) throws Exception {


        String windowType = element.getWindowType();

        IFlinkTimeExtractor<IConfigurableWindow> timeExtractor = FlinkTimeExtractor.of(eventTime);
        WindowExtractor<IConfigurableWindow> windowExtractor = WindowExtractor.of(windowType);
        long timestamp = timeExtractor.getTimestampFromElement(element, context);
        //get all the windows where that element belongs
        Collection<Window> windowsToAssign = windowExtractor.getWindows(element, timestamp);

        //register windows to the windowManager
        for (Window w : windowsToAssign) {
            windowManager.add(w);
        }

        //add element to the dataManager for storing
        dataManager.addElement(element);

        //get the windows that are expired (they are before the current time)
        long currentTime = timeExtractor.getCurrentFlinkTime(context);
        List<Window> windowsToFire = windowManager.windowsToFire(currentTime);

        //firing results for the windows
        for (Window window : windowsToFire) {
            fireWindow(collector,window);
        }

        //deleting the fired windows from the window manager
        deleteDataFromFiredWindows(currentTime, windowsToFire);

    }

    private void deleteDataFromFiredWindows(long currentTime, List<Window> windowsToFire) throws java.io.IOException {
        if (windowsToFire.isEmpty())
            return;

        Long start = null;
        Long end = null;
        for (Window w : windowsToFire) {
            if (start == null) {
                start = w.getStart();
            }
            if (currentTime >= w.getMaxTimestamp())
                end = w.getEnd();
            else
                end = w.getStart() - 1;
        }

        dataManager.removeElementsFromRange(start, end);
    }

    private void fireWindow(Collector<Collection<IN>> collector, Window w) throws java.io.IOException {
        collector.collect(
                new ArrayList<>(dataManager.getElementsFromTo(w.getStart(), w.getMaxTimestamp()))
        );
    }
}
