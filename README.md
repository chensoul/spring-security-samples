# learn-spring-security

基于 Maven 构建的 Spring Security 和 Spring Security OAuth2 示例。

## 模块说明

- lssc-lesson01: 一个 restful 应用
- lssc-lesson02: 配置 inMemoryAuthentication
- lssc-lesson03：配置受限的请求路径
- lssc-lesson04：自定义登录页面
- lssc-lesson05：自定义登出
- lssc-lesson06：待定
- lssc-lesson07：待定
- lssc-lesson08：配置 UserDetailsService
- lssc-lesson09：配置 rememberMe
- lssc-lesson10：配置 rememberMe
- lssc-lesson11：EL
- lssc-lesson12：DaoAuthenticationProvider
- lssc-lesson13：RunAsManager
- lssc-lesson14：自定义 AuthenticationProvider
- lssc-lesson15：session
- lssc-lesson16：AccessDecisionManager
- lssc-lesson17：MFA
- lssc-lesson18：MFA

- oauth2-legacy-lesson01
    - ✅ spring-boot-sample-secure-oauth2-provider
    - ✅ spring-boot-sample-secure-oauth2-resource
    - ✅ spring-boot-sample-secure-oauth2-provider-authentication-manager
    - ✅ spring-boot-sample-secure-oauth2-provider-jwt
    - ✅ spring-boot-sample-secure-oauth2-resource-jwt
    - ✅ spring-boot-sample-secure-oauth2-actuator
    - ✅ spring-boot-sample-secure-oauth2
    - ✅ spring-boot-sample-secure-github
- oauth2-legacy-lesson03
    - ✅ legacy-jwk--auth-server
    - ✅ legacy-jwk--resource-server
    - ❌ legacy-jwk--client
    - ✅ legacy-jwk-new--resource-server
    - ✅ legacy-jwk-new--client
- oauth2-legacy-lesson04
    - ✅ legacy-jwt--auth-server
    - ✅ legacy-jwt--resource-server
    - ❌ legacy-jwt--client
    - ✅ legacy-jwt-new--resource-server
    - ✅ legacy-jwt-new--client

## 说明

### spring-security-oauth2-autoconfigure

### spring-boot-starter-oauth2-authorization-server

### spring-boot-starter-oauth2-resource-server

### spring-boot-starter-oauth2-client

## 附录

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

## 参考文章

- https://github.com/spring-attic/spring-security-oauth2-boot/tree/main/samples
- https://github.com/chensoul/spring-security-oauth2-boot-examples
- https://github.com/eugenp/learn-spring-security/tree/lsso-module5/logout-with-oauth-and-oidc-end
- https://github.com/OpenDocCN/freelearn-javaweb-zh/blob/f12853ff9d621f45414c5f67af4c8d1d04484f1a/docs/ms-sprcld/ms-sprcld_12.md
