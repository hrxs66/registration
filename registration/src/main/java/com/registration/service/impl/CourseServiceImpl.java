package com.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.registration.entity.Course;
import com.registration.entity.Result;
import com.registration.mapper.CourseMapper;
import com.registration.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Override
    public Result<Course> addCourse(String name, Integer teacherId) {
        Result<Course> result = new Result<Course>();
        Course course = new Course();
        System.out.println(name);
        System.out.println(teacherId);

        QueryWrapper qw = new QueryWrapper();
        qw.eq("name",name);
        qw.eq("teacher_id",teacherId);
        Course course1 = courseMapper.selectOne(qw);
        if(course1 != null){
            result.setCode(404);
            result.setStatus("该课程已存在");
            return result;
        }
        else{
            course.setName(name);
            course.setTeacherId(teacherId);
            course.setCreateTime(LocalDateTime.now());
            course.setUpdateTime(LocalDateTime.now());
            course.setTime(0);
            course.setType(0);
            courseMapper.insert(course);
            QueryWrapper qw1 = new QueryWrapper();
            qw1.eq("name",name);
            qw1.eq("teacher_id",teacherId);
            Course course2 = courseMapper.selectOne(qw1);
            result.setCode(200);
            result.setData(course2);
            System.out.println(course2.getId());
            return result;
        }

    }

    @Override
    public Result teaCourseList(Integer id) {
        Result result = new Result();
        QueryWrapper qw = new QueryWrapper();
        qw.eq("teacher_id",id);
        List list = courseMapper.selectList(qw);
        result.setCode(200);
        result.setData(list);
        return result;
    }
}
