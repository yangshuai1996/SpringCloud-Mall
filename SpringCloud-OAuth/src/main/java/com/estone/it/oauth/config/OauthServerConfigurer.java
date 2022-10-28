package com.estone.it.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;


/**
 * @创建人 yangshuai
 * @创建时间 2022/10/26
 * @描述
 */
@Configuration
@EnableAuthorizationServer
public class OauthServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    private String sign_key = "lagou123"; // jwt签名密钥
    /**
     *
     * 认证服务器最终是以api接口的方式对外提供服务(校验合法性并生成令牌、校验令牌等)
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
        // 相当于打开endpoints 访问接口的开关，这样的话后期我们就能够访问该接口了
        // 允许客户端表单认证
        security.allowFormAuthenticationForClients()
                // 开启端口 /oauth/token_key 的访问权限（允许）
                .tokenKeyAccess("permitAll()")
                // 开启端口/oauth/check_token的访问权限（允许）
                .checkTokenAccess("permitAll()");
    }

    /**
     *
     * 客户端配置详情
     * 比如client_id,secret
     * 当前这个服务就如同QQ平台，拉勾网作为客户端需要在QQ平台进行登录授权认证等，提前需要到QQ平台注册，QQ平台会给拉勾网颁发client_id等必要参数，表明客户端是谁
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        // 从内存中加载客户端详情
        /*clients.inMemory() // 客户端信息存储在什么地方，可以在内存中，可以在数据库里
        .withClient("client_lagou") //添加一个client配置，指定其client_id
        .secret("abcxyz")                  // 指定客户端的密码/安全码
        .resourceIds("autodeliver")         // 指定客户端所能访问id清单，此处的资源id是需要在具体的资源服务器上也配置一样
        // 认证类型/令牌颁模式,可以配置多个在这里，但是不一定都用,具体使用哪种方式颁发token,需要根据客户端调用的时候传递参数指定
        .authorizedGrantTypes("password","refresh_token")
                //客户端的权限范围，此处配置为all全部即可
        .scopes("all");*/
        // 从数据库中加载客户端详情
        clients.withClientDetails(createJdbcClientDetailService());
    }

    @Bean
    public ClientDetailsService createJdbcClientDetailService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 认证服务器是玩转token的，那么这里配置token令牌管理相关（token此时就是一个字符串，当下的token需要在服务器端存储，
     * 那么存储在哪里呢？都是在这里配置）
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints.tokenStore(tokenStore()) // 指定token的存储方式
        .tokenServices(authorizationServerTokenServices()) // token 服务的一个描述，可以认为token生成细节，比如有效时间多少等
                .authenticationManager(authenticationManager) // 指定认证管理器，随后注入一个到当前类使用即可
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
    }

    /**
     * 该方法用于创建tokenStore对象(令牌存储对象)
     * @return
     */
    public TokenStore tokenStore(){
        /*return new InMemoryTokenStore();*/
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(sign_key);
        jwtAccessTokenConverter.setVerifier(new MacSigner(sign_key));
        return jwtAccessTokenConverter;
    }

    public AuthorizationServerTokenServices authorizationServerTokenServices(){
        // 使用默认实现
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setSupportRefreshToken(true); // 是否开启令牌刷新
        defaultTokenServices.setTokenStore(tokenStore());
        // 针对jwt令牌添加
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        // 设置令牌有效时间（一般设置为2个小时）
        defaultTokenServices.setAccessTokenValiditySeconds(60 * 60 * 2); //access_token就是我们请求资源需要携带的令牌
        // 设置刷新令牌的有效时间
        defaultTokenServices.setRefreshTokenValiditySeconds(259200); //3天
        return defaultTokenServices;
    }
}
