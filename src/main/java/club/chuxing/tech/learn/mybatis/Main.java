package club.chuxing.tech.learn.mybatis;

import java.io.InputStream;

import club.chuxing.tech.learn.mybatis.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Created by xuezhangying on 3/6/16.
 */
public class Main {
    public static void main(String[] args) {
        SqlSession session = null;
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession();
            // 1st
            Student student = session.selectOne("com.meituan.learn.mybatis.StudentMapper.selectStudent", 1);

            // 2nd
            // sqlSessionFactory.getConfiguration().addMapper(StudentDao.class);
            // StudentDao mapper = session.getMapper(StudentDao.class);
            // Student student = mapper.selectStudent(8);

            System.out.println(student.getName() + ", " + student.getAge());

            /*Student s = new Student();
            s.setAge(100);
            s.setName("aaa");
            s.setId(123);
            session.insert("com.meituan.learn.mybatis.StudentMapper.insert", s);
            session.commit();*/
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }
}
