package org.example.demo05.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.example.demo05.utils.AuditEntity;
import lombok.Getter;
import lombok.Setter;

@TableName("t_security_user")
@Getter
@Setter
public class User extends AuditEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private String description;
}
