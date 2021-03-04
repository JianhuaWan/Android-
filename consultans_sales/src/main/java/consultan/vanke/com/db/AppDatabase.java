package consultan.vanke.com.db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import consultan.vanke.com.db.Entity.Customer;
import consultan.vanke.com.db.Entity.User;
import consultan.vanke.com.db.dao.CustomerDao;
import consultan.vanke.com.db.dao.UserDao;

//注解指定了database的表映射实体数据以及版本等信息
// 指定version = 2（之前为1）
@Database(entities = {User.class, Customer.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //RoomDatabase提供直接访问底层数据库实现，我们通过定义抽象方法返回具体Dao
//然后进行数据库增删该查的实现。
    public abstract UserDao userDao();

    public abstract CustomerDao customerDao();

    //数据库变动添加Migration
    public static final Migration MIGRATION_1_2 = new Migration(1, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //数据库的具体变动，我是在之前的user表中添加了新的column，名字是age。
            //类型是integer，不为空，默认值是0
//            database.execSQL("ALTER TABLE user "
//                    + " ADD COLUMN age INTEGER NOT NULL DEFAULT 0");
        }
    };
}
