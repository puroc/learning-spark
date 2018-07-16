package com.example.scala

object Test1 {

  def main(args: Array[String]): Unit = {

    //可变map
    //    val map = scala.collection.mutable.Map[String,String]()
    //    map("name")="pud"
    //    map("age")="18"
    //    map("sex")="1"
    //    for (elem <- map) {
    //      println("1:"+elem._1)
    //      println("2:"+elem._2)
    //    }
    //
    //数组
    //    val a = Array(1,2,3,4)
    //    for (elem <- a) {
    //      println(elem)
    //    }

    //列表
    val b = List(1, 2, 3, 4, 5, 6)
    val b2 = b.::(0)
    for (elem <- b2) {
      println(elem)
    }


    //元组
    //    val c =(1,2,3,4,5)
    //    for (elem <- c.productIterator) {
    //      println(elem)
    //    }

  }

}
