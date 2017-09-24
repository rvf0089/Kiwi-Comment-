package blake.sprints1;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/18.
 */

public class Information implements Serializable {
    private String name;
    private String addresss;
    private String phonenumber;

    public Information(String name,String addresss,String phonenumber){
        this.name=name;
        this.addresss=addresss;
        this.phonenumber=phonenumber;
    }

    public String getName() {
        return name;
    }

    public String getAddresss() {
        return addresss;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}
