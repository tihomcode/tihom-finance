create database if not exists manager;
use manager;

create table t_product(
  id varchar(50) not null comment '产品编号',
  name varchar(50) not null comment '产品名称',
  threshold_amount decimal(15,3) not null comment '起投金额',
  step_amount decimal(15,3) not null comment '投资步长',
  lock_term smallint not null comment '锁定期',
  reward_rate decimal(5,3) not null comment '收益率，0-100，百分比',
  status varchar(20) not null comment '状态，AUDINTING：审核中，IN_SELL：销售中，LOCKED：暂停销售，FINISHED：已结束',
  memo varchar(200) comment '备注',
  create_at datetime comment '创建时间',
  create_user varchar(20) comment '创建者',
  update_at datetime comment '更新时间',
  update_user varchar(20) comment '更新者',
  primary key (id)
)ENGINE=InnoDB default charset=utf8 collate=utf8_unicode_ci;

create database if not exists seller;
use seller;
create table t_order(
  order_id varchar(50) not null comment '订单编号',
  chan_id varchar(50) not null comment '渠道编号',
  product_id varchar(50) not null comment '产品编号',
  chan_user_id varchar(50) not null comment '渠道用户编号',
  order_type varchar(50) not null comment '类型，APPLY：申购，REDEEM：赎回',
  order_status varchar(50) not null comment '状态，INIT：初始化，PROCESS：处理中，SUCCESS：成功，FAIL：失败',
  outer_order_id varchar(50) not null comment '外部订单编号',
  amount decimal(15,3) not null comment '金额',
  memo varchar(200) comment '备注',
  create_at datetime comment '创建时间',
  update_at datetime comment '更新时间',
  primary key (order_id)
)ENGINE=InnoDB default charset=utf8 collate=utf8_unicode_ci;

create table verification_order(
  order_id varchar(50) not null comment '订单编号',
  chan_id varchar(50) not null comment '渠道编号',
  product_id varchar(50) not null comment '产品编号',
  chan_user_id varchar(50) not null comment '渠道用户编号',
  order_type varchar(50) not null comment '类型，APPLY：申购，REDEEM：赎回',
  outer_order_id varchar(50) not null comment '外部订单编号',
  amount decimal(15,3) not null comment '金额',
  create_at datetime comment '创建时间',
  primary key (order_id)
)ENGINE=InnoDB default charset=utf8 collate=utf8_unicode_ci;

-- 长款
select t.order_id from t_order t left join verification_order v on t.chan_id=?1 and t.outer_order_id=v.order_id where v.order_id is null
and t.create_at>=?2 and t.create_at<?3;

-- 漏单
select v.order_id from verification_order v left join t_order t on t.chan_id=?1 and v.outer_order_id=t.order_id where t.order_id is null
and v.create_at>=?2 and v.create_at<?3;

-- 不一致
select t.order_id from t_order t join verification_order v on t.chan_id=?1 and t.outer_order_id=v.order_id
where CONCAT_WS('|',t.chan_id,t.chan_user_id,t.product_id,t.order_type,t.amount,DATE_FORMAT(t.create_at,'%Y-%m-%d %H:%i:%s')) !=
CONCAT_WS('|',v.chan_id,v.chan_user_id,v.product_id,v.order_type,v.amount,DATE_FORMAT(v.create_at,'%Y-%m-%d %H:%i:%s'))
and t.create_at>=?2 and t.create_at<?3;

create database seller_backup;