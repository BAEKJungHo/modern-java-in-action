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

정적 팩터리 메서드 Optional.ofNullable로 null 저장할 수 있는 Optional을 만들 수 있다.

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

### orElse와 isPresent

orElse는 값이 없는 경우 디폴트 값을 반환할 수 있으며, isPresent는 Optional이 값을 포함하는지 여부를 알려준다.

## 자바9 Optional 스트림 조작

자바 9에서는 Optional을 포함하는 스트림을 쉽게 처리할 수 있도록 Optional에 stream() 메서드를 추가했다.

## 예외와 Optional 클래스

```java
public static Optional<Integer> stringToInt(String s) {
  try {
    return Optional.of(Integer.parseInt(s));
  } catch(NumberFormatException e) {
    return Optional.empty();
  } 
}
```

## Optional Code

```java
private Product findProductById(Long productId) {
  return Optional.ofNullable(productRepository.findOne(productId)
    .orElseThrow(() -> new RuntimeException("해당 제품  ID 는 없는  ID 입니다.");
}
```

```java
Optional<Object> objectOptional = Optional.empty();

Object object1 = objectOptional.orElse(new Object());
Object object2 = objectOptional.orElseGet(() -> new Object());
Object object3 = objectOptional.orElseGet(Object::new);
```

```java
/**
 * of 를 사용하는 경우는 null 이 올 수 없다는 것을 명시하는 것이기 때문에
 * get 으로 꺼내쓰면된다.
 */
public User findUsers() {
  Optional<User> userOpt = Optional.of(userRepository.findUsers());
  return userOpt.get();
}
```

```java
Optional<UserVo> userVo = Optional.ofNullable(findUserById(employeeVo));
Employee employee = new Employee();
employee.setName(userVo.map(UserVo::getName).orElseGet(() -> ""));
// employee.setName(userVo.map(UserVo::getName)
    .orElseThrow(() -> new RuntimeException("ID 에 해당하는 유저가 존재하지 않습니다.")));
```

### ifPresent

```java
Member member = memberRepository.findById(id);
if (member != null) {
    if (member.isAdmin()) {
        member.addAdminPermissions();
    } else {
        member.addDefaultPermissions();
    }
}
```

null 체크 하는 로직을 optional 을 통해 없애보자.


```java
Optional<Member> memberOptional = memberRepository.findById(id);
memberOptional.ifPresent(member -> {
    if (member.isAdmin()) {
        member.addAdminPermissions();
    } else {
        member.addDefaultPermissions();
    }
});
```

null을 확인하던 if 문 대신에 ifPresent 함수를 호출하면서 그 안에 함수를 제공했다. 값이 존재하는 경우에 그 안에 있는 내용을 실행한다고 읽을 수 있으니 null 을 확인하는 if 문을 사용했던 첫번째 예제에 비해 코드량도 조금 줄어들고 가독성도 좋아졌다.

## References.

> http://homoefficio.github.io/2019/10/03/Java-Optional-%EB%B0%94%EB%A5%B4%EA%B2%8C-%EC%93%B0%EA%B8%B0/
