## Collector 인터페이스

```java
public interface Collector<T, A, R> {
  Supplier<A> supplier();
  BiConsumer<A, T> accumulator();
  Function<A, R> finisher();
  BinaryOperator<A> combiner();
  Set<Characteristics> characteristics();
}
```

- T는 수집될 스트림 항목의 제네릭 형식
- A는 누적자, 즉 수집과정에서 중간 결과를 누적하는 객체의 형식
- R은 수집 연산 결과 객체의 형식(항상 그런 것은 아니지만 보통 컬렉션 형식이다.)
