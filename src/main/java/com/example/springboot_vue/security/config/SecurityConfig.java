//package com.example.springboot_vue.security.config;
//
//import com.example.springboot_vue.pojo.verify.ResultVO;
//import com.example.springboot_vue.security.filter.LoginFilter;
//import com.example.springboot_vue.security.filter.MyRememberMeAuthenticationFilter;
//import com.example.springboot_vue.security.manager.CustomFilterInvocationSecurityMetadataSource;
//import com.example.springboot_vue.security.manager.CustomUrlDecisionManager;
//import com.example.springboot_vue.security.pojo.JwtUserDetails;
//import com.example.springboot_vue.security.service.MyPersistentTokenBasedRememberMeServices;
//import com.example.springboot_vue.security.service.MyUserDetailService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.*;
//import org.springframework.security.config.annotation.ObjectPostProcessor;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//import org.springframework.security.web.authentication.RememberMeServices;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
//import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
//import org.springframework.security.web.session.ConcurrentSessionFilter;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//import java.io.PrintWriter;
//
//@Configuration
//@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
//// 开启Spring Security的功能
//@EnableWebSecurity
//// 决定Spring Security在接口前注解是否可用@PreAuthorize,@PostAuthorize等注解,设置为true,会拦截加了这些注解的接口
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    CustomUrlDecisionManager customUrlDecisionManager;
//
//    @Autowired
//    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
//
//    @Autowired
//    MyUserDetailService myUserDetailService;
//
//    @Autowired
//    DataSource dataSource;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////        // 设置不隐藏 未找到用户异常
////        provider.setHideUserNotFoundExceptions(true);
////        // 用户认证service - 查询数据库的逻辑
////        provider.setUserDetailsService(myUserDetailService);
////        // 设置密码加密算法
////        provider.setPasswordEncoder(passwordEncoder());
////        auth.authenticationProvider(provider);
//        auth.userDetailsService(myUserDetailService);
//    }
//
//    @Override
//    public void configure(WebSecurity web) {
//        System.out.println("进入WebSecurity");
//        // 忽略权限授权接口,填写路径就好 这里不要放接口，一般放静态资源
//        // 这里放行的接口是不需要登录就可以访问的
////        web.ignoring().antMatchers("/crudInterface/*");
////        web.ignoring().antMatchers("/search/*");
//        web.ignoring().antMatchers("/user/*", "/crudInterface/*");
//        web.ignoring().antMatchers("/file/*", "/file/*");
//    }
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        /**
////         * 基于内存的方式，创建两个用户admin/123456，user/123456
////         * */
////        auth.inMemoryAuthentication()
////                .withUser("admin")//用户名
////                .password(passwordEncoder().encode("123456"))//密码
////                .roles("ADMIN");//角色
////        auth.inMemoryAuthentication()
////                .withUser("user")//用户名
////                .password(passwordEncoder().encode("123456"))//密码
////                .roles("USER");//角色
////    }
//
//    /**
//     * 指定加密方式
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // 使用BCrypt加密密码
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        // 配置数据源，用于向数据库中存储数据
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
////        tokenRepository.setCreateTableOnStartup(true);
//        return tokenRepository;
//    }
//
//    @Bean
//    public RememberMeServices rememberMeServices() {
////        MyRememberService rememberMeServices = new MyRememberService ();
//        MyPersistentTokenBasedRememberMeServices rememberMeServices = new MyPersistentTokenBasedRememberMeServices("INTERNAL_SECRET_KEY", myUserDetailService, persistentTokenRepository());
//        rememberMeServices.setParameter("rememberMe"); // 修改默认参数remember-me为rememberMe和前端请求中的key要一致
//        rememberMeServices.setTokenValiditySeconds(3600 * 24 * 7); //token有效期7天
//        return rememberMeServices;
//    }
//
//    @Bean
//    public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
//        // 重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
//        return new RememberMeAuthenticationFilter(authenticationManager(), rememberMeServices());
//    }
//
//    @Bean
//    SessionRegistryImpl sessionRegistry() {
//        return new SessionRegistryImpl();
//    }
//
//    @Bean
//    LoginFilter loginFilter() throws Exception {
//        LoginFilter loginFilter = new LoginFilter();
//        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
//
//            System.out.println(request.getRequestedSessionId());
//
//            response.setContentType("application/json;charset=utf-8");
//            PrintWriter out = response.getWriter();
//
//            // 在这里给前端返回数据
//            JwtUserDetails realUser = (JwtUserDetails) authentication.getPrincipal();
//            ResultVO<JwtUserDetails> ok = new ResultVO<>(realUser);
//            String s = new ObjectMapper().writeValueAsString(ok);
//            out.write(s);
//            out.flush();
//            out.close();
//        });
//
//        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
//            response.setContentType("application/json;charset=utf-8");
//            PrintWriter out = response.getWriter();
//            ResultVO respBean = ResultVO.fail(exception.getMessage());
//            if (exception instanceof LockedException) {
//                respBean.setMsg("账户被锁定，请联系管理员!");
//            } else if (exception instanceof CredentialsExpiredException) {
//                respBean.setMsg("密码过期，请联系管理员!");
//            } else if (exception instanceof AccountExpiredException) {
//                respBean.setMsg("账户过期，请联系管理员!");
//            } else if (exception instanceof DisabledException) {
//                respBean.setMsg("账户被禁用，请联系管理员!");
//            } else if (exception instanceof BadCredentialsException) {
//                respBean.setMsg("用户名或者密码输入错误，请重新输入!");
//            }
//            out.write(new ObjectMapper().writeValueAsString(respBean));
//            out.flush();
//            out.close();
//        });
//
////        authenticationManagerBean()
//        loginFilter.setAuthenticationManager(authenticationManager());
//        // 之前这里没写
//        loginFilter.setRememberMeServices(rememberMeServices());
//        // 这里是设置一个表示，与这里相符就按登录接口处理,注意它并不会跳转到这个接口，只是拦截了路径，然后跳转到在这里写的登录逻辑。
//        loginFilter.setFilterProcessesUrl("/user/login");
//        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
//        // 只能一个账号登录
//        sessionStrategy.setMaximumSessions(1);
//        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
//        return loginFilter;
//    }
//
//    @Bean
//    MyRememberMeAuthenticationFilter myRememberMeAuthenticationFilter() throws Exception {
//        MyRememberMeAuthenticationFilter o = new MyRememberMeAuthenticationFilter(
//                authenticationManagerBean(),
//                new MyPersistentTokenBasedRememberMeServices("INTERNAL_SECRET_KEY", myUserDetailService, persistentTokenRepository())
//        );
//        return o;
//    }
//
//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                // 配置不拦截路由
////                .antMatchers("/user/login").permitAll()
////                .antMatchers("/crudInterface/allCRUD").permitAll()
////                .antMatchers("/verify/test").permitAll()
////                .anyRequest() // 其他任何请求
////                .authenticated() // 都需要身份验证
////                .and()
////                // 表单认证方式
////                .formLogin()
//////                .loginPage("/login") // 自定义登录页面url
////                .usernameParameter("phone") // 设置登录账号参数，与表单一直
////                .passwordParameter("password") // 设置登录密码参数，与表单参数一致
////                .failureUrl("/")
////                // 注销
////                .and()
////                .logout()
////                .logoutUrl("/logout")
////                .permitAll()
////                .and()
////                //4、session管理
////                .sessionManagement()
////                .invalidSessionUrl("/login") //失效后跳转到登陆页面
////                .and()
////                //5、禁用跨站csrf攻击防御
////                .csrf()
////                .disable();
//        System.out.println("进入HttpSecurity");
//        http.authorizeRequests()
//                // 放行接口,这里放行的接口还是需要登录的
////                .antMatchers("/crudInterface/testUser").permitAll()
//                // 登录会走这里
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
//                        object.setAccessDecisionManager(customUrlDecisionManager);
//                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
//                        return object;
//                    }
//                })
//                .and()
//                .logout()
//                .logoutSuccessHandler((req, resp, authentication) -> {
//                            resp.setContentType("application/json;charset=utf-8");
//                            PrintWriter out = resp.getWriter();
//                            out.write(new ObjectMapper().writeValueAsString(ResultVO.success("注销成功!")));
//                            out.flush();
//                            out.close();
//                        }
//                )
//                .permitAll()
//                .and()
//                .csrf().disable().exceptionHandling()
//                // 没有认证时，在这里处理结果，不要重定向
//                .authenticationEntryPoint((req, resp, authException) -> {
//                    System.out.println("暂时没有认证，无法访问");
//                            resp.setContentType("application/json;charset=utf-8");
//                            resp.setStatus(401);
//                            PrintWriter out = resp.getWriter();
//                            ResultVO respBean = ResultVO.fail("访问失败!");
//                            if (authException instanceof InsufficientAuthenticationException) {
//                                respBean.setMsg("请求失败，请联系管理员!");
//                            }
//                            out.write(new ObjectMapper().writeValueAsString(respBean));
//                            out.flush();
//                            out.close();
//                        }
//                )
//                .and()
//                .rememberMe()
//                .key("INTERNAL_SECRET_KEY")
////                .tokenRepository(persistentTokenRepository())
////                .tokenValiditySeconds(3600)
////                .userDetailsService(myUserDetailService)
//                .and()
//                // 解决跨域
//                .cors();
//        // 添加登录拦截器
//        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        // 添加踢掉前一个用户的拦截器
//        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(), event -> {
//
//            System.out.println("在其他地方登录");
//            HttpServletResponse resp = event.getResponse();
//            resp.setContentType("application/json;charset=utf-8");
//            resp.setStatus(401);
//            PrintWriter out = resp.getWriter();
//            out.write(new ObjectMapper().writeValueAsString(ResultVO.fail("您已在另一台设备登录，本次登录已下线!")));
//            out.flush();
//            out.close();
//        }), ConcurrentSessionFilter.class);
//
//        // 添加remember-me的拦截器
//        http.addFilterAt(myRememberMeAuthenticationFilter(), RememberMeAuthenticationFilter.class);
//    }
//
//}
