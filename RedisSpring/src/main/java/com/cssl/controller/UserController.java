package com.cssl.controller;

import com.cssl.pojo.Users;
import com.cssl.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @program: ZProj
 * @Date: 2019/6/20 11:26
 * @Author: Mr.Deng
 * @Description:
 */
@Controller
public class UserController {

 @Autowired
 private Userservice userservice;
@Autowired
 private MongoTemplate mongoTemplate; //芒果Td提供的


 //新增
 @RequestMapping("/saveUser")
 public Users save(){
  Users user=new Users(520,"王子是你");
  mongoTemplate.save(user);//直接调用save的方法
  System.out.println("新增成功");

  return user;
 }

 @GetMapping("/findUser")
 public List<Users> find(){
  List<Users> userlist=mongoTemplate.findAll(Users.class);
  for (Users uu:userlist){
   System.out.println("ID"+uu.getUid()+"Name"+uu.getUname());
  }
  return userlist;
 }

@RequestMapping("Select")
 public  String Userfinall(Model mode){
 List<Users> list=userservice.userFianll();
 System.out.println("list"+list.size());
  mode.addAttribute("list",list);
  return "select.html";
 }

 //条件查询
 @RequestMapping("/findByName")
 public Users findByName(@RequestParam(name="uname") String uname){
  Query query=new Query();
  query.addCriteria(Criteria.where("uanme").is(uname));
  Users user=mongoTemplate.findOne(query,Users.class);
  //if(user!=null){
   System.out.println("----------"+user.getUname());
 // }

  return  user;
 }

 /**
  * @param
  * @return
  */
 @GetMapping("/findByNameA")
 public Users findByName2(@RequestParam("uname") String uname) {//Users user = service.findByName(name);
  //return user;
  Query query=new Query();
  query.addCriteria(Criteria.where("uname").is(uname));
  Users user =  mongoTemplate.findOne(query, Users.class);
  System.out.println("----------------"+user.getUname());
  return user;
 }

 @GetMapping("/findByPage")
 public List<Users> findByPage(@RequestParam("uname") String uname, Integer pageIndex, Integer pageSize){
  Query query = new Query();
  //模糊查询
  Criteria criteria = new Criteria();
  Pattern pattern = Pattern.compile("^.*" + uname + ".*$", Pattern.CASE_INSENSITIVE);
  criteria.andOperator(Criteria.where("uname").regex(pattern));
  query.addCriteria(criteria);
  //排序
  //query.with(new Sort(Sort.Direction.DESC, "age"));
  //分页
  query.skip((pageIndex - 1) * pageSize);                 // 从那条记录开始
  query.limit(pageSize);                                  // 取多少条记录
  long count = mongoTemplate.count(query, Users.class); // 总记录数
  System.out.println("count:"+count);
  System.out.println("-------------:"+mongoTemplate.find(query,Users.class).size());
  return mongoTemplate.find(query, Users.class);
 }

}
