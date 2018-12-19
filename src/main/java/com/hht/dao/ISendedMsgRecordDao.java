package com.hht.dao;

import com.hht.entity.SendedMsgRecord;
import com.hht.entity.SendedMsgRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ISendedMsgRecordDao {
    long countByExample(SendedMsgRecordExample example);

    int deleteByExample(SendedMsgRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SendedMsgRecord record);

    int insertSelective(SendedMsgRecord record);

    List<SendedMsgRecord> selectByExample(SendedMsgRecordExample example);

    SendedMsgRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SendedMsgRecord record, @Param("example") SendedMsgRecordExample example);

    int updateByExample(@Param("record") SendedMsgRecord record, @Param("example") SendedMsgRecordExample example);

    int updateByPrimaryKeySelective(SendedMsgRecord record);

    int updateByPrimaryKey(SendedMsgRecord record);
}