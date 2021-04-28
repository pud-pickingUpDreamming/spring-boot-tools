# spring-boot-tools
**记录常用工具类、常用对象模型、spring boot集成单个技术点服务**

## common 模块
**记录并不断的完善通用的工具类和不依赖于web的通用对象模型,为其它模块提供工具类支持**
**为了保持依赖与spring boot管理包版本一致,需要依赖spring-boot-starter-parent,
部分未被spring管理的包才用spring-boot-tools pom来管理,
如果单独使用的话只需要从spring-boot-starter-parent pom中找出对应依赖,把版本号拷过来就行了**
  
### 工具类
- 签名工具类
- AES256加密工具类
- 文件处理工具类
- Json工具类
- 执行操作系统命令工具类
- xml处理工具类
### 通用对象设计
- 系统异常类设计

## common-web
**与web 相关的通用功能及通用工具类和模型**

### 工具类
- rest接口响应数据封装工具类

### 通用对象
- 分页对象
- 业务异常对象
- 业务异常编码枚举

### 通用功能
- 日志拦截
- 权限校验

## webservice 模块
功能点:
- 通过cxf 优雅的实现soap接口客户端
- 通过cxf 优雅的实现soap接口服务端