package vip.imagin.blast.config;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import vip.imagin.blast.filter.JwtAuthenticationTokenFilter;
import vip.imagin.blast.handler.AccessDeniedHandlerImpl;
import vip.imagin.blast.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zzhi
 * @version 1.0
 * @description TODO
 * @createDate 2022/3/1
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *     jwt的过滤器替换原来的那个页面
     */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //成功处理器
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    //失败处理器
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    //注入BCryptPasswordEncoder,用于数据库密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-ui/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/profile/**")
                .antMatchers("/profile/**")
                .antMatchers("/v3/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //.antMatchers("/hello").permitAll()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
                // 对于注册接口 允许匿名访问
                .antMatchers("/user/signin").anonymous()
                // 对于获取验证码接口 允许匿名访问
                .antMatchers("/user/captch").anonymous()
                .antMatchers("/user/info").anonymous()
                .antMatchers("/user/faceLogin").anonymous()
//                .antMatchers("/user/**").anonymous()
                .antMatchers("/material/test").anonymous()
                .antMatchers("/swagger-ui/**").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/v3/**").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
//                .anyRequest().anonymous();
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //允许跨越
        http.cors();

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
