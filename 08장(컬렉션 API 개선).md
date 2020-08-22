# 컬렉션 API 개선

## 오버로딩 vs 가변인수

List나 Set, Map 인터페이스를 살펴보면 List.of와 같은 다양한 오버로드 버전이 존재한다.

```java
static <E> List<E> of(E e1, E e2, E e3, E e4)
static <E> List<E> of(E e1, E e2, E e3, E e4, E e5)
```

왜 다중요소를 받을 수 있도록 자바 API를 만들지 않은 것인지 궁금할 것이다.

```java
static <E> List<E> of(E... elements)
```

내부적으로 가변 인수 버전은 추가 배열을 할당해서 리스트로 감싼다. 따라서 배열을 할당하고 초기화 하며 나중에 가비지 컬렉션을 사용하는 비용을 지불하게
된다. 고정된 숫자의 요소(최대 10개까지)를 API로 정의하므로 이런 비용을 제거할 수 있다. List.of로 열 개 이상의 요소를 가진 리스트를 만들 수도 있지만
이 때는 가변 인수를 이용하는 메서드가 사용된다.

## 바꿀 수 없는 리스트, 집합, 맵

```java
List<String> friends = List.of("abc", "efg", "hij"); // 변수에 추가 삭제 못함
Set<String> friends2 = Set.of("abc", "efg", "hij");
```

맵은 2가지 방식으로 나뉜다.

1. 10개 이하의 키와 값 쌍을 가진 작은 맵을 만드는 경우
  - 일반적인 Map.of 사용
2. 10개 이상의 데이터를 가지는 맵을 만드는 경우
  - Map.Entry<K, V> 객체를 인수로 받으며 가변 인수로 구현된 Map.ofEntries 팩터리 메서드를 이용하는 것이 좋다.
  
```java
// 1번
Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);

// 2번
import static java.util.Map.entry;
Map<String, Integer> ageOfFriends2 = Map.ofEntries(entry("Raphael", 30), entry("Olivia", 25), entry("Thibaut", 26));
```

Map.entry는 Map.Entry 객체를 만드는 새로운 팩터리 메서드이다.

## Object.equals(E1, E2)
