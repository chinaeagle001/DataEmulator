package club.chuxing.tech.learn.mybatis.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import club.chuxing.tech.learn.mybatis.domain.Student;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static club.chuxing.tech.learn.utils.PrintUtil.*;

/**
 * Created by xuezhangying on 3/25/16.
 */

@ContextConfiguration(locations = "classpath:applicationContext-common.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentDaoTest {
    @Autowired
    private StudentDao dao;
    @Test
    public void testSelectStudent() throws Exception {
        Student student = dao.selectStudent(1);
        print(student);
    }
}