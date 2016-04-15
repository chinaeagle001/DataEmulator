package club.chuxing.tech.learn.mybatis.dao;

import club.chuxing.tech.learn.mybatis.domain.Student;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
/**
 * Created by xuezhangying on 3/6/16.
 */
@Component
public interface StudentDao {
    @Select("select * from student where id = #{id}")
    Student selectStudent(int id);
}
