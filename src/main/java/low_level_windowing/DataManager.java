package low_level_windowing;

import high_level_windowing.IConfigurableWindow;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


public class DataManager<T extends IConfigurableWindow> implements Serializable {

    ValueState<TreeMap<Long, T>> buffer;

    private int bufferSize;

    public DataManager(int bufferSize, RuntimeContext runtimeContext) {
        ValueStateDescriptor<TreeMap<Long, T>> valueStateBuffer = new ValueStateDescriptor<TreeMap<Long, T>>(
                "buffer",
                TypeInformation.of(new TypeHint<TreeMap<Long, T>>() {
                }),
                new TreeMap<Long, T>(Comparator.comparing(Long::longValue))
        );
        this.buffer = runtimeContext.getState(valueStateBuffer);
        this.bufferSize = bufferSize;
    }


    public void addElement(T e) throws Exception {
        TreeMap<Long, T> bufferTreeMap = buffer.value();

        if (bufferTreeMap.size() == this.bufferSize)
            bufferTreeMap.remove(bufferTreeMap.firstKey());

        bufferTreeMap.put(e.getTimestamp(), e);

        this.buffer.update(bufferTreeMap);
    }


    public List<T> getElementsFromTo(Long fromTimestamp, Long toTimestamp) throws IOException {
        TreeMap<Long, T> bufferTreeMap = buffer.value();

        return new ArrayList<>(bufferTreeMap.subMap(fromTimestamp, toTimestamp).values());
    }

    public void removeElementsFromRange(Long fromTimestamp, Long toTimestamp) throws IOException {
        TreeMap<Long, T> bufferTreeMap = buffer.value();

        bufferTreeMap.subMap(fromTimestamp, toTimestamp).clear();
//        bufferTreeMap.keySet().removeAll(timestamps);

//        for (Long timestamp : timestamps)
//            bufferTreeMap.remove(timestamp);

        buffer.update(bufferTreeMap);
    }


}
