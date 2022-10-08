package com.withThymeleaf.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.withThymeleaf.pojo.Employee;
import com.withThymeleaf.service.EmployeeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 员工列表
    @GetMapping("/lists")
    public String lists(Model model) {

        List<Employee> list = employeeService.lists();

        model.addAttribute("employeeList", list);

        return "empList";
    }

    // 注入配置文件中内容
    @Value("${photo.file.dir}")
    private String realPath;

    // 保存员工信息
    @SneakyThrows
    @PostMapping("/save")
    public String save(Employee employee, MultipartFile img) {
        String orinFileName = img.getOriginalFilename();
        // 避免文件重名
        String prefixFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 文件后缀
        String sufFileName = orinFileName.substring(orinFileName.lastIndexOf("."));

        // 新的文件名
        String newFileName = prefixFileName + sufFileName;

        // 设置员工头像资源访问路径
        employee.photo(newFileName);

        // 上传头像
        img.transferTo(new File(realPath, newFileName));

        // 保存员工信息
        employeeService.save(employee);

        return "redirect:/employee/lists";// 员工列表
    }

    // 根据 ID 查询员工信息
    @GetMapping("/detail")
    public String detail(Integer id, Model model) {

        Employee employee = employeeService.findEmployeeById(id);

        // 保存当前员工信息
        model.addAttribute("employee", employee);

        // 请求转发
        return "updateEmp";
    }

    // 更新员工信息
    @SneakyThrows
    @PostMapping("/update")
    public String update(Employee employee, MultipartFile img) {
        // 判断头像信息是否为空(为空说明没有上传)
        boolean notEmpty = !img.isEmpty();

        // 如果不为空说明 => 用户上传了头像
        if (notEmpty) {
            // 旧的头像路径 => 在本地中进行删除
            String oldPath = employeeService.findEmployeeById(employee.id()).photo();

            // 如果原来没有头像
            File file = new File(realPath, oldPath == null ? "" : oldPath);

            // 删除图片
            if (file.exists()) file.delete();

            String orinFilename = img.getOriginalFilename();
            // 时间戳唯一标识
            String prefixFilename = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            // 文件类型
            String sufFilename = orinFilename.substring(orinFilename.lastIndexOf("."));
            // 新的文件名
            String newFilename = prefixFilename + sufFilename;

            // 保存到本地中
            img.transferTo(new File(realPath, newFilename));

            // 更新员工信息
            employee.photo(newFilename);
        }

        // 如果 img 文件为空, 则使用原来 头像路径
        employeeService.update(employee);

        // 更新成功, 直接查询员工列表
        return "redirect:/employee/lists";
    }

    // 删除员工信息
    @GetMapping("/delete")
    public String delete(Integer id) {
        String photo = employeeService.findEmployeeById(id).photo();

        employeeService.delete(id);// 删除数据

        new File(realPath, photo).delete();// 删除本地图片

        return "redirect:/employee/lists";
    }

}