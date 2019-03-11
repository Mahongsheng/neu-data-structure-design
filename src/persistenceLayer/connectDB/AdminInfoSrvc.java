package persistenceLayer.connectDB;

import businessLayer.admin.Admin;
import persistenceLayer.connectDB.AdminInfoDao;

import java.util.List;

/**
 * 调用DAO实现方法
 * @author 软英1702 马洪升 20175188
 */
public class AdminInfoSrvc {

    /**
     * 我们使用这个方法从数据库中获取所有用户数据，并放到list集合
     * @return list
     */
    public List<Admin> search() {
        AdminInfoDao adminDao = new AdminInfoDao();
        return adminDao.search();
    }

    /**
     * 我们使用这个方法来判断是否要添加的用户已存在，判断不存在后进行添加。
     * @param userName
     * @param pwd
     * @return
     */
    public boolean insert(String userName,String pwd) {
        // 如果用户名或密码为空，不进行添加
        if(userName.isEmpty() || pwd.isEmpty()) {
            return false;
        }
        AdminInfoDao adminDao = new AdminInfoDao();//创建一个数据库操作类
        List<Admin> list = adminDao.search();//读取数据库所有的用户数据，并放到list集合
        //检测用户名是否已存在
        for(int i=0;i<list.size(); i++) {
            //如果用户名已存在，不添加
            if( list.get(i).getAdminName().equals(userName)) {
                return false;
            }
        }
        adminDao.insert(userName, pwd);//添加用户
        return true;
    }

    /**
     * 我们使用这个方法来更改用户的密码。
     * @param name
     * @param pwd
     * @return
     */
    public boolean updateByName(String name,String pwd) {
        //更改用户密码
        //如果姓名以及密码不为空
        if(!name.isEmpty() && !pwd.isEmpty()) {
            AdminInfoDao adminDao = new AdminInfoDao();
            List<Admin> list= adminDao.search();
            //查找到要更新的用户
            for(int i=0;i<list.size();i++){
                if(list.get(i).getAdminName().equals(name)) {
                    adminDao.updateByName(name, pwd);
                    return true;
                }
            }
        }
        //System.out.println("end");
        return false;
    }

    /**
     * 我们使用这个方法根据用户名来查找特定用户。
     * @param name
     * @return Admin
     */
    public Admin searchByName(String name) {
        //根据名字查找用户
        AdminInfoDao adminDao = new AdminInfoDao();
        List<Admin> list = adminDao.search();
        if(name != null) {
            for(int i=0;i<list.size();i++) {
                if(list.get(i).getAdminName().equals(name)) {
                    return list.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 我们使用这个方法来根据用户名来删除特定用户。
     * @param name
     * @return
     */
    public boolean deleteByName(String name){
        //根据用户名删除用户
        AdminInfoDao adminDao=new AdminInfoDao();
        List<Admin> list = adminDao.search();
        for(int i=0;i<list.size();i++) {
            if(list.get(i).getAdminName().equals(name)) {
                adminDao.deleteByName(name);
                return true;
            }
        }
        return false;
    }

}
