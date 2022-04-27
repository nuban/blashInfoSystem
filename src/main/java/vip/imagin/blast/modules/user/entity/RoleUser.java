package vip.imagin.blast.modules.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUser {
    private Long userid;
    private Long roleid;

    public RoleUser(Long userid) {
        this.userid = userid;
    }
}
