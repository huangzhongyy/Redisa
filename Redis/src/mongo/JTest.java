package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.junit.*;
import test.Person;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class JTest {

	private static BaseDAOImpl baseDAOImpl ;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		baseDAOImpl = new BaseDAOImpl();
	}

	//添加
	@Test
	public void testSave(){
		Person bean = new Person();
		bean.setAge(20);
		bean.setId("333");
		bean.setName("小龙");
		baseDAOImpl.insert("person", bean);
		System.out.println("新增成功！");
	}

	//查询全部
	@Test
	public void testQuery(){
		//不能使用父接口只能使用子对象
		BasicDBObject db = new BasicDBObject();
		//db.put("name", "兵哥");
		List<DBObject> list = baseDAOImpl.find("person", db);
		System.out.println("size:"+list.size());
		for(DBObject i : list){
			System.out.println(i.get("id")+"\t"+i.get("name")+"\t"+i.get("age"));
		}
		System.out.println("查询完成！");
	}

	//模糊查询
	@Test
	public void testQueryLike(){
		String key = "王";
		Pattern pattern = Pattern.compile("^.*" + key + ".*$", Pattern.CASE_INSENSITIVE);
		DB mdb = MongoDBUtil.getDB();
		List<DBObject> list1 =  mdb.getCollection("person").find(new BasicDBObject("name", pattern)).toArray();
		for(int i = 0; i < list1.size();i++){
			System.out.println(list1.get(i));
		}
	}

	//分页查询
	@Test
	public void testQueryPage(){
		BasicDBObject beanOne = new BasicDBObject();
		List<Map<String, Object>> list = baseDAOImpl.query("person", beanOne, 0, 1);
		System.out.println("count:"+baseDAOImpl.queryCount("person", beanOne));
		System.out.println("size:"+list.size());
		for(int i = 0; i < list.size();i++){
			System.out.println(list.get(i));
		}

		System.out.println("=======================");

		Long count=baseDAOImpl.queryCount("person", beanOne);
		System.out.println("count:"+count);

		DBObject sort = new BasicDBObject("age", 1);
		DBObject fields = new BasicDBObject("age",1);	//指定查询字段
		DBCursor cur=baseDAOImpl.getCursor("person", beanOne, null, sort, 0,4);

		while (cur.hasNext()) {
			DBObject obj = cur.next();
			System.out.println(obj.get("id")+"\t"+obj.get("name")+"\t"+obj.get("age"));
		}
	}

	//修改方法
	@Test
	public void testUpdate(){

		BasicDBObject oldBean = (BasicDBObject) baseDAOImpl.find("person", new BasicDBObject("name", "阿灵")).get(0);
		BasicDBObject newBean = (BasicDBObject) oldBean.clone();
		newBean.put("name","灵哥");
		newBean.put("age", 24);
		System.out.println(oldBean.get("name")+"\t"+oldBean.get("age"));
		//修改
		baseDAOImpl.update("person", oldBean, newBean);
		System.out.println(newBean.get("name")+"\t"+newBean.get("age"));
		System.out.println("修改完成！");
	}

	//删除方法
	@Test
	public void testDelete(){
		Person bean = new Person();
		bean.setId("p12");
		//bean.setName("辉哥");
		baseDAOImpl.delete("person", bean);
		System.out.println("删除操作完成！");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	MongoDBUtil.close();
	}

	@Before
	public void setUp() throws Exception {

	}
	@After
	public void tearDown() throws Exception {

	}
}
