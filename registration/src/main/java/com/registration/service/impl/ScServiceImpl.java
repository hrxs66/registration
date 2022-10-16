package com.registration.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.registration.entity.*;
import com.registration.mapper.CourseMapper;
import com.registration.mapper.ScMapper;
import com.registration.mapper.UserMapper;
import com.registration.service.ScService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class ScServiceImpl extends ServiceImpl<ScMapper, Sc> implements ScService {


    @Autowired
    UserMapper userMapper;
    @Autowired
    ScMapper scMapper;
    @Autowired
    CourseMapper courseMapper;
    @Override
    public Result addStudentList(Integer courseId, String allImagesPath) {

        Workbook wb = new Workbook();
        wb.loadFromFile(allImagesPath);

        //获取工作表
        Worksheet sheet = wb.getWorksheets().get(0);

//        sheet.deleteRow(1);//删除第1行
//        sheet.deleteRow(2);//删除第2行
//        sheet.deleteRow(3);//删除第3行
        sheet.deleteRow(1,3);//删除第1行及下一行在内的两行
        sheet.deleteColumn(3);//删除第3列
        //sheet.deleteColumn(4,2);//删除第4列及右侧一列在内的两列

        //保存文档
        wb.saveToFile(allImagesPath);
        wb.dispose();

        EasyExcel.read(allImagesPath, ScBo.class, new PageReadListener<ScBo>(dataList -> {
            for (ScBo scBo : dataList) {

                if (scBo.getName().isEmpty()) continue;
                if (scBo.getStudentNumber().length() == 8){
                    scBo.setStudentNumber("0"+scBo.getStudentNumber());
                }
                Sc sc = new Sc();
//                QueryWrapper qw = new QueryWrapper();
//                qw.eq("number",scBo.getStudentId());
//                User user = userMapper.selectOne(qw);
                sc.setStudentNumber(scBo.getStudentNumber());
                sc.setStudentName(scBo.getName());
                sc.setCourseId(courseId);
                sc.setCreateTime(LocalDateTime.now());
                sc.setUpdateTime(LocalDateTime.now());
                scMapper.insert(sc);
            }
        })).sheet().doRead();

        Result result = new Result();
        result.setCode(200);

        return result;
    }

    @Override
    public Result<ArrayList<Course>> courseList(Integer studentId) {

        Result<ArrayList<Course>> result = new Result<>();
        QueryWrapper<User> qw = new QueryWrapper<User>();
        qw.eq("id",studentId);
        User user = userMapper.selectOne(qw);

        QueryWrapper<Sc> qw1 = new QueryWrapper<Sc>();
        qw1.eq("student_number",user.getNumber());
        List<Sc> scList = scMapper.selectList(qw1);
        ArrayList<Course> courseList = new ArrayList<Course>();
        for (Sc sc : scList) {
            Course course = courseMapper.selectById(sc.getCourseId());
            courseList.add(course);
        }

        result.setCode(200);
        result.setData(courseList);

        return result;
    }


}
