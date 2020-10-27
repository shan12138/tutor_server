package com.dataee.tutorserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date time;
    private String flowType;
    private String kind;
    private Integer number;
    private String source;
    private Integer sourceId;
    private String target;
    private Integer targetId;
    private String message;
    private Integer courseId;
}
