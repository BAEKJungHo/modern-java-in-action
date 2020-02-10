# 새로운 날짜와 시간 API 

## LocalDate와 LocalTime

```java
LocalDate date = LocalDate.of(2020, 2, 11);
int year = date.getYear(); // 2020
Month month = date.getMonth9); // SEPTEMBER
DayOfWeek dow = date.getDayOfWeek(); // THURSDAY
int len = date.lengthOfMonth(); // 31
boolean leap = date.isLeapYear(); // false
```

```java
LocalTime time = LocalTime.of(13, 45, 20);
int hour = time.getHour();
int minute time.getMinute();
int second = time.getSecond();
```

## LocalDateTime

LocalDateTime은 LocalDate와 LocalTime을 쌍으로 갖는 복합 클래스이다.

## 날짜 조정, 파싱, 포매팅

- 절대적 방식

```java
LocalDate date1 = LocalDate.of(2020, 2, 11);
LocalDate date2 = date1.withYear(2019); // 2019-02-11
```

- 상대적 방식

```java
LocalDate date1 = LocalDate.of(2020, 2, 11);
LocalDate date2 = date1.plusWeeks(1); // 2020-02-18
```
