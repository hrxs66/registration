package com.registration.service;

import com.registration.entity.Result;
import com.registration.entity.Sc;
import com.baomidou.mybatisplus.extension.service.IService;
import com.registration.entity.ScBo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
public interface ScService extends IService<Sc> {

    static void save(List<ScBo> cachedDataList) {

    }

    Result addStudentList(Integer courseId, String allImagesPath);

    Result courseList(Integer studentId);



}
