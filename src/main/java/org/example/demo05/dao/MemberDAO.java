package org.example.demo05.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.demo05.entity.Member;

import java.util.List;

@Mapper
public interface MemberDAO {
    int insert(Member member);

    int deleteByPrimaryKey(Integer[] ids);

    int updateByPrimaryKey(Member member);

    //    Member selectByPrimaryKey(Integer id);
    List<Member> selectAll();
}
