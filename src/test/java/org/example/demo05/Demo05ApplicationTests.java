package org.example.demo05;

import com.github.pagehelper.Page;
import org.example.demo05.entity.Student;
import org.example.demo05.entity.User;
import org.example.demo05.entity.bean.StudentBean;
import org.example.demo05.service.StudentService;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

@SpringBootTest
class Demo05ApplicationTests {
    @Autowired
    private StudentService studentService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void contextLoads() {
    }

    //单元测试，测试StudentServiceImpl的findAll方法
    @Test
    @DisplayName("对StudentServiceImpl测试list方法")
    public void testStudentServiceList() {
        Page<?> p = new Page<>(1, 10);
        StudentBean studentBean = new StudentBean();
        studentBean.setId(22);
        List<Student> students = studentService.getStudents(p, studentBean);

        //断言
        Assertions.assertEquals(1, students.size());
    }

    @Test
    @DisplayName("测试Redis的string类型")
    public void testRedis01() {
        Student student = new Student();
        student.setId(1);
        student.setName("孙小美");
        student.setPhone("11111");
        //存
        redisTemplate.opsForValue().set("student", student);
        //取
        Student student1 = (Student) redisTemplate.opsForValue().get("student");
        IO.println(student1);

        //redisTemplate.opsForValue().set("count", Integer.valueOf(100));
        //redisTemplate.opsForValue().increment("count");

        redisTemplate.delete("student");
    }

    @Test
    @DisplayName("测试redis的list类型")
    public void testRedisList() {
        redisTemplate.opsForList().leftPush("01", "111");
        redisTemplate.opsForList().leftPush("01", "222");
        redisTemplate.opsForList().leftPush("01", "333");
        redisTemplate.opsForList().leftPush("01", "444");

        redisTemplate.opsForList().rightPush("01", "aaa");
        redisTemplate.opsForList().rightPush("01", "bbb");
        redisTemplate.opsForList().rightPush("01", "ccc");
        redisTemplate.opsForList().rightPush("01", "ddd");

        Assertions.assertEquals("444", redisTemplate.opsForList().leftPop("01"));

//        long cnt = redisTemplate.opsForList().size("01");
//        Assertions.assertEquals(26, cnt);
    }

    @Test
    @DisplayName("测试redis的set类型")
    public void testRedisSet() {
        redisTemplate.opsForSet().add("3", 10, 20, 30, 40, 50);
        Set<Object> values = redisTemplate.opsForSet().members("3");
        for (Object o : values) {
            IO.println(o);
        }
        redisTemplate.opsForSet().remove("3", 10, 20);
    }

    @Test
    @DisplayName("测试Redist的zset类型")
    public void testRedisZSet() {
        redisTemplate.opsForZSet().add("004", 1, 100);
        redisTemplate.opsForZSet().add("004", 10, 60);
        redisTemplate.opsForZSet().add("004", 100, 20);

        ZSetOperations.TypedTuple<Object> obj = redisTemplate.opsForZSet().popMax("004");
        Assertions.assertEquals(1, obj.getValue());
    }

    @Test
    @DisplayName("测试redis的hash类型")
    public void testRedisHash() {
        redisTemplate.opsForHash().put("005", "01", 10);
        redisTemplate.opsForHash().put("005", "02", 20);
        redisTemplate.opsForHash().put("005", "03", 30);
        redisTemplate.opsForHash().put("005", "04", 40);

        List<Object> list = redisTemplate.opsForHash().values("005");

        for (Object obj : list) {
            IO.println(obj);
        }

    }

    @Test
    @DisplayName("生成密钥")
    public void test1() {
//        String str = "15963294666";
//        String str = "ciphertext";
        String str = "123456";
        PasswordEncryptor pe = new StrongPasswordEncryptor();
        IO.println(pe.encryptPassword(str));
    }

    @Test
    @DisplayName("测试引用传递")
    public void test2() {
        User user = new User();
        user.setDescription("test1");
        User user2 = user;
        user2.setDescription("test2");
        IO.println(user2.getDescription());
        IO.println(user.getDescription());
    }
}
