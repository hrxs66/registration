package com.registration.controller;



import com.registration.entity.Course;
import com.registration.entity.Result;
import com.registration.service.CourseService;
import com.registration.service.ScService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    private String filePath = "D:\\registration\\cs\\";
    private String address = "\\image\\cs\\";

    @Autowired
    private CourseService courseService;
    @Autowired
    private ScService scService;


    @PostMapping("/addcourse")
    @ApiOperation(value = "添加课程（含导入学生名单）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "课程名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "teacherId", value = "教师id", required = true, dataType = "int"),
         //   @ApiImplicitParam(paramType = "query", name = "path", value = "文件路径", required = true, dataType = "String")

    })
    public Result addCourse(String name,Integer teacherId, MultipartFile file){
        Result result = new Result();
//        System.out.println(courseDto.getName());
//        System.out.println(courseDto.getTeacherId());
//        System.out.println(courseDto.getPath ());
        Result<Course> result1 = courseService.addCourse(name, teacherId);
        if ((result1.getCode() == 404)){
            return  result1;
        }

        String idPath = result1.getData().getId() + "\\";
        if (file == null) {
            result.setStatus("没有文件上传!");
            result.setCode(404);
            return result;
        }
        // 用于保存所文件路径
        String allImagesPath = new String();
        if (file != null) {

                String fileName = file.getOriginalFilename();//获取文件图片的原始名称
                //String suffixName = (fileName.substring(fileName.lastIndexOf("."))).toUpperCase(Locale.ROOT);  //获取图片后缀名

                String newFileName = fileName;//新的文件的名称
            //System.out.println(newFileName);
                File dest = new File(filePath + idPath + newFileName);
            //System.out.println(filePath + idPath + newFileName);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(dest);
                        allImagesPath =  filePath + idPath + newFileName ;
                } catch (IOException e) {
                    e.printStackTrace();
                }

        } else {
            File dest = new File(filePath + idPath);
            if (!dest.exists()) {
                dest.mkdir();
            }
        }
        //要改成传课程id
        result = scService.addStudentList(result1.getData().getId(),allImagesPath);

        return result;
    }


}
