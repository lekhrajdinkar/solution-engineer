# Java 17 new Features
- https://chatgpt.com/c/a9bd942f-f423-49ef-bc91-85b4cb6af15d
---
## Java 12
- Compact Number Formatting: Provides a more concise way to format numbers.
- JVM Constants API: Introduces an API to model the key class-file and run-time artifacts, the constant pool, and dynamic constants.
- Shenandoah Garbage Collector (Experimental): A low-pause-time garbage collector.
- Microbenchmark Suite (JMH): Includes the Java Microbenchmark Harness (JMH) by default.
---

## Java 13
> - A modernization `Socket API`.
- Dynamic CDS Archives: Introduces a dynamic archive format for the shared classes feature, improving `startup performance`.

---

## Java 14
> - `Switch Expressions` finalized
- Pattern Matching for instanceof (Preview): Enhances the instanceof operator to allow pattern matching and conditional extraction.
- Helpful NullPointerExceptions: Improves the readability of NullPointerException messages.
- Packaging Tool (`Incubator`): Adds a tool for packaging self-contained Java applications.
---

## Java 15
> - Hidden Classes: Introduces a mechanism for classes that are not discoverable via normal means.
> - Text Blocks (Standard): Finalizes the text blocks feature introduced as a preview in Java 13.
- ZGC: Adds macOS support for the Z Garbage Collector (ZGC).

---

## Java 16
> - `Records` (Standard): Finalizes the records feature introduced as a preview in Java 14.
- Pattern Matching for instanceof (Standard): Finalizes the pattern matching for instanceof feature introduced as a preview in Java 14.
- JEP 338: Vector API (Incubator): Introduces an initial iteration of an API for expressing vector computations.
- JEP 376: ZGC: Adds support for concurrent thread-stack processing, improving scalability.
- Unix-Domain Socket Channels: Adds Unix-domain socket support to the socket channel and server socket channel APIs.
---

## Java 17
> - `Sealed Classes` (Standard): Finalizes the sealed classes feature introduced as a preview in Java 15.
- Pattern Matching for switch (Standard): Introduces pattern matching for switch expressions.
- Foreign Function & Memory API (Incubator): Introduces an API to allow Java programs to interoperate with code and data outside of the Java runtime.
- Context-Specific Deserialization Filters: Enhances the serialization mechanism to allow filtering during deserialization based on the context.
- Strongly Encapsulate JDK Internals by Default: Strengthens encapsulation of JDK internals by default, making them inaccessible to code outside the JDK.
- Remove the Experimental AOT and JIT Compiler: Removes the Graal-based experimental just-in-time compiler and ahead-of-time compiler from the JDK.
---

