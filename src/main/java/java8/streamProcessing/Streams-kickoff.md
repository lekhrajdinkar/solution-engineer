https://www.baeldung.com/java-collections-interview-questions

# Java 8 streams

### Introduction :
Java 8 introduced the concept of streams, which allow you to process sequences of elements in a functional style. Streams provide a way to work with collections of objects, such as lists or arrays, in a more concise and expressive manner.


**Creation of Streams** : 
You can create streams from various sources, such as collections, arrays, or by using static methods in the Stream interface.

**Intermediate Operations** : 
Streams support various intermediate operations, such as filter, map, flatMap, distinct, sorted, limit, and skip, which can be used to transform, filter, or manipulate the stream elements.

**Terminal Operations** : 
Terminal operations such as forEach, collect, reduce, count, anyMatch, allMatch, and noneMatch are used to produce a result or a side-effect.

**Lazy Evaluation**: 
Stream operations are lazily evaluated, meaning they are only executed when a terminal operation is invoked.

---

### Mastering Topics:

Functional Interfaces: Understand functional interfaces like Predicate, Function, Consumer, and Supplier, which are often used in stream operations.

Lambda Expressions: Learn how to use lambda expressions to concisely define implementations of functional interfaces.

Streams Basics: Understand the basics of working with streams, including creating streams, intermediate operations, and terminal operations.

Intermediate Operations: Learn about intermediate operations such as filter, map, flatMap, distinct, sorted, limit, and skip, and understand how and when to use them.

Terminal Operations: Understand terminal operations like forEach, collect, reduce, count, anyMatch, allMatch, and noneMatch, and how to use them to process streams.

Parallel Streams: Learn how to create parallel streams to take advantage of multi-core processors for improved performance.

Error Handling: Understand how to handle exceptions that may occur during stream processing.

Collectors: Learn about collectors, which are used to accumulate the elements of a stream into a collection or to perform a reduction operation.

Optional: Understand how Optional can be used in stream operations to represent values that may or may not be present.

Stream Performance: Understand the performance characteristics of streams and how to write efficient stream operations.

Advanced Stream Operations: Learn about advanced stream operations, such as groupingBy, partitioningBy, joining, and reducing.

Integration with Existing APIs: Learn how to integrate streams with existing Java APIs, such as the Collections framework.

Best Practices: Understand best practices for using streams, such as when to use streams versus traditional loops and how to write readable and maintainable stream code.

Testing: Learn how to test stream operations to ensure they behave as expected.