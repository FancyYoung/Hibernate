package com.itheima.test;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.itheima.domain.User;
import com.itheima.utlils.HibernateUtil;

public class Test1 {
	@Test
	public void testAdd1() {
		User u = new User();
		u.setName("张2");
		u.setAddress("上海");

		// 通过Configuration().configure();读取并解析hibernate.cfg.xml配置文件。
		Configuration config = new Configuration().configure();

		// 获得SessionFactory对象
		SessionFactory sessionFactory = config.buildSessionFactory();

		// 获得Session对象。相当于Connection连接对象
		Session session = sessionFactory.openSession();

		// 开启事务
		session.beginTransaction();

		// 操作代码
		session.save(u);

		// 事务提交
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}

	// CRUD操作

	// C操作
	@Test
	public void testAdd2() {
		User u = new User();
		u.setName("王五");
		u.setAddress("北京");
		Session session = HibernateUtil.openSession();
		// 开启事务
		session.beginTransaction();

		// 操作代码
		session.save(u);

		// 事务提交
		session.getTransaction().commit();
		session.close();
	}

	// R操作
	@Test
	public void getById() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		User user = session.get(User.class, 3);
		System.out.println(user);
		session.getTransaction().commit();
		session.close();
	}

	// R所有的操作
	@Test
	public void findAll() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();

		Query q = session.createQuery("from User");
		System.out.println(q.list());

		session.getTransaction().commit();
		session.close();
	}

	// U操作
	@Test
	public void update() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();

		User u = session.get(User.class, 3);
		u.setAddress("深圳");
		session.update(u);

		session.getTransaction().commit();
		session.close();
	}

	// D操作
	@Test
	public void delete() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();

		User user = session.get(User.class, 7);
		session.delete(user);

		session.getTransaction().commit();
		session.close();

	}

	/**
	 * HQL分页查询
	 */
	@Test
	public void test03() {
		Session s = HibernateUtil.openSession();
		String hql = "From User";
		Query q = s.createQuery(hql);
		q.setFirstResult(0);
		q.setMaxResults(10);

		System.out.println(q.list());

		s.close();
	}

	/**
	 * 投影查询（取出数据封装为对象）
	 */
	@Test
	public void test06() {
		Session s = HibernateUtil.openSession();

		String hql = "SELECT new User(name, address) FROM User ";

		Query q = s.createQuery(hql);
		q.setFirstResult(0);
		q.setMaxResults(10);
		List<User> users = q.list();

		System.out.println(users);

		s.close();

	}

	/**
	 * 使用unqiueResult查询一条数据
	 */
	@Test
	public void test01() {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();

		String hql = "FROM User WHERE id = ?";
		Query q = s.createQuery(hql);
		q.setParameter(0, 6);

		User u = (User) q.uniqueResult();
		System.out.println(u);

		s.getTransaction().commit();
		s.close();
	}

	/**
	 * 使用名称参数
	 */
	@Test
	public void test04() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();

		String hql = "FROM User WHERE name Like :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", "%张%");

		List<User> list = query.list();
		System.out.println(list);

		session.getTransaction().commit();
		session.close();
	}

	/**
	 * 使用原生sql查询
	 */
	@Test
	public void sql01() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();

		String sql = "Select * from user";
		SQLQuery sqlQuery = session.createSQLQuery(sql);

		List<User> list = sqlQuery.list();
		System.out.println(list);

		session.getTransaction();
		session.close();

	}

	/**
	 * 条件查询
	 */
	@Test
	public void test02(){
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		String sql="SELECT * FROM user WHERE name like:name";
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.addEntity(User.class);
		sqlQuery.setParameter("name", "李四");
		
		List<User> list = sqlQuery.list();
		System.out.println(list);
		
		session.getTransaction();
		session.close();
		
	}
	
	/**
	 * 使用Criteria查询所有
	 */
	@Test
	public void criteriaTest1(){
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		List<User> list = criteria.list();
		System.out.println(list);
		
		session.getTransaction().commit();
		session.close();
		
		
		
	}
	
	/**
	 * 使用Criteria分页查询
	 */
	@Test
	public void criteriaTest2(){
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.setFirstResult(0);
		criteria.setMaxResults(2);
		
		List<User> list = criteria.list();
		System.out.println(list);
		
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * 使用Criteria进行多条件查询
	 */
	@Test
	public void criteriaTest3() {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();

		Criteria c = s.createCriteria(User.class);
		c.add(Restrictions.or(Restrictions.eq("address", "上海"), Restrictions.eq("name", "李四")));
		
		List<User> us = c.list();
		System.out.println(us);
		
		s.getTransaction().commit();
		s.close();
	}

	/**
	 * 使用Criteria进行排序
	 */
	@Test
	public void criteriaTest4() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.addOrder(Order.asc("id"));//按照id从小到大排序
		List<User> us = criteria.list();
		System.out.println(us);

		
		session.getTransaction().commit();
		session.close();
	}

}
