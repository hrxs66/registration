package com.registration.controller;


import com.registration.entity.Record;
import com.registration.entity.RecordVo;
import com.registration.entity.Result;
import com.registration.service.RecordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordService recordService;

    @PostMapping("/addregistration")
    @ApiOperation(value = "教师发起签到")
    public Result addRegistration(@RequestBody RecordVo recordVo) {
        Result result = recordService.addRegistration(recordVo.getCourseId(),recordVo.getLocation());
        return result;
    }

    @PostMapping("/endregistration")
    @ApiOperation(value = "教师结束签到")

    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "courseId", value = "课程id", required = true, dataType = "int"),
//            @ApiImplicitParam(paramType = "query", name = "time", value = "第几次点名", required = true, dataType = "int"),

    })
    public Result endRegistration(@RequestBody RecordVo recordVo) {
        Result result = recordService.endRegistration(recordVo.getCourseId(),recordVo.getTime());
        return result;
    }


    @PostMapping("/studentregistration")
    @ApiOperation(value = "学生签到")
    @ApiImplicitParams({
      //      @ApiImplicitParam(paramType = "query", name = "id", value = "签到记录id", required = true, dataType = "int"),
//
    })
    public Result studentreRistration(@RequestBody Record record) {
        Result result = recordService.studentRegistration(record.getStudentNumber(),record.getCourseId(),record.getTime());
        return result;
    }
    @PostMapping("/setleave")
    @ApiOperation(value = "教师设置学生为请假")
    @ApiImplicitParams({
          //  @ApiImplicitParam(paramType = "query", name = "id", value = "签到记录id", required = true, dataType = "int"),

    })
    public Result setLeave(@RequestBody Record record) {
        Result result = recordService.setLeave(record.getId());
        return result;
    }

    @GetMapping("/registrationlist")
    @ApiOperation(value = "查询学生待签到列表")
    public Result registrationlist(Integer studentId) {
        Result result = recordService.registrationList(studentId);
        return result;
    }

    @GetMapping("/courseregistrationlist")
    @ApiOperation(value = "教师查询单次课程签到列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "courseId", value = "课程id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "time", value = "第几次点名", required = true, dataType = "int"),

    })
    public Result courseRegistrationList(Integer courseId,Integer time) {
        Result result = recordService.courseRegistrationList(courseId,time);
        return result;
    }
    @GetMapping("/scourseregistrationlist")
    @ApiOperation(value = "学生查询单个课程全部签到情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "courseId", value = "课程id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "number", value = "学生学号", required = true, dataType = "int"),

    })
    public Result scourseRegistrationList( Integer courseId,String number) {
        Result result = recordService.scourseRegistrationList(courseId,number);
        return result;
    }


}
