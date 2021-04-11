/**
 * @Author: mafei0728
 * @Date: 2021/4/11 21:36
 * @Description:
 */

package cn.mafei.scalike.base.connectionpool

import com.typesafe.config.{Config, ConfigFactory}
import scalikejdbc._

object ConnectionPoolAdd {
  // 添加一个数据源的连接池
  private val config: Config = ConfigFactory.load()
  val settings = ConnectionPoolSettings(
    initialSize = 5,
    maxSize = 20,
    connectionTimeoutMillis = 3000L,
    validationQuery = "select 1 from dual")

  ConnectionPool.singleton(config.getString("mysql01.default.url"),
    config.getString("mysql01.default.user"), config.getString("mysql01.default.password"), settings)
  //添加其他数据源连接
  // ConnectionPool.add('other, "xx", "xx", "xx")
  // 设置隐式值
  implicit val session = AutoSession
  // 如果是需要切换到其他的
  //  implicit val session = NamedAutoSession('other)
  // 这里使用默认方式
  // DB.readOnly { implicit session => sql"xxx" }
  // 或者这样使用其他数据源
  // NamedDB('other).readOnly { implicit session => sql"xxx" }

  def testSql: Unit ={
    sql"select * from test01".map(_.toMap()).list.apply().foreach(println)
  }

  def main(args: Array[String]): Unit = {
    testSql
  }
}
