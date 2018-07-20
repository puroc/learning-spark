package com.example.spark.scala.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object RddOnCollection {

  private val FILE_PATH = "/Users/puroc/git/learning-spark/rdd/src/main/resources/"


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("RddOnCollection")
    conf.setMaster("local")
    val sc = new SparkContext(conf)
//        val nums = 1.to(100)
//        val rdd = sc.parallelize(nums)
//        val sum = rdd.reduce(_+_)
//        println(sum)
    val rdd1:RDD[String] = sc.textFile(FILE_PATH+"english.txt")
    println("size:"+rdd1.collect().size)
    rdd1.flatMap(line=>line.split(" ")).map(word=>(word,1)).reduceByKey((x,y)=>{x+y}).foreach(result=>println(result._1+","+result._2))

  }
}
