package test;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ZProj
 * @Date: 2019/6/19 17:33
 * @Author: Mr.Deng
 * @Description:
 */
public class Test {

  private static Jedis jd;
  private Person bean1;
  private Person bean2;



  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    jd = new Jedis("192.168.191.128", 6380); //端口是虚拟机的端口 要主服务器才能写
    jd.auth("huangzhong");
    System.out.println("连接成功！");
    //选择数据库

  }
//给对象赋值
  @Before
  public void setUp() throws Exception {
    bean1 = new Person();
    bean1.setId("111");
    bean1.setAge(11);
    bean1.setName("问问");
    bean2 = new Person();
    bean2.setId("222");
    bean2.setAge(22);
    bean2.setName("来啦");
  }


  //保存对象
  @org.junit.Test
  public  void test(){
    jd.set("pp".getBytes(),Serialize.serialize(bean1));//写入2进制 将对象序列化成byte[]
    jd.expire("pp".getBytes(),300);//设置失效时间
    //从redis数据库里面读取s
    byte[] value=jd.get("pp".getBytes());
    Object obj=Serialize.unserialize(value);
    if(obj!=null && obj instanceof Person){
      Person per=(Person)obj;
      System.out.println(per.getId()+"\t"+per.getName()+"\t"+per.getAge());
    }
  }


  @org.junit.Test  //操作集合
  public void testlist(){
    List<Person> list=new ArrayList<>();
    list.add(bean1);
    list.add(bean2);
    jd.set("plist".getBytes(),Serialize.serialize(list));
    jd.expire("plist".getBytes(),300);

    byte[] value=jd.get("plist".getBytes());
    Object obj=Serialize.unserialize(value);
   if(obj!=null && obj instanceof List<?>){
     List<Person> pl = (List<Person>)obj;
     for (Person p :pl){
       System.out.println(p.getId()+"\t"+p.getName()+"\t"+p.getAge());
     }
   }
  }

  //从边上丢进去然后删除
  @org.junit.Test
  public  void ssteset(){
  jd.lpush("bean".getBytes(),Serialize.serialize(bean1));
  jd.lpush("bean".getBytes(), Serialize.serialize(bean2));
  jd.expire("bean".getBytes(), 300);
  for (int i = 0; i < jd.llen("bean".getBytes()); i++) {
    Person per = (Person)Serialize.unserialize(jd.lindex("bean".getBytes(), i));
    System.out.println(per.getId()+"\t"+per.getName()+"\t"+per.getAge());
  }
  System.out.println("=======================");

  byte[] value = jd.lpop("bean".getBytes());
  Object obj = Serialize.unserialize(value);
  if(obj!=null && obj instanceof Person){
    Person per = (Person)obj;
    System.out.println(per.getId()+"\t"+per.getName()+"\t"+per.getAge());
  }

  value = jd.lpop("bean".getBytes());
 // value = jd.lindex("bean".getBytes(), 0);
  obj = Serialize.unserialize(value);
  if(obj!=null && obj instanceof Person){
    Person per = (Person)obj;
    System.out.println(per.getId()+"\t"+per.getName()+"\t"+per.getAge());
  }


  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    jd.close();
    //pool.close();
  }

}
