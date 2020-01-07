package com.bjh;

import com.bjh.chapter4.dish.DishVo;
import com.bjh.chapter4.dish.Type;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
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
    }
}
