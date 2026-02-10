package org.example.demo05.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.demo05.entity.Member;
import org.example.demo05.entity.bean.MemberBean;
import org.example.demo05.utils.AuditEntity;

import java.util.List;

@Mapper
public interface MemberDAO {
    int insert(Member member);

    int deleteByPrimaryKey(@Param("auditEntity") AuditEntity auditEntity, @Param("ids") Integer[] ids);

    int updateByPrimaryKey(Member member);

    //    Member selectByPrimaryKey(Integer id);
    List<Member> selectAll(MemberBean memberBean);
}
