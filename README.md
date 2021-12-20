# JndiLookupRemover

A Java agent which mitigates CVE-2021-44228 (log4shell) by patching the JndiLookup class.

### How to use
Download the latest release and add it as a Java agent to your application using `-javaagent:<release jar path>`.

### What it does
JndiLookupRemover patches log4j so that JNDI lookups return the static string `!!PREVENTED JNDI LOOKUP!!`. This prevents the log4shell exploit and allows for easy querying for exploit attempts.

### How it works
JndiLookupRemover works by replacing the contents of the `lookup` and `convertJndiName` methods of any class which match any of the following criteria:
- Class is named `JndiLookup`
- Class declares a field named `CONTAINER_JNDI_RESOURCE_PATH_PREFIX`

### Advantages over other solutions
JndiLookupRemover offers a couple advantages over other class manipulating solutions:
1. Most class or JAR editing solutions only patch classes which are located in the `org.apache.logging.log4j.core.lookup` package. This will fail if log4j has been relocated to a different package. JndiLookupRemover would correctly patch relocated classes as it looks for the JndiLookup class in any package.
2. Most class or JAR editing solutions patch classes based on only the name. This would fail if an obfuscator renamed the class. JndiLookupRemover would correctly patch obfuscated classes as long as the field names are not obfuscated as well.