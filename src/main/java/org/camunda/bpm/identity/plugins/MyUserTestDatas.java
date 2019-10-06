package org.camunda.bpm.identity.plugins;

import java.util.HashMap;
import java.util.Map;

/**
 * @author eagle_daiqiang
 * 用户组测试数据
 */
public class MyUserTestDatas {
    public static final Map<String,Map<String,Object>> userTable = new HashMap<String,Map<String,Object>>();
    public static final Map<String,String> groupUserTable = new HashMap<>();
    public static final Map<String,String> userGroupTable = new HashMap<>();
    public static final Map<String,Map<String,Object>> groupTable = new HashMap<>();

    static {
        //用户定义
        Map eagle = new HashMap();
        eagle.put("tableIdentity",1);
        eagle.put("id","eagle");//用户名
        eagle.put("firstName","代");
        eagle.put("lastName","强");
        eagle.put("email","daiqiang987@pingan.com.cn");
        eagle.put("password","123456");
        userTable.put("eagle",eagle);

        Map admin = new HashMap();
        admin.put("tableIdentity",2);
        admin.put("id","admin");
        admin.put("firstName","admin");
        admin.put("lastName","admin");
        admin.put("email","admin@pingan.com.cn");
        admin.put("password","123456");
        userTable.put("admin",admin);

        Map demo = new HashMap();
        demo.put("tableIdentity",3);
        demo.put("id","demo");
        demo.put("firstName","demo");
        demo.put("lastName","demo");
        demo.put("email","demo@pingan.com.cn");
        demo.put("password","123456");
        userTable.put("demo",demo);

        Map sale1 = new HashMap();
        sale1.put("tableIdentity",4);
        sale1.put("id","sale1");
        sale1.put("firstName","sale1");
        sale1.put("lastName","sale1");
        sale1.put("email","sale1@pingan.com.cn");
        sale1.put("password","123456");
        userTable.put("sale1",sale1);

        //定义组
        Map camundaAdmin = new HashMap();
        camundaAdmin.put("tableIdentity",1);
        camundaAdmin.put("id","camunda-admin");
        camundaAdmin.put("name","管理员");
        camundaAdmin.put("type","workflow");
        camundaAdmin.put("revision",1);
        groupTable.put("camunda-admin",camundaAdmin);

        Map sales = new HashMap();
        sales.put("tableIdentity",2);
        sales.put("id","sales");
        sales.put("name","销售");
        sales.put("type","workflow");
        sales.put("revision",1);
        groupTable.put("sales",sales);

        Map accounting = new HashMap();
        accounting.put("tableIdentity",3);
        accounting.put("id","accounting");
        accounting.put("name","财务");
        accounting.put("type","workflow");
        accounting.put("revision",1);
        groupTable.put("accounting",accounting);

        Map management = new HashMap();
        management.put("tableIdentity",4);
        management.put("id","management");
        management.put("name","管理");
        management.put("type","workflow");
        management.put("revision",1);
        groupTable.put("management",management);

        //组-用户关系定义
        groupUserTable.put("camunda-admin","eagle");
        groupUserTable.put("camunda-admin","admin");
        groupUserTable.put("camunda-admin","demo");

        groupUserTable.put("sales","sale1");

        groupUserTable.put("accounting","sale1");
        groupUserTable.put("management","sale1");

        //用户-组关系定义
        userGroupTable.put("eagle","camunda-admin");
        userGroupTable.put("admin","camunda-admin");
        userGroupTable.put("demo","camunda-admin");

        userGroupTable.put("sale1","sales,accounting,management");
    }
}
