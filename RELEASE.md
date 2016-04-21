#Release Note
##TODO
- [ ] App支持导入导出。
- [X] App支持通用配置，作用域为全部子项目。
- [ ] 全局查询支持查找config（目前只查App）。
- [ ] 支持打包成war。

##v1.0.1

### 增加对公共配置的支持
通常各个子系统之间会有部分配置项是相同的。若不做处理，这些相同的配置项就需要维护多份，繁琐且容易产生手工失误。公共配置就为了解决这个问题，公共配置把这些分散于各个子系统的相同的配置项提取成独立对象单独维护，公共配置更新时会更新全部添加了监听的client端。

> WithCommonConfLabInit ，支持公共配置的系统初始化方案。

##v1.0.0
第一个稳定版,功能如介绍中描述
1. 服务端
 全局检索，地址栏url查询（方便收藏）。
 app 新建、编辑、复制、删除、推送（更新ZK）、全部推送。
 group 新建、编辑、删除
 config 新建、编辑、删除

2. 客户端
- 开发模式支持按照环境变量或用户目录下的`Conflab.properties` 文件配置客户端。
- 生产环境使用直接加载方式。