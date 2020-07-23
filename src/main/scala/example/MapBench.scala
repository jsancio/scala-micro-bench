package example

import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ConcurrentHashMap

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class MapBench {
  private[this] val keys = (0 until 1000).map(_.toString).toSet
  private[this] val cMap = {
    val map = new ConcurrentHashMap[String, Int]

    // create map
    for (key <- keys) {
      map.put(key, Int.MaxValue)
    }

    map
  }
  private[this] val iMap = {
    var map = Map.empty[String, Int]

    // create map
    for (key <- keys) {
      map = map + (key -> Int.MaxValue)
    }

    map
  }

  @Benchmark
  def immutableMap: Map[String, Int] = {
    var map = Map.empty[String, Int]

    // create map
    for (key <- keys) {
      map = map + (key -> Int.MaxValue)
    }

    // update map
    for (key <- keys) {
      map = map + (key -> Int.MinValue)
    }

    map
  }

  @Benchmark
  def concurrentMap: ConcurrentMap[String, Int] = {
    val map = new ConcurrentHashMap[String, Int]

    // create map
    for (key <- keys) {
      map.put(key, Int.MaxValue)
    }

    // update map
    for (key <- keys) {
      map.put(key, Int.MinValue)
    }

    map
  }

  @Benchmark
  def immutableMapUpdate: Unit = {
    var map = iMap

    // update map
    for (key <- keys) {
      map = map + (key -> Int.MinValue)
    }
  }

  @Benchmark
  def immutableMapDelete: Unit = {
    var map = iMap

    // update map
    for (key <- keys) {
      map = map - key
    }
  }

  @Benchmark
  def concurrentMapUpdate: Unit = {
    val map = new ConcurrentHashMap[String, Int](cMap)

    // update map
    for (key <- keys) {
      map.put(key, Int.MinValue)
    }
  }

  @Benchmark
  def concurrentMapDelete: Unit = {
    val map = new ConcurrentHashMap[String, Int](cMap)

    // update map
    for (key <- keys) {
      map.remove(key)
    }
  }
}
