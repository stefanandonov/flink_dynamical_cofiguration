package process;


import messages.output.RuleOutputMessage;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class WindowTimestampAssigner extends ProcessWindowFunction<Tuple2<RuleOutputMessage, String>, Tuple2<RuleOutputMessage, String>, String, TimeWindow> {

    @Override
    public void process(String s, Context context, Iterable<Tuple2<RuleOutputMessage, String>> elements, Collector<Tuple2<RuleOutputMessage, String>> out) throws Exception {
        for (Tuple2<RuleOutputMessage, String> ruleStatisticsMessageStringTuple2 : elements) {
            ruleStatisticsMessageStringTuple2.f0.setTimestamp(context.window().getEnd());
            out.collect(ruleStatisticsMessageStringTuple2);
        }
    }
}
