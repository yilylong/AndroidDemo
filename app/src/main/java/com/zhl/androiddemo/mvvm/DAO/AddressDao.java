package com.zhl.androiddemo.mvvm.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zhl.androiddemo.mvvm.bean.Address;

import java.util.List;

/**
 * 描述：数据操作类，包含用于访问数据库的方法
 *
 * @Dao ： 标注数据库操作的类。
 * @Query ： 包含所有Sqlite语句操作。
 * @Insert ： 标注数据库的插入操作。
 * @Delete ： 标注数据库的删除操作。
 * @Update ： 标注数据库的更新操作。
 * Created by zhaohl on 2020/3/5.
 */
@Dao
public interface AddressDao {
    @Query("SELECT * FROM address WHERE aid=:id")
    public Address getAddressByid(int id);

    @Query("SELECT * FROM address WHERE pid=:pid")
    public List<Address> getAddressByPersonID(String pid);

    //查询所有数据
    @Query("Select * from address")
    List<Address> getAll();

    //删除全部数据
    @Query("DELETE FROM address")
    public void deleteAll();

    //一次插入单条数据 或 多条
    /**
     * 在@Insert注解中有conflict用于解决插入数据冲突的问题，其默认值为OnConflictStrategy.ABORT
     * OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务
     * OnConflictStrategy.ROLLBACK：冲突策略是回滚事务
     * OnConflictStrategy.ABORT：冲突策略是终止事务
     * OnConflictStrategy.FAIL：冲突策略是事务失败
     * OnConflictStrategy.IGNORE：冲突策略是忽略冲突
     *
     * @param addresses
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Address... addresses);

    //一次删除单条数据 或 多条
    @Delete
    public void delete(Address... addresses);

    //一次更新单条数据 或 多条
    @Update
    public void update(Address... addresses);


}
