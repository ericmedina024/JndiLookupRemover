package xyz.ericmedina024.jndilookupremover;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Main {

    // https://github.com/apache/logging-log4j2/blob/4b789c8572d1762cd55f051971d91e44f0628908/log4j-core/src/main/java/org/apache/logging/log4j/core/lookup/JndiLookup.java#L41
    public static final String JNDI_LOOKUP_CLASS_NAME = "JndiLookup";
    public static final String JNDI_LOOKUP_PATH_FIELD_NAME = "CONTAINER_JNDI_RESOURCE_PATH_PREFIX";

    public static void premain(String args, Instrumentation inst) {
        new AgentBuilder
                .Default()
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(
                        ElementMatchers
                                .declaresField(ElementMatchers.named(JNDI_LOOKUP_PATH_FIELD_NAME))
                                .or(target -> target != null && JNDI_LOOKUP_CLASS_NAME.equals(target.getSimpleName()))
                )
                .transform(new JndiLookupTransformer())
                .installOn(inst);

    }


}
