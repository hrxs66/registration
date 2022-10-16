package com.registration.service;

import com.registration.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.registration.entity.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
public interface RecordService extends IService<Record> {

    Result addRegistration(Integer courseId, String location);

    Result studentRegistration(String studentNumber, Integer studentId, Integer time);

    Result registrationList(Integer studentId);


    Result courseRegistrationList(Integer courseId, Integer time);

    Result scourseRegistrationList(Integer courseId, String number);


    Result endRegistration(Integer courseId, Integer time);

    Result setLeave(Integer id);
}
