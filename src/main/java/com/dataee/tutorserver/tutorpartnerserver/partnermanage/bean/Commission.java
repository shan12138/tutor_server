package com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 18:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commission {
    private Integer id;
    private Integer courseId;
    private Integer productId;
    private Integer money;
    private Integer parentId;
    private Integer realMoney;
}
