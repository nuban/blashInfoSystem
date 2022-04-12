package vip.imagin.blast.modules.user.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 这是一个登录的Userdetails返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private  User user;

    @JSONField(serialize = false) //表示这是一个不放入json的序列化的对象
    private List<GrantedAuthority> authorities;

    //封装权限信息
    private List<String> permissions;

    public MyUserDetails(User user, List<String> permission) {
        this.user = user;
        this.permissions = permission;
    }

    //实际上的权限信息是从这个方法中拿到的
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        for (String permission : permissions) {
//            authorities.add(new SimpleGrantedAuthority(permission));
//        }
        /**
         * stream流的方式封装
         */
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //token是不是过期了
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账号是不是被锁了
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账号是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}

///**
// * @author lingqu
// * @date 2022/3/1
// * @apiNote
// */
//
//import com.alibaba.fastjson.annotation.JSONField;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
///**
// * @author zzhi
// * @version 1.0
// * @description TODO
// * @createDate 2022/3/1
// */
//@Data
//@NoArgsConstructor
////@JsonIgnoreProperties(ignoreUnknown = true)
//public class MyUserDetails implements UserDetails{
//
//    private User user;
//    private List<String> permissions;
//
//
//
//    public MyUserDetails(User user, List<String> permissions) {
//        this.user = user;
//        this.permissions = permissions;
//    }
//
//    @JSONField(serialize = false) //表示这是一个不放入json的序列化的对象
//    private List<GrantedAuthority> permissionList;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        //将permissions的字符传入SimpleGrantedAuthority
//        permissionList = new ArrayList<>();
//        for (String permission : permissions) {
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
//            permissionList.add(simpleGrantedAuthority);
//        }
//
//        //使用函数式
//        //List<SimpleGrantedAuthority> permissionList = permissions.stream()
//        //        .map(SimpleGrantedAuthority::new)
//        //        .collect(Collectors.toList());
//        return permissionList;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
