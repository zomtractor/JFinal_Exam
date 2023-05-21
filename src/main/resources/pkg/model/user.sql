#sql("selectUserByName")
    select * from user
    where username like concat('%', ifnull(#para(0), ''), '%')
#end

#sql("selectUserByUserName")
            select * from user
            where username = #para(0)
#end


#sql("selectUserByCondition")
    select * from user where true
    #if(notBlank(user.username))
        and username like concat('%', #para(user.username), '%')
    #end
    #if(notBlank(user.name))
        and name like concat('%', #para(user.name), '%')
    #end
#end

#sql("selectUserCount")
    select count(*) from user where true
    #if(notBlank(user.username))
        and username like concat('%', #para(user.username), '%')
    #end
    #if(notBlank(user.name))
        and name like concat('%', #para(user.name), '%')
    #end
#end