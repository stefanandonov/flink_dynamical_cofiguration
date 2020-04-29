package process;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatisticsBuilder {

    Map<String, Object> map = new LinkedHashMap<>();

    void reset() {
        map = new LinkedHashMap<>();
    }

    void setMin(double min) {
        map.put("min", min);
    }

    void setMax(double max) {
        map.put("max", max);
    }

    void setSum(double sum) {
        map.put("sum", sum);
    }

    void setCount(long count) {
        map.put("count", count);
    }

    void setAverage(double average) {
        map.put("average", average);
    }

    void setMedian(double median) {
        map.put("median", median);
    }

    void setQ1(double q1){
        map.put("q1", q1);
    }

    void setQ3(double q3) {
        map.put("q3", q3);
    }

    String getResult() {
        if (map.isEmpty())
            return null;
        String result = new Gson().toJson(map, LinkedHashMap.class);
        reset();
        return result;
    }
}
