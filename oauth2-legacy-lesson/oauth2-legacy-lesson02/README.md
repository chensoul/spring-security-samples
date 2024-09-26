# oauth2-legacy-lesson02

基于 spring-security-oauth2-autoconfigure 最小化配置实现 OAuth2 认证授权服务。

## 源码说明

### OAuth2AutoConfiguration

添加下面依赖之后，Spring Boot 会自动装配 OAuth2AutoConfiguration 类：

```xml
<dependency>
    <groupId>org.springframework.security.oauth.boot</groupId>
    <artifactId>spring-security-oauth2-autoconfigure</artifactId>
</dependency>
```

OAuth2AutoConfiguration 类：

```java
@Configuration
@ConditionalOnClass({ OAuth2AccessToken.class, WebMvcConfigurer.class })
@Import({ OAuth2AuthorizationServerConfiguration.class, OAuth2MethodSecurityExpressionHandlerConfiguration.class,
		OAuth2ResourceServerConfiguration.class, OAuth2RestOperationsConfiguration.class })
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties({ OAuth2ClientProperties.class, ClientProperties.class })
public class OAuth2AutoConfiguration {

	private final ClientProperties credentials;

	public OAuth2AutoConfiguration(ClientProperties credentials) {
		this.credentials = credentials;
	}

	@Bean
	public ResourceServerProperties resourceServerProperties() {
		return new ResourceServerProperties(this.credentials.getClientId(), this.credentials.getClientSecret());
	}
}
```

- OAuth2AutoConfiguration 类装配条件： 存在 OAuth2AccessToken 和 WebMvcConfigurer 类
- 在 WebMvcAutoConfiguration 之前装配
- 启用 OAuth2ClientProperties 和 ClientProperties 配置属性：
    - security.oauth2.client.client-id
    - security.oauth2.client.client-secret
- 自动装配以下类：
    - OAuth2AuthorizationServerConfiguration：OAuth2 授权服务配置
        - 装配条件：
            - 存在 AuthorizationServerEndpointsConfiguration Bean
                - 该 Bean 用于配置授权服务端点，依赖 AuthorizationServerEndpointsConfigurer 和
                  AuthorizationServerConfigurer 类
                - 该 Bean 由 EnableAuthorizationServer 注解导入
            - 存在 EnableAuthorizationServer 类
        - 导入 AuthorizationServerTokenServicesConfiguration，该 Bean 用于配置授权服务端点的 Token 服务，包括
          TokenStore、TokenConverter、TokenServices 等
            - 该 Bean 由 EnableAuthorizationServer 注解导入
      - 内部类：
        - AuthorizationSecurityConfigurer：根据配置自动装配一个默认的 AuthorizationServerConfigurer，供
          OAuth2AuthorizationServerConfiguration 使用
            - 依赖注入 BaseClientDetails、AuthenticationManager、TokenStore、AccessTokenConverter
        - ClientDetailsLogger
        - BaseClientDetailsConfiguration：装配 BaseClientDetails
    - OAuth2MethodSecurityExpressionHandlerConfiguration：OAuth2 方法安全表达式处理器配置
        - 装配条件，存在 GlobalMethodSecurityConfiguration Bean
    - OAuth2ResourceServerConfiguration：OAuth2 资源服务配置
        - 装配条件：
            - ResourceServerCondition
            - web 环境
            - 存在 EnableResourceServer、SecurityProperties 类
            - 存在 ResourceServerConfiguration Bean
                - 该 Bean 由 EnableResourceServer 注解导入
                - 该类继承 WebSecurityConfigurerAdapter 类
        - 导入 ResourceServerTokenServicesConfiguration
            - 该 Bean 用于配置资源服务端点的 Token 服务，包括 TokenStore、TokenConverter、TokenServices 等
            - 装配条件：不存在 AuthorizationServerEndpointsConfiguration Bean
    - OAuth2RestOperationsConfiguration：OAuth2 REST 操作配置
        - 装配条件：存在 EnableOAuth2Client 类
        - 内部装配以下三个类：
            - SingletonScopedConfiguration
                - ClientCredentialsResourceDetails
                - DefaultOAuth2ClientContext
            - SessionScopedConfiguration
                - OAuth2ClientContextFilter
            - RequestScopedConfiguration
                - DefaultOAuth2ClientContext

### 授权服务器启动过程

1. 自动装配 OAuth2AutoConfiguration
2. 自动装配 OAuth2AuthorizationServerConfiguration
3. AuthorizationSecurityConfigurer 自动装配一个默认的 AuthorizationServerConfigurer，供 OAuth2AuthorizationServerConfiguration 使用
   - BaseClientDetailsConfiguration 自动装配 BaseClientDetails
   - 创建一个 AuthorizationSecurityConfigurer Bean，通过 ClientDetailsServiceConfigurer 配置 InMemoryClientDetailsServiceBuilder；通过 AuthorizationServerEndpointsConfigurer 设置端点需要的 TokenStore 和 AccessTokenConverter；如果授权类型包括 password，则设置 AuthenticationManager；通过 AuthorizationServerSecurityConfigurer 设置 Security 配置，包括 PasswordEncoder 以及 checkTokenAccess、tokenKeyAccess 访问权限
4. 自动装配 ClientDetailsServiceConfiguration，创建一个 ClientDetailsServiceBuilder来 初始化一个 ClientDetailsServiceConfigurer
5. AuthorizationServerEndpointsConfiguration init 方法通过 AuthorizationServerConfigurer 配置 AuthorizationServerEndpointsConfigurer；AuthorizationServerEndpointsConfigurer 设置 clientDetailsService
6. 自动装配 AuthorizationServerSecurityConfiguration
   - 通过 configure(ClientDetailsServiceConfigurer clientDetails) 方法配置 AuthorizationServerConfigurer，AuthorizationServerConfigurer 类里从配置文件中读取 client-id 和 client-secret 创建了一个 InMemoryClientDetailsServiceBuilder 并设置到了 ClientDetailsServiceConfigurer 中
7. AuthorizationServerTokenServicesConfiguration：当使用 jwt token 时配置
8. OAuth2MethodSecurityExpressionHandlerConfiguration

WebSecurityConfigurerAdapter

DefaultSecurityFilterChain 可以看到过滤器链的顺序：

* DisableEncodeUrlFilter
* WebAsyncManagerIntegrationFilter
* SecurityContextPersistenceFilter
* HeaderWriterFilter
* LogoutFilter
* OAuth2AuthenticationProcessingFilter
* RequestCacheAwareFilter
* SecurityContextHolderAwareRequestFilter
* AnonymousAuthenticationFilter
* SessionManagementFilter
* ExceptionTranslationFilter
* FilterSecurityInterceptor
