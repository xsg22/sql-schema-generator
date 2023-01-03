# 自动生成不同数据库的DDL/DML
## 背景
业务项目在私有化部署的时候，支持了Mysql/Oracle等数据库。针对数据库的变更，每次都要为不同数据库写对应的脚步，工作量爆炸。
尝试找到一种办法能写一次脚本，自动生成对应数据库的变更脚本。

## 方案
经调研发现开源项目[liquibase](https://github.com/liquibase/liquibase)的generator模块支持多种数据库的SQL生成功能。
liquibase是一个schema管理工具，它定义了一种新的DSL来描述脚本变更的内容，再将DSL翻译成对应数据库的SQL。
我写了demo描述SQL生成的过程，具体DSL语法见liquibase的文档。

## 支持的数据库类型
除了主流的Mysql/Postgres/Oracle，一共支持38种数据库【[详情见文档](https://www.liquibase.com/supported-databases)】