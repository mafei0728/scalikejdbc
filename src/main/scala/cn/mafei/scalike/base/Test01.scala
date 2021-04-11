/**
 * @Author: mafei0728
 * @Date: 2021/4/9 23:33
 * @Description:
 */

package cn.mafei.scalike.base

import scalikejdbc._

import java.sql.{Date, Timestamp}

case class Test01(id: Option[Int], name: Option[String], date: Option[Timestamp], per_day: Option[Date])

object Test01 extends SQLSyntaxSupport[Test01] {
  def apply(o:SyntaxProvider[Test01])(rs:WrappedResultSet):Test01= apply(o.resultName)(rs)
  def apply(o:ResultName[Test01])(rs:WrappedResultSet):Test01={
    new Test01(rs.intOpt(o.id), rs.stringOpt(o.name), rs.timestampOpt(o.date), rs.dateOpt(o.per_day))
  }
}
