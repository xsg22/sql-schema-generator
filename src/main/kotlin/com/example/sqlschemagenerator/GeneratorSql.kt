package com.example.sqlschemagenerator

import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.DatabaseChangeLog
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.parser.ChangeLogParser
import liquibase.parser.ChangeLogParserFactory
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.sqlgenerator.SqlGeneratorFactory

fun main(args: Array<String>) {
    val generatorSqlInstance = GeneratorSql()
    for (databaseName in listOf<String>("mysql", "oracle")) {
        val sqls = generatorSqlInstance.generatorSql("db/changelog/example-changelog.json", databaseName)
        println("----$databaseName----")
        for (sql in sqls) {
            println("$sql;")
        }
        println()
    }
}

class GeneratorSql {

    fun generatorSql(changeLogFile: String, databaseName: String):List<String> {
        // 获取数据库类型
        val database = DatabaseFactory.getInstance().getDatabase(databaseName)
        val databaseChangeLog = getDatabaseChangeLog(changeLogFile, database)
        val sqlList = arrayListOf<String>()
        for (changeSet in databaseChangeLog.changeSets) {
            for (change in changeSet.changes) {
                // 通过change生成对应数据库的SQL
                val sqls = SqlGeneratorFactory.getInstance().generateSql(change, database)
                for (sql in sqls) {
                    sqlList.add(sql.toSql())
                }
            }
        }
        return sqlList
    }

    /**
     * 获取ChangeLog
     */
    fun getDatabaseChangeLog(changeLogFile: String, database: Database): DatabaseChangeLog {
        val changeLogParameters = ChangeLogParameters(database)
        val resourceAccessor = ClassLoaderResourceAccessor()
        val parser: ChangeLogParser = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor)
        return parser.parse(changeLogFile, changeLogParameters, resourceAccessor)
    }
}

