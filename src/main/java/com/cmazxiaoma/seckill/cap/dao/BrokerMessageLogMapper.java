package com.cmazxiaoma.seckill.cap.dao;

import com.cmazxiaoma.seckill.cap.model.BrokerMessageLog;
import com.cmazxiaoma.seckill.core.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/5 17:53
 */
public interface BrokerMessageLogMapper extends Mapper<BrokerMessageLog> {

    /**
     * 查询消息状态为发送中且已经超时的消息集合
     * @return
     */
    List<BrokerMessageLog> listStatusAndTimeoutMessage();

    /**
     * 更新消息重试次数
     */
    void updateReSendMessage(@Param("messageId") String messageId,
                             @Param("updateTime") Date updateTime);

    /**
     * 更新消息状态
     */
    void updateMessageLogStatus(@Param("messageId") String messageId,
                                @Param("status") String status,
                                @Param("updateTime") Date updateTime);
}
