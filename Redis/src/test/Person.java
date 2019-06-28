package test;

import java.io.Serializable;

/**
 * @program: ZProj
 * @Date: 2019/6/19 17:50
 * @Author: Mr.Deng
 * @Description:
 */
public class Person implements Serializable {


 private static final long serialVersionUID = 1L;
 private String id;
 private String name;
 private Integer age;

 public String getId() {
  return id;
 }
 public void setId(String id) {
  this.id = id;
 }
 public String getName() {
  return name;
 }
 public void setName(String name) {
  this.name = name;
 }
 public Integer getAge() {
  return age;
 }
 public void setAge(Integer age) {
  this.age = age;
 }

}
