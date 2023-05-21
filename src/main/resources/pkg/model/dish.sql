#sql("selectDishByName")
select * from dish where name like concat('%',ifnull(#para(0), ''), '%')
#end

#sql("selectDishByCondition")
select * from dish where true
    #if(notBlank(dish.name))
        and name like concat('%', #para(dish.name), '%')
    #end
#end

#sql("selectDishCount")
select count(*) from dish where true
    #if(notBlank(dish.name))
        and name like concat('%', #para(dish.name), '%')
    #end
#end

#sql("setDishUnable")
   update dish set enable = false;
#end

#sql("setDishEnable")
    update dish
    set enable = true
    where id in #para(0,"in")
#end

#sql("selectEnabled")
    select * from dish where enable = true
#end
