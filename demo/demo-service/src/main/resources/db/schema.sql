-- DROP TABLE IF EXISTS t_coffee;

CREATE TABLE IF NOT EXISTS t_coffee (
    id bigint not null auto_increment,
    name varchar(255) comment '咖啡名称',
    price bigint not null comment '咖啡价格',
    create_time timestamp,
    update_time timestamp,
    primary key (id)
);
