# flatMap을 사용한 스트림 평준화

예를 들어 아래와 같은 코드가 있다고 가정하자

```java
Optional<String> name = optPerson.map(Person::getCar)
  .map(Car::getInsurance)
  .map(Insurance::getName)
```

위 코드의 문제점은 getCar 메서드가 `Optional<Car>`를 반환하므로 Optional 내부의 Person이 `Optional<Car>`로 변환되면서
중첩 Optional이 생긴다. 따라서 flatMap연산으로 Optional을 평준화 해야한다.

> 평준화 과정이란 이론적으로 두 Optional을 합치는 기능을 수행하면서 둘 중 하나라도 null이면 빈 Optional을 생성하는 연산이다.

```java
