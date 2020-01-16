package com.bjh;

import com.bjh.chapter4.dish.DishVo;
import com.bjh.chapter4.dish.Type;
import com.bjh.chapter5.Trader;
import com.bjh.chapter5.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class Main {

    public static void main(String[] args) {

        /**
         * 필터 : filter
         * 변형 : map
         * 변환 : collect, reduce
         */

        /**
         * 데이터 소스 : 요리 메뉴
         * 연속된 요소 : 내부 리스트 객체
         * */
        List<DishVo> menu = Arrays.asList(
                new DishVo("pork", false, 800, Type.MEAT),
                new DishVo("beef", false, 700, Type.MEAT),
                new DishVo("chicken", false, 400, Type.MEAT),
                new DishVo("fries", true, 500, Type.OTHER),
                new DishVo("rice", true, 350, Type.OTHER),
                new DishVo("fruit", true, 120, Type.OTHER),
                new DishVo("pizza", false, 600, Type.OTHER),
                new DishVo("prawns", false, 300, Type.FISH),
                new DishVo("salmon", false, 450, Type.FISH)
        );

        /**
         * 매핑(Mapping)
         * map 은 함수를 적용한 결과가 새로운 버전을 만든다.
         */
        List<String> dishNames = menu.stream()
                .map(DishVo::getName)
                .collect(toList());

        /**
         * 스트림으로 데이터 처리
         * filter, map, limit, collect 데이터 처리 연산
         * - pipeline
         * collect 를 제외한 처리 연산들
         * filter, map, limit은 스트림을 반환하지만, collect는 리스트를 반환한다.
         */
        List<String> threeHighCaloricDishNames =
                menu.stream() // 메뉴에서 스트림을 얻는다.
                     // 파이프라인 연산 만들기
                    .filter(dishVo -> dishVo.getCalories() > 300) // 고칼로리 요리 필터링
                    .map(DishVo::getName) // 요리명 추출
                    .limit(3) // 선착순 3개만 선택
                    .collect(toList()); // 결과를 다른 리스트로 저장

        System.out.println(threeHighCaloricDishNames);

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        /** 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오. */
        List<Transaction> transaction2011s = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());

        /** 거래자가 근무하는 모든 도시를 중복 없이 나열하시오. */
        List<String> workingCitys = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());

        /** 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오. */
        List<Trader> workers = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());

        /** 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오. */
        String names = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(joining());

        /** 밀라노에 거래자가 있는가? */
        boolean isMilanBased = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader()
                .getCity()
                .equals("Milan"));

        /** 케임브리지에 거주하는 거래자의 모든 트랜잭션값 추출 */
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        /** 전체 트랜잭션중 최댓값은? */
        Optional<Integer> highestValue = transactions.stream()
                    .map(Transaction::getValue)
                    .reduce(Integer::max);

        /** 리스트의 모든 짝수를 선택하고 중복을 필터링 */
        List<Integer> numbers = Arrays.asList(1,2,1,3,3,2,4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        /**
         * 스트림 슬라이싱(Stream slicing)
         * takeWhile과 dropWhile로 조건에 벗어나면 반복 작업 중단
         * filter의 경우는 조건을 찾아도 끝까지 반복한다.
         * 자바 9 이상 사용 가능
         */
        List<DishVo> specialMenu = Arrays.asList(
                new DishVo("seasonal fruit", true, 12, Type.OTHER),
                new DishVo("prawns", false, 300, Type.FISH),
                new DishVo("rice", true, 350, Type.OTHER)
        );
        List<DishVo> sliceMenu1 = specialMenu.stream()
                    .takeWhile(dish -> dish.getCalories() < 320) // 320보다 작은 것들 선택
                    .collect(toList());
        List<DishVo> sliceMenu2 = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320) // 320보다 큰 것들 선택
                .collect(toList());

        /**
         * flatMap
         * flatMap은 각 배열을 스트림이 아니라 스트림의 콘텐츠로 매핑한다. 즉, 하나의 평면화된 스트림을 반환한다.
         */
        List<String> uniqueCharacters =
                words.stream()
                        .map(word -> word.splict("")) // 각 단어를 개별 문자를 포함하는 배열로 반환
                        .flatMap(Arrays::stream) // 생성된 스트림을 하나의 스트림으로 평면화
                        .distinct()
                        .collect(toList());

        /** 두 개의 숫자 리스트 1,2,3 과 3,4 가 있을때 모든 숫자 쌍의 리스트를 반환 하시오 (1,3) (1,4) (2,3) ... */
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                .collect(toList());


        /**
         * 검색과 매칭
         * anyMach : 프레디케이트가 적어도 한 요소와 일치하는지 확인
         * allMach : 프레디케이트가 모든 요소와 일치하는지 검사
         * noneMatch : 프레디케이트가 모든 요소와 일치하지 않는 경우 검사
         */
        if(menu.stream().anyMatch(DishVo::isVegetarian)) {
            System.out.println("The menu is somewhat vegetarian friendly!!");
        }

        boolean isHealthy1 = menu.stream().allMatch(dish -> dish.getCalories() < 1000);
        boolean isHealthy2 = menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);

        /**
         * 쇼트 서킷(short circuit)
         * 예를 들어 여러 and 연산으로 연결된 커다란 불리언 표현식을 평가한다고 가정하자.
         * 표현식에서 하나라도 거짓이라는 결과가 나오면 나머지 표현식의 결과와 상관없이 전체 결과도 거짓이 된다.
         * 이러한 상황을 쇼트서킷이라고 부른다.
         * allMatch, noneMatch, findFirst, findAny, limit 등의 연산은 스트림의 모든 요소를 처리하지 않고 반환할 수 있다.
         * 즉, 원하는 요소를 찾고 즉시 결과를 반환할 수 있다.
         */
        Optional<DishVo> dish = menu.stream()
                .filter(DishVo::isVegetarian)
                .findAny(); // 임의의 요소 반환

        List<Integer> someNumbers = Arrays.asList(1,2,3,4,5);
        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream()
                .map(n -> n * n)
                .filter(n -> n % 3 == 0)
                .findFirst(); // 첫 번째 요소 찾기

        /**
         * 리듀싱 연산(fold)
         * reduce는 두 개의 인수를 갖는다.
         * 1. 초기값
         * 2. 두 요소를 조합해서 새로운 값을 만드는 BinaryOperator<T>
         */
        int sum = numbers.stream().reduce(0, (a,b) -> a+b);

        /**
         *  숫자리스트 : 8,4,1,9,7,5
         * 위 처럼 되어있을 경우 초기값 0이 a의 자리에 들어가고, b에는 8이 들어간다.
         * 누적값이 8이 되었다.
         * 누적값 8이 a에 들어가고, b에는 4가 들어간다.
         * 누적값이 12가 되었다.
         * 누적값 12가 a에 들어가고, b에는 1이 들어간다.
         * 누적값이 13이 되었다.
         * 반복...
         */
        int sum2 = numbers.stream().reduce(0, (a, b) -> a + b);

        /**
         * 초기값을 받지 않도록 오버로드된 reduce도 있다.
         * 이 reduce는 Optional 객체를 반환한다.
         */
        Optional<Integer> sum3 = numbers.stream().reduce((a, b) -> (a + b));

        /**
         * 맵 리듀스 패턴(map-reduce pattern)
         * 구글이 웹 검색 엔진에 적용한 사례
         */
        int count = menu.stream()
                .map(d -> 1)
                .reduce(0, (a,b) -> a + b);

        /**
         * 기본형 특화 스트림(primitive stream specialization)
         * Integer::sum 과 같은 경우 박싱 비용이 든다.
         * 박싱 비용을 피하기 위해서 스트림 API는 기본형 특화 스트림을 제공한다.
         * IntStream, DoubleStream, LongStream
         * mapToInt, mapToDouble, mapToLong
         */
        int calories = menu.stream()
                .mapToInt(DishVo::getCalories)
                .sum();

        /**
         * 객체 스트림으로 복원
         */
        IntStream intStream = menu.stream().mapToInt(DishVo::getCalories); // 스트림을 숫자 스트림으로 변환
        Stream<Integer> stream = intStream.boxed(); // 숫자 스트림을 스트림으로 변환

        /**
         * rangeClose(1, 100)은 1과 100을 포함
         * range(1, 100)은 1과 100을 제외
         */
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());

        /**
         * 피보나치수열
         * 0, 1, 1, 2, 3, 5, 8 ...
         */
        Stream.iterate(new int[]{0, 1}, t-> new int[] {t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);

        /**
         * Collectors.reducing
         * reducing은 세 개의 인수를 받는다.
         * 1. 초기값
         * 2. 합계 함수
         * 3. 변환 함수 (세 번째 인수는 같은 종류의 두 항목을 하나의 값으로 더하는 BinaryOperator이다.)
         * */

        /**
         * 한 개의 인수를 갖는 reducing
         * 두 번째 인수에서 자기 자신을 그대로 반환하는 항등함수(identity function)을 받는다.
         */
        Optional<DishVo> mostCaloireDish = menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        /** 각 요리를 요리명으로 변환 후 요리명 연결 joining */
        String joiningMenu = menu.stream()
                .map(DishVo::getName)
                .collect(joining());

        /** 각 요리를 요리명으로 변환 후 문자열을 누적자로 사용하여 요리명 연결 reducing */
        String reducingMenu = menu.stream()
                .map(DishVo::getName)
                .collect(reducing((s1, s2) -> s1 + s2))
                .get();

        String reducingMenu2 = menu.stream()
                .collect(reducing("", DishVo::getName, (s1, s2) -> s1 + s2));
        
        List<String[]> treeCategories = categoryRepository.findTreeCategories(parent).stream()
                .filter(category -> categorySeq.equals(category.getCategorySeq()))
                .map(category -> category.getDepthFullName().split(">"))
                .collect(toList());
        
        /** 
          * 그룹화 groupingBy
          * 생선, 고기 그 밖의 것들로 그룹화 
          */
        Map<Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
    }
    
    /**
     * 회사 프로세스
     * 어떤 이름 목록에서, 한 글자로 된 이름을 제외한 모든 이름을 대문자화 해서 쉼표로 연결한 문자열 구현
     */
    public String cleanNames(List<String> names) {
        if(names == null) return "";
        return names
                .stream()
                .filter(name -> name.length() > 1)
                .map(name -> capitalize(name))
                .collect(joining(","));
    }
    
    private String capitalize(String e) {
        return e.substring(0,1).toUpperCase() + e.substring(1, e.length());
    }
}
