package org.example.demo05;

import com.github.pagehelper.Page;
import org.example.demo05.entity.Member;
import org.example.demo05.entity.bean.MemberBean;
import org.example.demo05.service.MemberService;
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
    private MemberService memberService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void contextLoads() {
    }

    //单元测试，测试MemberServiceImpl的findAll方法
    @Test
    @DisplayName("对MemberServiceImpl测试list方法")
    public void testMemberServiceList() {
        Page<?> p = new Page<>(1, 10);
        MemberBean memberBean = new MemberBean();
        memberBean.setId(22);
        List<Member> members = memberService.getMembers(p, memberBean);

        //断言
        Assertions.assertEquals(1, members.size());
    }

    @Test
    @DisplayName("测试Redis的string类型")
    public void testRedis01() {
        Member member = new Member();
        member.setId(1);
        member.setName("孙小美");
        member.setPhone("11111");
        //存
        redisTemplate.opsForValue().set("member", member);
        //取
        Member member1 = (Member) redisTemplate.opsForValue().get("member");
        IO.println(member1);

        //redisTemplate.opsForValue().set("count", Integer.valueOf(100));
        //redisTemplate.opsForValue().increment("count");

        redisTemplate.delete("member");
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

}
