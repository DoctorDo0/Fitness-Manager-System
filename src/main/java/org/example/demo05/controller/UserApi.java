package org.example.demo05.controller;

import org.example.demo05.entity.Account;
import org.example.demo05.entity.User;
import org.example.demo05.service.UserService;
import org.example.demo05.utils.JsonResp;
import com.wf.captcha.SpecCaptcha;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserApi {
    private static final PasswordEncryptor pe = new StrongPasswordEncryptor();
    private RedisTemplate<Object, Object> redisTemplate;
    private UserService userService;


    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletResponse resp, @Validated @NotBlank String key) throws IOException {
        SpecCaptcha captcha = new SpecCaptcha(140, 40, 5);
        //清除缓存
        resp.setContentType("image/gif");
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);

        //将验证码存储在redis中
        redisTemplate.opsForValue().set("captcha-" + key,
                captcha.text().toLowerCase(), Duration.ofMinutes(1));

        //响应给前端
        captcha.out(resp.getOutputStream());
    }

    //登录
    @PostMapping("/login")
    public JsonResp login(@RequestBody @Validated Account account) {
        //从redis中取出正确的验证码
        String correct = (String) redisTemplate.opsForValue()
                .getAndDelete("captcha-" + account.getKey());

        if (!account.getCaptcha().equals(correct)) {
            return JsonResp.error("验证码不正确");
        }

        User user = this.userService.findByUsername(account.getUsername());
        if (user == null) {
            return JsonResp.error("用户名或密码错误");
        }

        boolean success = pe.checkPassword(account.getPassword(), user.getPassword());

        if (success) {
            //密码正确，颁发令牌
            //jwt：json web token
            return null;
        } else {
            return JsonResp.error("用户名或密码错误");
        }
    }


    @PostMapping
    public JsonResp save(@RequestBody User user) {
        boolean success = this.userService.save(user);
        if (success) {
            return JsonResp.success(user);
        } else {
            return JsonResp.error("保存用户失败");
        }
    }

    @PutMapping
    public JsonResp update(@RequestBody User user) {
        boolean success = this.userService.updateById(user);
        if (success) {
            return JsonResp.success(user);
        } else {
            return JsonResp.error("修改用户失败");
        }
    }

    @DeleteMapping
    public JsonResp deleteByIds(@RequestBody Integer[] ids) {
        boolean success = this.userService.removeByIds(List.of(ids));
        if (success) {
            return JsonResp.success("删除操作成功");
        } else {
            return JsonResp.error("删除操作失败");
        }
    }

}
