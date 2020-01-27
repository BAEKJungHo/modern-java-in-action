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
