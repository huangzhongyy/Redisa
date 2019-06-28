package com.cssl.service.impl;

import com.cssl.dao.UsersDao;
import com.cssl.pojo.Users;
import com.cssl.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: ZProj
 * @Date: 2019/6/20 11:26
 * @Author: Mr.Deng
 * @Description:
 */
@Service
@Transactional
public class Userserviceimpl implements Userservice {

 @Autowired
  private UsersDao udao;


 @Override
 @Cacheable(value = "hzz")
 public List<Users> userFianll() {
  return udao.userFianll();
 }



}
