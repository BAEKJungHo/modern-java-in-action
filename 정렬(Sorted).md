# 정렬(Sorted)

```java
List<UserMenuDto.Response> userMenusBySorted = userMenus.stream()
        .sorted(Comparator.comparing(UserMenuDto.Response::getOder))
        .collect(Collectors.toList()); // 항상 종료 연산자를 호출해야함 중간 연산자인 sorted 만 호출하면 
```        
