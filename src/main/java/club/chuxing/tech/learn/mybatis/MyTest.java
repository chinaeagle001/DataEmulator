package club.chuxing.tech.learn.mybatis;

import club.chuxing.tech.learn.mybatis.dao.StudentDao;
import club.chuxing.tech.learn.mybatis.domain.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static club.chuxing.tech.learn.utils.PrintUtil.print;

/**
 * Created by xuezhangying on 3/6/16.
 */
public class MyTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-common.xml");
        StudentDao  dao = context.getBean(StudentDao.class);
        Student student = dao.selectStudent(1);
        print(student);
    }
}
