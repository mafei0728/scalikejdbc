/**
 * @Author: mafei0728
 * @Date: 2021/4/11 22:08
 * @Description:
 */

package cn.mafei.scalike.base.connectionpool

import scalikejdbc._
import scalikejdbc.config._

object ViaConfig {
  // 通过配置文件获得,加载所有
  // DBs.setupAll()
  // 加载指定的
  DBs.setupAll()
  implicit val session = AutoSession

  def test01: Unit = {
   sql"select * from test01".map(_.toMap()).list.apply().foreach(println)
  }

  def test02: Unit = {
    implicit val session = NamedAutoSession('mysql01)
    sql"select * from test01".map(_.toMap()).list.apply().foreach(println)
  }

  def main(args: Array[String]): Unit = {
    test01
    test02
  }
}
