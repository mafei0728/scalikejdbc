/**
 * @Author: mafei0728
 * @Date: 2021/4/11 21:31
 * @Description:
 */

package cn.mafei.scalike.base.connect

import scalikejdbc.{AutoSession, ConnectionPool, SQL, scalikejdbcSQLInterpolationImplicitDef}

import java.time.{LocalDate, LocalDateTime}

object ExampleOne {
  // 加载驱动
  Class.forName("com.mysql.jdbc.Driver")
  val url: String = "jdbc:mysql://hadoop01:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false"
  ConnectionPool.singleton(url, "root", "mafei0728")
  // 隐式
  implicit val session = AutoSession

  def runDDl: Unit = {
    sql"""
         create database if not exists test
       """.execute.apply()

    sql"""
         create table if not exists test01  (
         id int,
         name varchar(8),
         date timestamp,
         per_day date)
       """.execute.apply()
  }

  def insetData: Unit = {
    val lz: Seq[(Int, String)] = Seq((1, "a"), (2, "b"), (3, "c"))
    lz foreach { lx =>
      sql"""
        insert into test01 values (${lx._1}, ${lx._2}, current_timestamp, current_date )
         """.update.apply()
    }
    // 这样用是最好的
    val tableName = "test01"
    lz foreach { lx =>
      SQL(s"insert into $tableName values (?, ?,?,?)")
        .bind(lx._1, lx._2, LocalDateTime.now(), LocalDate.now())
        .update
        .apply()
    }
  }

  def queryDataToMap: Unit = {
    sql"select * from test01".map(_.toMap()).list.apply().foreach(println)
  }

  def main(args: Array[String]): Unit = {
    insetData
  }
}
