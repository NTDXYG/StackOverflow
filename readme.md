说明：

1、resources下的application是配置文件，用于配置MySql的

stopwords下是常见的英文分词。

2、Dao下的dao是用于操作数据库，插入数据的。

3、domain是实体类，问题和答案。

4、lda/run/utilites是LDA算法的Java实现，在run/RunLDA里可以测试。

5、util里的FleissKappa是FleissKappa算法的Java实现；

HtmlUilts是基于Jsoup的html文档段的处理（去除code标签和a.href标签）；

XMLProcess是在本机运行localhost:8080/index处理xml文件的（已完成数据的部分插入，暂时不动）

