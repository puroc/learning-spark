package com.example.spark.rdd

import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Assert, Before, Test}

@Test
class RddTest extends SparkTestEnv {

  private var sc:SparkContext = null

  @Before
  @throws[Exception]
  def setUp(): Unit = {
    val conf = new SparkConf()
    conf.setAppName("RddOnCollection")
    conf.setMaster("local")
    sc = new SparkContext(conf)
  }

  @After
  @throws[Exception]
  def tearDown():Unit={
    sc.stop
  }


  @Test
  def count: Unit = {
    val nums = 1.to(100)
    val rdd = sc.parallelize(nums)
    val sum = rdd.reduce(_ + _)
//    println(sum)
    Assert.assertEquals(5050, sum)
  }
}