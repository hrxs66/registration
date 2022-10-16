package com.registration.service;

import com.registration.entity.Course;
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
public interface CourseService extends IService<Course> {

    Result<Course> addCourse(String name, Integer teacherId);

    Result teaCourseList(Integer id);
}
