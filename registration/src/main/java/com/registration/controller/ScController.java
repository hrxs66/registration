package com.registration.controller;


import com.registration.entity.Result;
import com.registration.entity.User;
import com.registration.service.CourseService;
import com.registration.service.ScService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
@RestController
@RequestMapping("/sc")
public class ScController {

    @Autowired
    private ScService scService;
    @Autowired
    private CourseService courseService;
    @GetMapping("/courselist")
    @ApiOperation(value = "学生查询参加的全部课程")
    @ApiImplicitParams({

            @ApiImplicitParam(paramType = "query", name = "id", value = "学生id", required = true, dataType = "int"),

    })
    public Result courseList(Integer id) {
        //System.out.println(id);
        Result result = scService.courseList(id);
        return result;
    }


    @GetMapping("/teacourselist")
    @ApiOperation(value = "教师查询发起的全部课程")
    @ApiImplicitParams({

            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true, dataType = "int"),

    })
    public Result teaCourseList(Integer id) {
        Result result = courseService.teaCourseList(id);
        return result;
    }

}
