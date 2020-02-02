# 도메인 모델에 Optional을 사용했을 때 데이터를 직렬화할 수 없는 이유

자바 언어 아키텍트인 브라이언 고츠(Brian Goetz)는 Optional의 용도가 선택형 반환값을 지원하는 것이라고 명확하게 못박았다. Optional 클래스는
필드 형식으로 사용할 것을 가정하지 않았으므로 Serializable 인터페이스를 구현하지 않는다. 따라서 우리 도메인 모델에 Optioanl을 사용한다면
직렬화(serializable) 모델을 사용하는 도구나 프레임워크에서 문제가 생길 수 있다.이와 같은 단점에도 불구하고 여전히 Optional을 사용해서
도메인 모델을 구성하는 것이 바람직하다고 생각한다. 특히 객체 그래프에서 일부 또는 전체 객체가 null일 수 있는 상황이라면 더욱 그렇다.

직렬화 모델이 필요하아면 아래와 같이 Optional로 값을 반환받을 수 있는 메서드를 추가하는 방식을 권장한다.

```java
public class Person {
  private Car car;
  public Optioanl<Car> getCarAsOptional() {
    return Optional.ofNullable(car);
  }
}
```
