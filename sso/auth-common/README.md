# auth-common

### 项目功能
  1. 提供 dubbo 接口定义
  2. service 和 web 共用的相关 POJO

### 分层领域模型规约
  - DO（Data Object）：与数据库表结构一一对应，通过DAO层向上传输数据源对象。
  - DTO（Data Transfer Object）：数据传输对象，Service或Manager向外传输的对象。
  - BO（Business Object）：业务对象。由Service层输出的封装业务逻辑的对象。
  - AO（Application Object）：应用对象。在Web层与Service层之间抽象的复用对象模型，极为贴近展示层，复用度不高。
  - VO（View Object）：显示层对象，通常是Web向模板渲染引擎层传输的对象。
  - Query：数据查询对象，各层接收上层的查询请求。注意超过2个参数的查询封装，禁止使用Map类来传输。

### 命名规约
#### A) Service/DAO层方法命名规约
        1） 获取单个对象的方法用get作前缀。
        2） 获取多个对象的方法用list作前缀。
        3） 获取统计值的方法用count作前缀。
        4） 插入的方法用save/insert作前缀。
        5） 删除的方法用remove/delete作前缀。
        6） 修改的方法用update作前缀。
#### B) 领域模型命名规约
        1） entity目录对象与数据库表结构一一对应。(区别于阿里手册，不以DO结尾)
        2） dto/bo/vo目录对象都分别以DTO/BO/VO结尾，如xxxVO
        3） POJO是DO/DTO/BO/VO的统称，禁止命名成xxxPOJO。

### 详细规则参考：[https://github.com/alibaba/p3c](https://github.com/alibaba/p3c "《阿里巴巴Java开发手册》")


<font color=gray size=4>*说明：POJO（Plain Ordinary Java Object），专指只有setter / getter / toString的简单类，包括DO/DTO/BO/VO等*</font>
