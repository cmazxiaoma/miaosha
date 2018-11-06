package com.cmazxiaoma.seckill.cap.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/5 17:21
 */
@Table(name = "tbl_broker_message_log")
@Data
public class BrokerMessageLog {

    @Id
    @Column(name = "message_id")
    private String messageId;

    @Column(name = "message")
    private String message;

    @Column(name = "try_count")
    private Integer tryCount;

    @Column(name = "status")
    private String status;

    @Column(name = "next_retry")
    private Date nextRetry;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
