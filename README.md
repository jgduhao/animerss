## AnimeRss

### 简介

通过rss订阅获取动画剧集更新并且可以一键推送至Aria2下载的工具。简化回归下载时代后从bt站获取剧集更新与下载的流程。

后端基于java8+springboot2.5.0+h2数据库。

前端基于vue3+element plus，不是专门做前端的，~~就不搞工程化了，能用就行~~ 为了加载速度还是用了vue-cli，组件按需加载，也优化了手机页面的展示。

本来想加入分组的功能，但觉得花时间做了会耽误看动画的时间，所以仅保留了一部分代码和表结构。

如果想要从源代码用maven打包请跳过test，不保证测试用例在空白数据库上可以正常执行。

后续可能会考虑在这个基础上做一个正经的rss阅读器。

### 安装部署

建议使用docker部署，镜像已上传至dockerhub

下载镜像：

```bash
docker pull jgduhao/animerss:0.1
```

启动：

首先为配置文件，数据库文件和日志文件在宿主机建立目录：

例如：

配置文件目录：/var/animerss/config 

数据库文件目录：/var/animerss/db

日志文件目录：/var/animerss/log

然后执行命令：

```bash
docker run -p 8080:8080 \ 
-v /var/animerss/db/:/animerss/db \ 
-v /var/animerss/log/:/animerss/log/ \
-v /var/animerss/config/:/animerss/config/animerss/ \
--name animerss --restart=always -d jgduhao/animerss:0.1
```

其中配置文件目录可以不建立，不挂载，默认aria rpc地址为127.0.0.1:6800，无代理。

日志目录和数据库文件目录可以不建立，不在docker run命令手动挂载，让docker自动挂载。

精简启动命令：

```bash
docker run -p 8080:8080 --restart=always -d jgduhao/animerss:0.1
```

推荐至少挂载配置文件，方便灵活修改配置，修改配置文件后使用docker restart命令重启生效。

### 配置文件

可以按需准备配置文件，在运行docker run命令启动前放到配置文件目录，名称请确保固定为application.yml

```yaml
animerss:
  #网络代理地址和端口,如果不需要经过代理请把host字段留空
  proxy:
    host: 127.0.0.1
    port: 7890
    #网络代理认证的用户名密码信息，如果不需要认证请留空
    auth:
      user: abc
      secret: abcdefg
  #aria2rpc地址和密钥,aria2 rpcurl必须配置正确否则不能下载，如果使用ariaNg之类的图形界面可以直接把图形界面上的配置拿过来,如果没有配置密钥请把secret字段留空
  aria2:
    rpcurl: http://127.0.0.1:6800/
    secret: abcdefg
```

### 环境变量配置

可以通过环境变量来配置上述配置文件中的配置项：

ANIMERSS_PROXY_HOST 网络代理地址

ANIERSS_PROXY_PORT 网络代理端口

ANIMERSS_PROXY_AUTH_USER 网络代理认证用户名

ANIMERSS_PROXY_AUTH_SECRET 网络代理认证密码

ANIMERSS_ARIA2_RPCURL aria2rpc地址

ANIMERSS_ARIA2_SECRET aria2rpc密钥

在启动docker时也可以通过指定相应的环境变量来完成配置，而不需要额外的准备并挂载配置文件


