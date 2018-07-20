package com.example.spark.rdd

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object RddOnCollection {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("RddOnCollection")
    conf.setMaster("local")
    val sc = new SparkContext(conf)
    val nums = 1.to(100)
    val rdd = sc.parallelize(nums)
    val sum = rdd.reduce(_+_)
    println(sum)
  }

}
