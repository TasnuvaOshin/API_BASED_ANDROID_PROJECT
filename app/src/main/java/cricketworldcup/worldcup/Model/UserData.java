package cricketworldcup.worldcup.Model;

public class UserData {
    String username;
    String useremail;
    String userphoneno;
    String userdsmcode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserphoneno() {
        return userphoneno;
    }

    public void setUserphoneno(String userphoneno) {
        this.userphoneno = userphoneno;
    }

    public String getUserdsmcode() {
        return userdsmcode;
    }

    public void setUserdsmcode(String userdsmcode) {
        this.userdsmcode = userdsmcode;
    }

    public UserData() {
    }

    public UserData(String username, String useremail, String userphoneno, String userdsmcode) {
        this.username = username;
        this.useremail = useremail;
        this.userphoneno = userphoneno;
        this.userdsmcode = userdsmcode;
    }
}
