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


Collector 인터페이스를 이용해서 `커스텀 컬렉터`를 개발할 수 있다. (자세한건 책 232p 참고)

- 장점 : 성능이 뛰어남(약 32퍼센트가량 상승)
- 단점 : 가독성과 재사용성이 
