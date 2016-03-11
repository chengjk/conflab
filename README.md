#Conflab


当前版本v1.0.0,详细见[RELEASE.md][1]。


Conflab，一个集群式配置中心，实现了业务系统配置项的统一管理。配置项存于`zookeeper`，更新时消息通知对应客户端。客户端需要设置ZK地址，我们把它写入环境变量即可做到零配置。

服务端conflab-sever管理配置项并推送更新zookeeper，业务系统则通过客户端conflab-client端从zookeeper上获取和接收更新消息。演示模块conflab-demo是业务系统使用client的一个示例。

## Getting start
- 配置zookeeper环境变量

```shell
ZK_ADDRESS=127.0.0.1:2181
```

- 编译工程

```shell
mvn clean package -DskipTests=true
```

client端编译结果个jar包。server端编译完是一个可以解压运行的zip，目录：

```tree
.
├── application.properties
├── bin
│   ├── start.cmd
│   └── start.sh
├── conflab-server-1.0-SNAPSHOT.jar
├── conflab-server-1.0-SNAPSHOT-sources.jar
├── lib
└── static
    ├── css
    ├── index.html
    ├── js
    ├── lib
    ├── package.json
    └── temp
```

- 启动服务端
`bin/start.sh` or `bin/start.cmd`。
- 访问服务端主页
[http://localhost:8080/index.html][2]
- 增删改查推送
通过界面增加配置并推送。

> 测试用url：/t/save

##结构图

![`系统结构图`](./assets/chart.jpg)

## 服务端 conflab-server




###启动
bin目录下有start.sh供linux下启动，start.cmd则在windows下启动。启动之后访问[http://localhost:8080/index.html][2]就进入了服务端的管理页面。

> 主页后增加url参数key`?key=my`等同于在主页搜索框中搜索关键字‘my’。

这类连接可以作为常用收藏到浏览器，访问就可以实现关键字检索，再也不用被不相关的内容干扰了。e.g.团队中没人都有自己的配置，以自己名字作为关键字就是很好的选择。

###配置
因为配置数据都在中，需要在主机上配置zookeeper的环境变量，每个客户端也需要配置相同的环境变量。
系统常用的配置项在根目录application.properties文件中。这里可以修改端口，数据库类型和数据库连接信息。

>注意:使用默认数据库SQLite，当spring.jpa.hibernate.ddl-auto=`update` 时启动会报错，因此默认配置是`create`,也就是每次启动都会重新创建新表。建议首次启动表生成之后把这个配置注释掉。这个问题后续版本修改。


###工程介绍
工程前后端分离，前端是最常见的html+js静态页面，毕竟是小工具，越简单越好。后端使用spring boot,spring mvc,spring data jpa，maven打包。数据库可用SpringDataJpa支持的所有类型，提供了mysql和SQLite配置，大型项目建议用mysql。默认使用SQLite，不用做任何配置。简单。

系统实现了对配置内容分应用分组管理。支持多系统，系统内按业务分组。以应用为单位更新到zookeeper，与该应用相关的客户端会都收到更新消息。

## 客户端 conflab-client
业务系统中这样就可以得到想要配置：
```java
String value=Conflab.getString("key");
```
客户端有一个类用来监听Zookeeper的更新事件，默认实现了更新本地配置缓存。其他业务需要可以在回调函数里实现。

客户端对app（对应zookeeper目录）的监听有两种模式：开发模式和生产模式。

- 生产模式比较简单，监听对象固定不变，使用常量即可，例如`server`。
- 团队开发模式中，源码都是一样的，每个人却需要各自的配置，配置不能加入版本管理。需要在主机的`CONFLAB_HOME`目录或用户目录下`conflab.properties`文件中配置一个或多个对应，如：
    
    ```code
    server=jacky-server
    demo=jacky-demo
    ```
    这样开发环境下server使用jacky-server配置，demo同理。

客户端只是一个jar包，引入到业务系统中即可。使用办法可以参考范例conflab-demo。client也是特别轻，只依赖了logback和zk客户端。简单。


  [1]: ./RELEASE.md
  [2]: http://localhost:8080/index.html
