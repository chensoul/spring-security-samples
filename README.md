![video_spider](https://socialify.git.ci/chensoul/learn-spring-security/image?forks=1&issues=1&language=1&name=1&owner=1&stargazers=1&theme=Light)

# <font size="6p">learn-spring-security</font> <font size="5p">  | [English Documentation](README.md)</font>

<p align="left">
	<a href="https://github.com/chensoul/learn-spring-security/stargazers"><img src="https://img.shields.io/github/stars/chensoul/learn-spring-security?style=flat-square&logo=GitHub"></a>
	<a href="https://github.com/chensoul/learn-spring-security/network/members"><img src="https://img.shields.io/github/forks/chensoul/learn-spring-security?style=flat-square&logo=GitHub"></a>
	<a href="https://github.com/chensoul/learn-spring-security/watchers"><img src="https://img.shields.io/github/watchers/chensoul/learn-spring-security?style=flat-square&logo=GitHub"></a>
	<a href="https://github.com/chensoul/learn-spring-security/issues"><img src="https://img.shields.io/github/issues/chensoul/learn-spring-security.svg?style=flat-square&logo=GitHub"></a>
	<a href="https://github.com/chensoul/learn-spring-security/blob/main/LICENSE"><img src="https://img.shields.io/github/license/chensoul/learn-spring-security.svg?style=flat-square"></a>
</p>

基于 Maven 构建的 Spring Security 示例。

[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://codespaces.new/chensoul/learn-spring-security)

## 模块说明

- security-lesson01: 一个 restful 应用
- security-lesson02: 配置 inMemoryAuthentication
- security-lesson03：配置受限的请求路径
- security-lesson04：自定义登录
- security-lesson05：自定义登出
- security-lesson06：待定
- security-lesson07：待定
- security-lesson08：配置 UserDetailsService
- security-lesson09：配置 rememberMe
- security-lesson10：配置 rememberMe
- security-lesson11：EL
- security-lesson12：DaoAuthenticationProvider
- security-lesson13：RunAsManager
- security-lesson14：自定义 AuthenticationProvider
- security-lesson15：session
- security-lesson16：AccessDecisionManager
- security-lesson17：MFA
- security-lesson18：MFA

## 如何运行

### 生成证书

JRE 提供了一个简单的证书管理工具——keytool。它位于您的JRE_HOME\bin目录下。以下代码中的命令生成一个自签名证书并将其放入
PKCS12 KeyStore 中。除了 KeyStore 的类型之外，您还需要设置其有效期、别名以及文件名。在开始生成过程之前，keytool会要求您输入密码和一些其他信息，如下所示：

```bash
keytool -genkeypair -alias mytest -keyalg RSA -keysize 2048 \
    -storetype PKCS12 -keystore mytest.p12 -storepass mypass \
    -dname "CN=WebServer,OU=Unit,O=Organization,L=City,S=State,C=CN" -validity 3650
```

导出公钥文件：

```bash
keytool -list -rfc --keystore mytest.p12 -storepass mypass | \
    openssl x509 -inform pem -pubkey > public.key
```

导出私钥文件：

```bash
keytool -importkeystore -srckeystore mytest.p12 -srcstorepass mypass \
    -destkeystore private.p12 -deststoretype PKCS12 \
    -deststorepass mypass -destkeypass mytest

#输入 storepass 密码 
openssl pkcs12 -in private.p12 -nodes -nocerts -out private.key
```

## 参考

- https://github.com/spring-attic/spring-security-oauth2-boot/tree/main/samples
- https://github.com/chensoul/spring-security-oauth2-boot-examples
- https://github.com/eugenp/learn-spring-security/tree/lsso-module5/logout-with-oauth-and-oidc-end
- https://github.com/OpenDocCN/freelearn-javaweb-zh/blob/f12853ff9d621f45414c5f67af4c8d1d04484f1a/docs/ms-sprcld/ms-sprcld_12.md

## 贡献

非常欢迎[提出请求](https://help.github.com/articles/creating-a-pull-request) 。

## 许可

learn-spring-authorization-server 是在 [Apache 2.0 许可](https://www.apache.org/licenses/LICENSE-2.0.html)
下发布的开源软件 。

