package com.bjh.chapter4.dish;

/**
 * DishVo 불변형 클래스
 */
public class DishVo {

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public DishVo(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

}
