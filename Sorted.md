# 정렬

```java
List<UserMenuDto.Response> userMenusBySorted = userMenus.stream()
        .sorted(Comparator.comparing(UserMenuDto.Response::getOder))
        .collect(Collectors.toList());
 ```               
