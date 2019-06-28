package mongo;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * @program: ZProj
 * @Date: 2019/6/21 19:33
 * @Author: Mr.Deng
 * @Description:
 * 原生
 */
public class MongoDBUtil {
 private static MongoClient client;
 private static Mongo mongo = null;
 private static String DBString = "test";//数据库名
 private static String hostName = "192.168.191.130";
 private static int port = 27017;//端口号
 private static int poolSize = 10;//连接池大小
 //private static String url = "mongodb://192.168.191.130:27017/test?AutoConnectRetry=true";
 private static String url = "mongodb://testrw:testrw@192.168.191.130:27017/test?AutoConnectRetry=true";

 //获取数据库连接
 public static DB getDB() {
  MongoClientURI uri = new MongoClientURI(url);
  client = new MongoClient(uri);
  return client.getDB(DBString);
 }
 public static void main(String[] args) {
  DB db = getDB();
  System.out.println("db:"+db);
  //db.requestStart();
  //db.requestDone();
 }
 public static void close(){
  client.close();
 }

}
