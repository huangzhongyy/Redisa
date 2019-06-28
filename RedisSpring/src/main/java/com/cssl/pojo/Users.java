package com.cssl.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @program: ZProj
 * @Date: 2019/6/20 11:25
 * @Author: Mr.Deng
 * @Description:
 */
@Getter
@Setter
public class Users implements Serializable {
 private int uid;
 private String uname;
 //private String password;
 //private Date borddate;

 public Users() {

 }

 public Users(Integer uid, String uname) {
  this.uid = uid;
  this.uname = uname;

 }

}
