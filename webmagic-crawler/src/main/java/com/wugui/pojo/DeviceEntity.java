package com.wugui.pojo;

import com.wugui.utils.JsonDataUserType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table(name = "device")
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "deviceNum")
    private String deviceNum;

    /**
     * 如果不自定义映射，会出现错误column "jsonb" is of type jsonb but expression is of type character varying
     * 建议：You will need to rewrite or cast the expression.
     **/
    @Column(name = "jsonb")
    @Type(type = "JsonDataUserType")
    private String jsonb;
}
