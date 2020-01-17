# 자바 벤치마크(JHM library)를 사용한 스트림 성능 측정

> 벤치마크(Benchmark) 란? 벤치마크는 메시징 응용 프로그램을 위한 테스트 프로그램을 만들고 이 테스트 프로그램의 메시지 처리량이나 기타 성능 요소를 측정하는 프로세스입니다.
>
> https://docs.oracle.com/cd/E19148-01/820-0845/aeojk/index.html

`자바 마이크로벤치마크 하니스(Java Microbenchmark Harness, JMH)`라는 라이브러리를 이용해 작은 벤치마크를 구현할 수 있다. JMH를 이용하면 간단하고,
어노테이션 기반 방식을 지원하며,  안정적으로 자바 프로그램이나 자바 가상머신(JVM)을 대상으로 하는 다른 언어용 벤치마크를 구현할 수 있다.

사실 JVM으로 실해되는 프로그램을 벤치마크 하는 작업은 쉽지 않다. 핫스팟(Hotspot)이 바이트코드를 최적화 하는데 필요한 준비 시간, 가비지 컬렉터로 인한
오버헤드 등과 같은 여러 요소를 고려해야한다.

메이븐의 pom.xml에 의존성을 추가해서 JMH를 사용할 수 있다.

- JMH Library

```xml
<dependency>
  <groupId>org.openjdk.jmh</groupId>
  <artifactId>jmh-core</artifactId>
  <version>1.17.4</version>
</dependency>
<dependency>
  <groupId>org.openjdk.jmh</groupId>
  <artifactid>jbh-generator-annprocess</artifactId>
  <version>1.17.4</version>
</dependency>
```

- 자바 아카이브(Java Archive, JAR) 파일을 만드는데 도움을 주는 어노테이션 프로세서를 포함하는 라이브러리

```java
<build> 
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <executions>
        <execution>
          <phase>package</phase>
          <goals><goal>shade</goal></goals>
          <configuration>
            <finalName>benchmarks</finalName>
            <transformers>
              <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
              <mainClass>org.openjdk.jmh.Main</mainClass>
              </transformer>
            </transformers>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

- n개의 숫자를 더하는 함수의 성능 측정

```java
@BenchmarkMode(Mode.AverageTime) // 벤치마크 대상 메서드를 실행하는데 걸린 평균 시간 측정
@OutputTimeUnit(TimeUnit.MILLISECONS) // 벤치마크 결과를 밀리초 단위로 출력
@Fork(2, jvmArgs={"-Xms4G", "-Xmx4G"}) // 4Gb의 힙 공간을 제공한 환경에서 두 번 벤치마크를 수행해 결과의 신뢰성 확보
public class ParallelStreamBenchmark {
  private static final long N = 10_000_000L;
  
  @Benchmark // 벤치마크 대상 메서드
  publiclong sequentialSum() {
    return Stream.iterate(1L, i -> i + 1).limit(N)
      .reduce(0L, Long::sum);
  }
  
  @TearDown(Level.Invocation) // 매 번 벤치마크를 실행한 다음에는 가비지 컬렉터 동작 시도
  public void tearDown() {
    System.gc();
  }
}
```

클래스를 컴파일 하면 이전에 설정한 메이븐 플러그인이 benchmarks.jar라는 두 번째 파일을 만든다. 이 파일을 다음처럼 실행할 수 있다.

> java -jar ./target/benchmarks.jar ParallelStreamBenchmark

위 결과가 정확하지 않을 수도 있는데, 기계가 지원하는 코어의 갯수 등이 실행 시간에 영향을 미칠 수 있기 때문이다.
