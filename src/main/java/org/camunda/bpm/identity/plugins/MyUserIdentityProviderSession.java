package org.camunda.bpm.identity.plugins;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.identity.*;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.identity.plugins.vo.MyUserGroupEntity;
import org.camunda.bpm.identity.plugins.vo.MyUserUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author eagle_daiqiang
 */
public class MyUserIdentityProviderSession implements ReadOnlyIdentityProvider {
    private static final Logger logger = LoggerFactory.getLogger(MyUserIdentityProviderSession.class);

    private MyUserIdentityConfigration myUserIdentityConfigration;

    public MyUserIdentityProviderSession(MyUserIdentityConfigration myUserIdentityConfigration) {
        this.myUserIdentityConfigration = myUserIdentityConfigration;
    }

    @Override public User findUserById(String userId) {
        return createUserQuery(org.camunda.bpm.engine.impl.context.Context.getCommandContext())
                .userId(userId)
                .singleResult();
    }

    @Override public UserQuery createUserQuery() {
        return new MyUserQueryImpl(org.camunda.bpm.engine.impl.context.Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
    }

    @Override public UserQuery createUserQuery(CommandContext commandContext) {
        return new MyUserQueryImpl();
    }

    @Override public NativeUserQuery createNativeUserQuery() {
        throw new BadUserRequestException("Native user queries are not supported for identity service provider.");
    }

    @Override public boolean checkPassword(String userId, String password) {
        // prevent a null password
        if(password == null) {
            return false;
        }

        // engine can't work without users
        if(userId == null || userId.isEmpty()) {
            return false;
        }

//        MyUserUserEntity user = this.getUserByUsername(userId);
        MyUserUserEntity user = (MyUserUserEntity)findUserById(userId);
        if(user!=null&&password.equals(user.getPassword())){
            return true;
        }
        return false;
    }

    private MyUserGroupEntity getGroupByGroupid(String groupid){
        Map<String,Object> group = MyUserTestDatas.groupTable.get(groupid);
        if(group!=null){
            MyUserGroupEntity groupEntity = new MyUserGroupEntity();
            groupEntity.setTableIdentity(String.valueOf(group.get("tableIdentity")));
            groupEntity.setId((String)group.get("id"));
            groupEntity.setName((String)group.get("name"));
            groupEntity.setRevision((Integer)group.get("revision"));
            groupEntity.setType((String)group.get("type"));

            return groupEntity;
        }
        return null;
    }

    @Override public Group findGroupById(String groupId) {
        return  this.getGroupByGroupid(groupId);
    }

    @Override public GroupQuery createGroupQuery() {
        return new MyGroupQueryImpl((org.camunda.bpm.engine.impl.context.Context.getProcessEngineConfiguration().getCommandExecutorTxRequired()));
    }

    @Override public GroupQuery createGroupQuery(CommandContext commandContext) {
        return new MyGroupQueryImpl();
    }

    @Override public Tenant findTenantById(String tenantId) {
        return null;
    }

    @Override public TenantQuery createTenantQuery() {
        return new MyUserTenantQueryImpl((org.camunda.bpm.engine.impl.context.Context.getProcessEngineConfiguration().getCommandExecutorTxRequired()));
    }

    @Override public TenantQuery createTenantQuery(CommandContext commandContext) {
        return new MyUserTenantQueryImpl();
    }

    @Override public void flush() {
        // nothing to do
    }

    @Override public void close() {
        // nothing to do
    }

    public MyUserIdentityConfigration getMyUserIdentityConfigration() {
        return myUserIdentityConfigration;
    }

    public void setMyUserIdentityConfigration(
            MyUserIdentityConfigration myUserIdentityConfigration) {
        this.myUserIdentityConfigration = myUserIdentityConfigration;
    }


    public long findGroupCountByQueryCriteria(MyGroupQueryImpl groupQuery) {
        return findGroupByQueryCriteria(groupQuery).size();
    }

    public List<Group> findGroupByQueryCriteria(MyGroupQueryImpl query) {
        List<Group> groupList = new ArrayList<>();
        // add additional filters from query
        if(query.getId() != null) {
            //分组id - 分组code
            MyUserGroupEntity groupEntity = this.getGroupByGroupid(query.getId());
            if(groupEntity!=null){
                groupList.add(groupEntity);
            }
        }
        if(query.getName() != null) {
            //分组名称
        }
        if(query.getNameLike() != null) {
            //分组名称 - 模糊查询
        }
        if(query.getUserId() != null) {
            //用户名
            List<MyUserGroupEntity> userGroupEntities = this.getGroupByUsername(query.getUserId());
            groupList.addAll(userGroupEntities);
        }
        return groupList;
    }

    private List<MyUserGroupEntity> getGroupByUsername(String username){
        String groupMap = MyUserTestDatas.userGroupTable.get(username);
        List<MyUserGroupEntity> groupEntities = new ArrayList<>();
        if(groupMap!=null){
            String[] groupids = groupMap.split(",");
            for(int i=0;i<groupids.length;i++){
                String groupId = groupids[i];
                MyUserGroupEntity groupEntity = this.getGroupByGroupid(groupId);
                if(groupEntity!=null){
                    groupEntities.add(groupEntity);
                }
            }
        }
        return groupEntities;
    }

    public long findUserCountByQueryCriteria(MyUserQueryImpl query){
        return this.findUserByQueryCriteria(query).size();
    }

    public List<User> findUserByQueryCriteria(MyUserQueryImpl query) {
        if(query.getGroupId() != null) {
            return findUsersByGroupId(query);
        } else {
            return findUsersWithoutGroupId(query, false);
        }
    }

    /**
     * 通过组查对应用户信息
     * @param query
     * @return
     */
    protected List<User> findUsersByGroupId(MyUserQueryImpl query) {
        List<User> users = new ArrayList<>();
        if(query.getGroupId()!=null){
            String username = MyUserTestDatas.groupUserTable.get(query.getGroupId());
            if(username!=null){
                MyUserUserEntity user = getUserByUsername(username);
                if(user!=null){
                    users.add(user);
                }
            }
        }
        return users;
    }

    private MyUserUserEntity getMyUserUserEntity(Map<String, Object> user) {
        MyUserUserEntity userEntity = new MyUserUserEntity();

        userEntity.setTableIdentity(String.valueOf(user.get("tableIdentity")));
        userEntity.setId((String)user.get("id"));
        userEntity.setFirstName((String)user.get("firstName"));
        userEntity.setLastName((String)user.get("lastName"));
        userEntity.setEmail((String)user.get("email"));
        //必须是dbpassword()，方法setPassword()对应属性为newPassword.
        userEntity.setDbPassword((String)user.get("password"));
        return userEntity;
    }

    private MyUserUserEntity getUserByUsername(String username){
        Map user = MyUserTestDatas.userTable.get(username);
        if(user!=null) {
            MyUserUserEntity userEntity = getMyUserUserEntity(user);
            return userEntity;
        }
        return null;
    }

    /**
     * 通过条件查用户信息
     * @param query
     * @param ignorePagination 是否忽略分页
     * @return
     */
    public List<User> findUsersWithoutGroupId(MyUserQueryImpl query, boolean ignorePagination){
        List<User> users = new ArrayList<>();
        if(query.getId() != null) {
            //用户名-单个
            MyUserUserEntity user = getUserByUsername(query.getId());
            if(user!=null){
                users.add(user);
            }
        }else if(query.getIds() != null && query.getIds().length > 0) {
            //用户名-批量
        }
       /* if(query.getEmail() != null) {

        }
        if(query.getEmailLike() != null) {
        }
        if(query.getFirstName() != null) {
        }
        if(query.getFirstNameLike() != null) {
        }
        if(query.getLastName() != null) {
        }
        if(query.getLastNameLike() != null) {
        }*/
       return users;
    }

}
