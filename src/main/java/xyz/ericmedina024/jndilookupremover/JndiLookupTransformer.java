package xyz.ericmedina024.jndilookupremover;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.utility.JavaModule;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class JndiLookupTransformer implements AgentBuilder.Transformer {

    public static final String LOOKUP_RETURN_VALUE = "!!PREVENTED JNDI LOOKUP!!";

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
        return builder
                .method(named("lookup"))
                .intercept(FixedValue.value(LOOKUP_RETURN_VALUE))
                .method(named("convertJndiName"))
                .intercept(FixedValue.value(""));
    }
}
