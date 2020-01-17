## Collectors 클래스의 정적 팩토리 메서드

> collect(Collectors.메서드명)과 같이 사용

- `toList()` 
  - return List<T>
  - 스트림의 모든 항목을 리스트로 수집
  - List<Dish> dishes = menu.stream().collect(toList());
  
- `toSet()`
  - return Set<T>
  - 스트림의 모든 항목을 중복이 없는 집합으로 수집
  - Set<Dish> dishes = menu.stream().collect(toSet());

- `toCollection()`
  - return Collection<T>
  - 스트림의 모든 항목을 발행자가 제공하는 컬렉션으로 수집
  - Collection<Dish> dishes = menu.stream().collect(toCollection(), ArrayList::new);
  
- `counting()`
  - return Long
  - 스트림의 항목 수 계산
  - long howManyDishes = menu.stream().collect(counting());
  
- `summingInt()`
  - return Integer
  - 스트림의 항목에서 정수 프로퍼티 값을 더함
  - int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
  
- `averagingInt()`
  - return Double
  - 스트림 항목의 정수 프로퍼티의 평균값 계산
  - double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));
  
- `summarizingInt()`
  - return IntSummaryStatistics
  - 스트림 내 항목의 최댓값, 최솟값, 합계, 평균 등의 정수 정보 통계 수집
  - IntSummaryStatistics menuStatistics = menuStream.collect(summarizingInt(Dish::getCalories));
  
- `joining()`
  - return String
  - 스트림의 각 항목에 toString() 메서드를 호출한 결과 문자열 연결
  - String shortMenu = menuStream.map(Dish::getName).collect(joining(", "));

- `maxBy(), minBy()`
  - return Optional<T>
  - 주어진 비교자를 이용해서 스트림의 최댓값 요소를 Optional로 감싼 값을 반환. 스트림에 요소가 없을 때는 Optional.empty() 반환
  - Optional<Dish> fattest = menuStream.collect(maxBy(comparingInt(Dish::getCalories)));
  
- `reducing()`
  - 누적자를 초기값으로 설정한 다음에 BinaryOperator로 스트림의 각 요소를 반복적으로 누적자와 합쳐 스트림을 하나의 값으로 리듀싱
  - int totalCalories = menuStream.collect(reducing(0, Dish::getCalories, Integer::sum));
  
- `collectingAndThen()`
  - 다른 컬렉터를 감싸고 그 결과에 변환 함수 적용
  - int howManyDishes = menuStream.collect(collectingAndThen(toList(), List::size));
  
- `groupingBy()`
  - return Map<K, List<T>>
  - 하나의 프로퍼티 값을 기준으로 스트림의 항목을 그룹화 하며 기준 프로퍼티 값을 맵의 키로 사용
  - Map<Type, List<Dish>> dishesByType = menuStream.collect(groupingBy(Dish::getType));
  
- `partitioningBy()`
  - return Map<Boolean, List<T>> 
  - 프레디케이트를 스트림의 각 항목에 적용한 결과로 항목 분할
  - Map<Boolean, List<Dish>> vegetarianDishes = menu.stream().collect(partitioningBy(Dish::isVegetarain));
  
 
