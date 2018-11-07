package com.cmazxiaoma.seckill.cap.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/5 17:17
 */
@Table(name = "tbl_order")
@Data
public class Order implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "message_id")
    private String messageId;
}
