# [Hudi](https://eng.uber.com/apache-hudi/)

- 介绍一则新闻，uber向Apache 开源了一个大数据库

  -  petabytes of data   pb级别的数据

  - was built to address  被用于解决

  - ingest   摄取

  - inefficiencies  效率低下

  - Incubator,  孵化器,

  - furthering       adv. 进一步地；而且；更远地adj. 更远的；深一层的vt. 促进，助长；增进

  - evolve   vi. 发展，进展；进化；逐步形成vt. 发展；进化；使逐步形成；推断出

  - in collaboration with   与…勾结；与…合作

  -  a diverse set of   不同的

  - columnar  adj. 柱状的；圆柱的；分纵栏印刷或书写的

  - testament  n. [法] 遗嘱；圣约；确实的证明

  - warehouse   n. 仓库；货栈；大商店vt. 储入仓库；以他人名义购进（股票）

  - 

    ​	



​	管理与快速访问pb级别的表对于整个数据系统的快速成长非常重要。速度与规模的相结合不总是适用于已存在的批处理与流式架构中。

​	代码名为”Hoodie “，从2016年开始开发。Hudi  被用于解决摄取数据与某些ETL  管道无效的问题，ETL  管道要求在Uber’的大数据系统中[upsert （一种操作术语）](https://blog.csdn.net/abfunnyboy/article/details/52002123) 并且增量消费原始数据，为了更广泛的与大数据社区分享这些好处，Uber  在2017年开源Hudi  。

​	2019年1月，我们将Hudi  项目提交到Apache孵化器,为了更好的履行我们开源的承诺，确保在Apache Software Foundation开源管理与组织下长期的可持续发展与成长。

​	Uber从许多很棒的Apache 项目中受益，我们相信Apache 这种社区驱动的方式，开源的发展将让我们与各种各样各样的的社区贡献者一起促进Apache Hudi 的成长。我们期待和Apache Software Foundation 的合作促使工程的最佳实践，并且带给工程一些新的想法。

​	过去一段时间，在大数据开源社区的帮助下，Hudi  已经在一些大数据存储系统中有所贡献，它能够

- 在摄取与查询引擎中实现快照隔离，包括Apache Hive, Presto, and Apache Spark 

- 支持回滚与节点保存用于恢复数据

- 自动管理文件的容量与布局，来优化查询性能与目录显示

- 在刷新数据方面，近实时摄取与提供数据

- 实时与柱状数据的异步压缩

  为了证明其扩展性，Hudi  管理了超过4,000  张表，存储了Uber pB级别的数据。Hudi  在使Apache Hadoop 仓库的访问从几个小时降低到30分钟以下的同时，Hudi  也比以前公司使用的解决方案，用很低的消耗支持着数百张增量数据通道。

  ​	