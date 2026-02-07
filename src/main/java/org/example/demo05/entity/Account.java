package org.example.demo05.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    @NotBlank(message = "用户名不可为空")
    @Size(min = 6, max = 12, message = "用户名长度必须介于6~12位")
    private String username;
    @NotBlank(message = "密码不可为空")
    private String password;
    @NotBlank(message = "验证码不可为空")
    private String captcha;
    @NotBlank(message = "验证码键不可为空")
    private String key;
}
