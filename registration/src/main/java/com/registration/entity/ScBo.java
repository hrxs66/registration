package com.registration.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)

public class ScBo  implements Serializable {



   // @ExcelProperty(index = 0)
    private String studentNumber;
   // @ExcelProperty(index = 1)
    private String name;

    private Integer id;
}
