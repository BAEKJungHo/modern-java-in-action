# null 대신 Optional 클래스

```java
public String getCarInsuranceName(Person person) {
  return person.getCar().getInsurance().getName();
}
```

위 코드의 문제는 NullPointerException이 발생할 수 있다는 것이다.

NullPointerException을 피하려면  필요한 곳에 다양한 null 확인 코드를 추가할 것인데, 아래처럼 반복 패턴 코드를 `깊은 의심(deep doubt)`이라고 부른다.

```java
public String getCarInsuranceName(Person person) {
  if(person != null) {
    Car car = person.getCar();
    if(car != null) }
      Insurance insuracne = car.getInsurance();
      if(insurance != null) {
        return insurance.getName();
      }
    }
  }
  return "Unknown";
}
```

## null 때문에 발생하는 문제

1. 에러의 근원이다.
2. 코드를 어지럽힌다.
3. 아무 의미가 없다
4. 자바 철학에 위배된다.
5. 형식 시스템에 구멍을 만든다.

## Optional<T> 클래스 소개
  
`java.util.Optional<T>` 라는 클래스를 제공, Optional은 값이 있다면 값을 감싸고, 없으면 Optional.empty 메서드로 Optional을 반환한다.

Optional.empty는 Optional의 특별한 싱글턴 인스턴스를 반환하는 정적 팩토리 메서드이다.

```java
public class Person {
  private Optional<Car> car; // 사람이 차를 소유했을 수도 소유하지 않았을 수도 있다.
  public Optional<Car> getCar() {
    return car;
  }
}
```
NPE(NullPointerException)을 피하기 위해서 모든 객체에 Optional을 써도 되지 않을까? 라고 생각이 들수 도 있다. 
하지만 어떤 변수나 객체가 무조건 null이 아니어야 하는 객체를 Optional로 감싸게되면 고쳐야하는 문제를 감추는 꼴이 되어버리기 때문에
semeantic이 명확하지 않아진다.

### null이 아닌 값으로 Optional 만들기

정적 팩터리 메서드 `Optional.of`로 null이 아닌 값을 포함하는 Optiaonl을 만들 수 있다.

```java
Optional<Car> optCar = Optional.of(car);
```

car가 null이라면 NPE가 발생한다. 만약 Optional을 사용하지 않았다면, Car 프로퍼티에 접근하려 할 때 에러가 발생했을 것이다.

### null값으로 Optional 만들기

정적 팩터리 메서드 Optional.ofNullable로 null갑승ㄹ 저장할 수 있는 Optional을 만들 수 있다.

```java
Optioanl<Car> optCar = Optional.ofNullable(car);
```

### 맵으로 Optional 값을 추출하고 변환하기

```java
String name = null;
if(insurance != null) {
  name = insurance.getName();
}
```

위와 같은 유형의 패턴에서는 Optional은 map을 지원한다.

```java
Optional<Insurance> optInsurace = Optioanl.ofNullable(insuracne);
Optional<String> name = opInsurance.map(Insurance::getName);
```

만약 아래와 같이 여러 메서드를 호출할 때는 어떻게 변환해야 할까?

```java
public String getCarInsuranceName(Person person) {
  return person.getCar().getInsuracne.getName();
}
```

```java
public String getCarInsuarnceName(Optional<Person> person) {
  return person.flatMap(Person::getCar)
          .flatMap(Car::getInsurance)
          .map(Insurance::getName)
          .orElse("Unknown"); // optional이 비어있으면 기본값 사용
}
```

