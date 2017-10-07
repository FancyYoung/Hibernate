
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	static{
		// 通过Configuration().configure();读取并解析hibernate.cfg.xml配置文件。
		configuration=new Configuration().configure();
		// 获得SessionFactory对象
		sessionFactory =configuration.buildSessionFactory();
	}
	public static Session openSession(){
		// 返回Session对象。相当于Connection连接对象
		return sessionFactory.openSession();
	}
}
