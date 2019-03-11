package businessLayer.admin;

/**
 * 这是一个管理员实体类，管理员拥有用户名以及密码。
 * @author 软英1702 马洪升 20175188
 * @data 2019-1-14 10:31
 *
 */
public class Admin {
    private String adminName;
    private String pwd;

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}

