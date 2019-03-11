package businessLayer.admin;

import persistenceLayer.connectDB.AdminInfoSrvc;

import java.util.List;

/**
 * 这是一个管理员操作类，我们调用这个类中的方法来进行一系列操作。
 * @author 软英1702 马洪升 20175188
 * @method 登录、添加管理员、更改管理员密码、删除管理员。
 */
public class AdminOpr {
    private AdminInfoSrvc adminInfoSrvc = new AdminInfoSrvc();

    /**
     * 输入用户名与密码，调用数据库进行核查，无误后登录
     * @param name
     * @param pwd
     * @return
     */
    public boolean login(String name, String pwd){
        List<Admin> admins = adminInfoSrvc.search();
        for (Admin admin : admins){
            if (admin.getAdminName().equals(name) && admin.getPwd().equals(pwd)){
                return true;
            }
        }
        return false;
    }

    /**
     * 添加新的用户
     * @param name
     * @param pwd
     * @return
     */
    public boolean addAdmin(String name, String pwd){
        return adminInfoSrvc.insert(name,pwd);
    }

    /**
     * 改变用户密码
     * @param name
     * @param pwd
     * @return
     */
    public boolean changePwd(String name, String pwd){
        return adminInfoSrvc.updateByName(name,pwd);
    }

    /**
     * 删除指定用户
     * @param name
     * @return
     */
    public boolean deleteAdmin(String name){
        return adminInfoSrvc.deleteByName(name);
    }


}
