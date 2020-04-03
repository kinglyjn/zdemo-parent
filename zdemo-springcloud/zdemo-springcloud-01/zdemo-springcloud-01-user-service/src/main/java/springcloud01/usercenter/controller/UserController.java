package springcloud01.usercenter.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springcloud01.usercenter.module.User;
import springcloud01.usercenter.service.UserService;
import springcloud01.usercenter.vo.UserVo;

import java.util.List;

/**
 * @Author KJ
 * @Date 2020-04-01 4:04 PM
 * @Description
 */
@Api("对用户表CRUD")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation("添加用户")
    @PostMapping("/add")
    public String add(@RequestBody UserVo user){
        User u = new User();
        u.setName(user.getUsername());
        return userService.save(u) ? "SUCCESS" : "FAIL";
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/delete/{id}")
    public String delete(@ApiParam("用户主键") @PathVariable(value = "id") Integer id){
        return userService.removeById(id) ? "SUCCESS" : "FAIL";
    }

    @ApiOperation("修改用户")
    @PostMapping("/update")
    public String update(@RequestBody User user){
        return userService.updateById(user) ? "SUCCESS" : "FAIL";
    }

    @ApiOperation(value = "查询学生列表")
    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    @ApiOperation(value = "查询学生详情")
    @GetMapping("/{id}")
    public User info(@PathVariable(value = "id") Long id){
        return userService.getUserById(id);
    }

}
