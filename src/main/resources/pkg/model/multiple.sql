#sql("dishAnalysisByDate")
    select d.id as id, name, sum(dr.demand) as value
        from dish d left join dish_rate dr on d.id = dr.dish_id
        where time > #para(0) and time < #para(1)
        group by dish_id
        order by value desc;
#end

#sql("dishAnalysisById")
    select d.id,time as name, demand as value
    from dish d left join dish_rate dr on d.id = dr.dish_id
    where d.id = #para(0) and time > #para(1) and time < #para(2)
    order by time;
#end

#sql("userAnalysisById")
    select user_id as id,name,
    count(dish_id) as value
    from  user_rate ur right join dish d on d.id = ur.dish_id
    where ur.user_id = #para(0)
    group by dish_id;
#end

#sql("userAnalysisByDate")
select user_id as id,
       (
           select name
           from dish d2
           where ur.dish_id = d2.id
       ) as name,
       count(dish_id) as value
from  user_rate ur
where ur.user_id = #para(0) and time>#para(1) and time<#para(2)
group by dish_id;
#end

#sql("selectUserFavorite")
select *
from user_rate ur left join dish d on d.id = ur.dish_id
where user_id = 1;
#end

#sql("selectUrByUserId")
select *
from user_rate
where user_id = #para(0)
order by time desc
;
#end


