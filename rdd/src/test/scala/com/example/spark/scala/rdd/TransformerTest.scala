package com.example.spark.scala.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Assert, Before, Test}

@Test
class TransformerTest extends SparkTestEnv {

  private var sc: SparkContext = null

  private val FILE_PATH = "/Users/puroc/git/learning-spark/rdd/src/test/resources/"

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
  def tearDown(): Unit = {
    sc.stop
  }


  @Test
  def testCount: Unit = {
    val nums = 1.to(100)
    val rdd = sc.parallelize(nums)
    val sum = rdd.reduce(_ + _)
    println(sum)
    Assert.assertEquals(5050, sum)
  }

  @Test
  def testReduceByKey: Unit = {
    val rdd1: RDD[String] = sc.textFile(FILE_PATH + "english.txt",3)
    rdd1.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey((x, y) => {
      x + y
    }).foreach(result => println(result._1 + "," + result._2))
  }

  @Test
  def testGroupByKey: Unit = {
    val rdd1: RDD[String] = sc.textFile(FILE_PATH + "english.txt",3)
    rdd1.flatMap(line => line.split(" ")).map(word => (word, 1)).groupByKey().foreach(result => println(result._1 + "," + result._2))
  }
}