package process;

import high_level_windowing.IConfigurableWindow;
import messages.MatchedMessage;
import messages.output.RuleOutputMessage;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.util.Collection;

public class AggregateProcessFunction  extends ProcessFunction<Collection<MatchedMessage>, Tuple2<RuleOutputMessage, String>> {

    @Override
    public void processElement(Collection<MatchedMessage> value, Context ctx, Collector<Tuple2<RuleOutputMessage, String>> out) throws Exception {
        RuleAccumulator ruleAccumulator = new RuleAccumulator();
        value.forEach(ruleAccumulator::add);
        Tuple2<RuleOutputMessage, String> result = ruleAccumulator.getResult();
        if (result!=null) {
            result.f0.setTimestamp(ctx.timestamp());
            out.collect(result);
        }
    }
}
