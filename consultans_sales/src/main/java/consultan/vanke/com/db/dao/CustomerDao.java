package consultan.vanke.com.db.dao;

import androidx.room.*;
import consultan.vanke.com.db.Entity.Customer;
import consultan.vanke.com.db.Entity.User;

import java.util.List;

//注解配置sql语句
@Dao
public interface CustomerDao {
    //所有的CURD根据primary key进行匹配
//------------------------query------------------------
//简单sql语句，查询user表所有的column
    @Query("SELECT * FROM customer")
    List<Customer> getAll();

    //根据条件查询，方法参数和注解的sql语句参数一一对应
    @Query("SELECT * FROM customer WHERE uid IN (:userIds)")
    List<Customer> loadAllByIds(int[] userIds);

    //同上
    @Query("SELECT * FROM customer WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Customer findByName(String first, String last);

    //同上
    @Query("SELECT * FROM customer WHERE uid = :uid")
    Customer findByUid(int uid);

//-----------------------insert----------------------

    // OnConflictStrategy.REPLACE表示如果已经有数据，那么就覆盖掉
//数据的判断通过主键进行匹配，也就是uid，非整个user对象
//返回Long数据表示，插入条目的主键值（uid）
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Customer customer);

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Customer... customers);

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Customer> customers);

    //---------------------update------------------------
//更新已有数据，根据主键（uid）匹配，而非整个user对象
//返回类型int代表更新的条目数目，而非主键uid的值。
//表示更新了多少条目
    @Update()
    int update(Customer customer);

    //同上
    @Update()
    int updateAll(User... user);

    //同上
    @Update()
    int updateAll(List<Customer> customers);

    //-------------------delete-------------------
//删除user数据，数据的匹配通过主键uid实现。
//返回int数据表示删除了多少条。非主键uid值。
    @Delete
    int delete(Customer customer);

    //同上
    @Delete
    int deleteAll(List<Customer> customers);

    //同上
    @Delete
    int deleteAll(Customer... customers);

    //全部删除
    @Query("delete from customer")
    int deleteAllUser();
}
