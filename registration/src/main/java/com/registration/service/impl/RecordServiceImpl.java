package com.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.registration.entity.*;
import com.registration.mapper.CourseMapper;
import com.registration.mapper.RecordMapper;
import com.registration.mapper.ScMapper;
import com.registration.mapper.UserMapper;
import com.registration.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.registration.utils.RedisUtils.redisTemplate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    ScMapper scMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public Result addRegistration(Integer courseId, String location) {
        Result result = new Result();
        QueryWrapper qw = new QueryWrapper();
        qw.eq("course_id",courseId);
        List<Sc> sclist = scMapper.selectList(qw);

        QueryWrapper qw1 = new QueryWrapper();
        qw1.eq("id",courseId);
        Course course = courseMapper.selectOne(qw1);
        UpdateWrapper uw = new UpdateWrapper();

        redisTemplate.opsForValue().set("Registration" + courseId + course.getTime() + 1, location, 2, TimeUnit.HOURS );

       // System.out.println("Registration" + courseId+course.getTime());
        for (int i = 0;i<sclist.size();++i){
            Sc sc = sclist.get(i);
            Record record = new Record();
            record.setCourseId(courseId);
            record.setStudentNumber(sc.getStudentNumber());
            record.setTime(course.getTime()+1);
            record.setType(0);
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateTime(LocalDateTime.now());
            recordMapper.insert(record);
        }

        uw.eq("id",courseId);
        uw.set("time",course.getTime()+1);
        uw.set("type",1);
        course.setTime(course.getTime()+1);
        course.setType(1);
        courseMapper.update(null,uw);
        result.setCode(200);
        result.setData(course);
        return result;
    }

    @Override
    public Result studentRegistration(String studentNumber, Integer id, Integer time) {
        Result result = new Result();
        QueryWrapper qw = new QueryWrapper();
        qw.eq("student_number",studentNumber);
        qw.eq("course_id",id);
        qw.eq("time",time);
        Record record = recordMapper.selectOne(qw);
        System.out.println(record.toString());
        record.setType(1);
        record.setUpdateTime(LocalDateTime.now());
        recordMapper.updateById(record);
        result.setCode(200);
        result.setData(record);
        return result;
    }

    @Override
    public Result registrationList(Integer studentId) {
        Result result = new Result();
        QueryWrapper qw1 = new QueryWrapper();
        qw1.eq("id",studentId);
        User user = userMapper.selectOne(qw1);
        QueryWrapper qw = new QueryWrapper();
        qw.eq("student_number",user.getNumber());
        qw.eq("type",0);
        List<Record> list = recordMapper.selectList(qw);
        ArrayList<RecordVo> recordVos = new ArrayList<>();
        for (int i = 0; i <list.size(); i++) {
            String location = redisTemplate.opsForValue().get("Registration" + list.get(i).getId() + list.get(i).getTime())+"";

            RecordVo recordVo = new RecordVo();
            recordVo.setLocation(location);
            recordVo.setId(list.get(i).getId());
            recordVo.setCourseId(list.get(i).getCourseId());
            recordVo.setCreateTime(list.get(i).getCreateTime());
            recordVo.setStudentNumber(list.get(i).getStudentNumber());
            recordVo.setTime(list.get(i).getTime());
            recordVo.setType(list.get(i).getType());
            recordVo.setUpdateTime(list.get(i).getUpdateTime());

            recordVos.add(recordVo);
        }
        result.setCode(200);
        result.setData(recordVos);
        return result;

    }

    @Override
    public Result endRegistration(Integer courseId, Integer time) {

        Result result = new Result();
        QueryWrapper qw = new QueryWrapper();
        qw.eq("course_id",courseId);
        qw.eq("time",time);
        qw.eq("type",0);
        List<Record> recordList = recordMapper.selectList(qw);

        UpdateWrapper uw = new UpdateWrapper();

        uw.eq("id",courseId);
        uw.set("type",0);
        courseMapper.update(null,uw);

        for (int i = 0;i<recordList.size();++i){
            Record record = recordList.get(i);
            record.setType(2);
            record.setUpdateTime(LocalDateTime.now());
            recordMapper.updateById(record);

        }

        result.setCode(200);
        return result;
    }

    @Override
    public Result setLeave(Integer id) {
        Result result = new Result();
        QueryWrapper qw = new QueryWrapper();
        qw.eq("id",id);
        Record record = recordMapper.selectOne(qw);
        record.setType(3);
        record.setUpdateTime(LocalDateTime.now());
        recordMapper.updateById(record);
        result.setCode(200);
        result.setData(record);
        return result;
    }

    @Override
    public Result courseRegistrationList(Integer courseId, Integer time) {
        Result result = new Result();

        QueryWrapper qw = new QueryWrapper();
        qw.eq("course_id",courseId);
        qw.eq("time",time);
        List<Record> recordList = recordMapper.selectList(qw);
        ArrayList<RecordDto> recordDtoList = new ArrayList<RecordDto>();
        for (int i = 0;i<recordList.size();++i){
            RecordDto recordDto = new RecordDto();
            recordDto.setCourseId(recordList.get(i).getCourseId());
            recordDto.setStudentNumber(recordList.get(i).getStudentNumber());
            recordDto.setCreateTime(recordList.get(i).getCreateTime());
            recordDto.setUpdateTime(recordList.get(i).getUpdateTime());
            recordDto.setTime(recordList.get(i).getTime());
            recordDto.setType(recordList.get(i).getType());
            recordDto.setId(recordList.get(i).getId());
            QueryWrapper qw1 = new QueryWrapper();
            qw1.eq("id",recordList.get(i).getCourseId());
            Course course = courseMapper.selectOne(qw1);
            recordDto.setCourseName(course.getName());
            QueryWrapper qw2 = new QueryWrapper();
            qw2.eq("student_number",recordList.get(i).getStudentNumber());
            qw2.eq("course_id",recordList.get(i).getCourseId());
            Sc sc = scMapper.selectOne(qw2);
            recordDto.setStudentName(sc.getStudentName());
            recordDtoList.add(recordDto);
        }
        result.setCode(200);
        result.setData(recordDtoList);
        return result;

    }

    @Override
    public Result scourseRegistrationList(Integer courseId, String number) {
        Result result = new Result();
        QueryWrapper qw = new QueryWrapper();
        qw.eq("course_id",courseId);
        qw.eq("student_number",number);
        List<Record> recordList = recordMapper.selectList(qw);

        result.setCode(200);
        result.setData(recordList);
        return result;

    }
}
