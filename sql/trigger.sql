drop trigger if exists tr_insert_data_collect;
create trigger tr_insert_data_collect
after insert on data_basic for each row

BEGIN

-- 忽略非法数据逻辑
IF new.STAND_ID LIKE '%9999%' OR new.STATION_ID LIKE '%9999%' OR new.GROUP_ID LIKE '%9999%' OR ((new.PT1 <= 0 OR new.PT1 >=1) AND (new.PT2 <= 0 OR new.PT2 >=1)) THEN
-- 什么也不干会报错，select语句也会报错
insert into illegal_data_id(id) values (new.ID);
-- 修复错误数据
IF ((new.PT1 <= 0 OR new.PT1 >=1) AND (new.PT2 <= 0 OR new.PT2 >=1) AND (new.PT3 <= 0 OR new.PT3 >=1)) AND (new.STAND_ID NOT LIKE '%9999%' AND new.STATION_ID NOT LIKE '%9999%' AND new.GROUP_ID NOT LIKE '%9999%') THEN

select FT3Q_PRICE,JQI_PRICE,QQI_PRICE from data_price group by ID desc limit 1 into
@FT3Q_PRICE,@JQI_PRICE,@QQI_PRICE;

SELECT ID FROM data_basic WHERE STAND_ID = new.STAND_ID AND STATION_ID = new.STATION_ID AND PT1 BETWEEN 0.01 AND 0.99 ORDER BY OP_TIME DESC LIMIT 1
into @ID;

insert into data_collect(STAND_ID,STATION_ID,GROUP_ID,OP_TIME,PT1,PT2,TE1,TE2,CVI1,QI,FT1,FT1Q,PT3,PT4,TE3,TE4,FT2,FT3,FC1V1,LT1,QQI,JQI,FT3Q,FT2Q,FT3Q_PRICE,JQI_PRICE,QQI_PRICE,BATCH_NUMBER)
SELECT STAND_ID,STATION_ID,GROUP_ID,new.OP_TIME,PT1,PT2,TE1,TE2,CVI1,QI,FT1,FT1Q,PT3,PT4,TE3,TE4,FT2,FT3,FC1V1,LT1,QQI,JQI,FT3Q,FT2Q,@FT3Q_PRICE,@JQI_PRICE,@QQI_PRICE,date_format(new.OP_TIME, '%y%m%d%H%i')
FROM data_basic WHERE ID = @ID;

End IF;

ELSE

-- 插入收集表 这里在data_price表里，取一下水价，热价，电价 然后更新到collect里，然后同步更新basic里面的值。

select FT3Q_PRICE,JQI_PRICE,QQI_PRICE from data_price group by ID desc limit 1 into
@FT3Q_PRICE,@JQI_PRICE,@QQI_PRICE;

insert into data_collect(STAND_ID,STATION_ID,GROUP_ID,OP_TIME,PT1,PT2,TE1,TE2,CVI1,QI,FT1,FT1Q,PT3,PT4,TE3,TE4,FT2,FT3,FC1V1,LT1,QQI,JQI,FT3Q,FT2Q,FT3Q_PRICE,JQI_PRICE,QQI_PRICE,BATCH_NUMBER)
values(new.STAND_ID,new.STATION_ID,new.GROUP_ID,new.OP_TIME,new.PT1,new.PT2,new.TE1,new.TE2,new.CVI1,new.QI,new.FT1,new.FT1Q,new.PT3,new.PT4,new.TE3,
new.TE4,new.FT2,new.FT3,new.FC1V1,new.LT1,new.QQI,new.JQI,new.FT3Q,new.FT2Q,@FT3Q_PRICE,@JQI_PRICE,@QQI_PRICE,date_format(new.OP_TIME, '%y%m%d%H%i'));

-- 机组表逻辑
IF new.STAND_ID IS NOT NULL THEN
IF new.STAND_ID not in (select STAND_ID from data_stand where STAND_ID = new.STAND_ID and STATION_ID = new.STATION_ID) THEN -- 如果对应换热站id和机组id在机组表里没有

insert into data_stand (STAND_ID,STAND_NAME,STATION_ID) values (new.STAND_ID,new.STAND_NAME,new.STATION_ID);

ELSE
update data_stand set STAND_NAME = new.STAND_NAME where STAND_ID = new.STAND_ID and STATION_ID = new.STATION_ID;
End IF;
End IF;

-- 换热站逻辑
IF new.STATION_ID IS NOT NULL THEN
IF new.STATION_ID not in (select STATION_ID from data_station where STATION_ID = new.STATION_ID and GROUP_ID = new.GROUP_ID) THEN -- 如果对应分所id和换站站id没有在换热站表里
insert into data_station (STATION_ID,STATION_NAME,GROUP_ID) values (new.STATION_ID,new.STATION_NAME,new.GROUP_ID);
ELSE
update data_station set STATION_NAME = new.STATION_NAME where STATION_ID = new.STATION_ID and GROUP_ID = new.GROUP_ID;
End IF;
End IF;

-- 分所逻辑
IF new.GROUP_ID IS NOT NULL THEN
IF new.GROUP_ID not in (select GROUP_ID from data_group where GROUP_ID = new.GROUP_ID) THEN -- 如果对应分所id和换站站id没有在换热站表里
insert into data_group (GROUP_ID,GROUP_NAME) values (new.GROUP_ID,new.GROUP_NAME);
ELSE
update data_group set GROUP_NAME = new.GROUP_NAME where GROUP_ID = new.GROUP_ID;
End IF;
End IF;
End IF;-- 忽略非法数据逻辑结束
END;