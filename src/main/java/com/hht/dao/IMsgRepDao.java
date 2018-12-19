package com.hht.dao;

import com.hht.entity.MsgRep;
import com.hht.entity.MsgRepExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IMsgRepDao {
    long countByExample(MsgRepExample example);

    int deleteByExample(MsgRepExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsgRep record);

    int insertSelective(MsgRep record);

    List<MsgRep> selectByExampleWithBLOBs(MsgRepExample example);

    List<MsgRep> selectByExample(MsgRepExample example);

    MsgRep selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsgRep record, @Param("example") MsgRepExample example);

    int updateByExampleWithBLOBs(@Param("record") MsgRep record, @Param("example") MsgRepExample example);

    int updateByExample(@Param("record") MsgRep record, @Param("example") MsgRepExample example);

    int updateByPrimaryKeySelective(MsgRep record);

    int updateByPrimaryKeyWithBLOBs(MsgRep record);

    int updateByPrimaryKey(MsgRep record);
}