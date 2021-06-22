# 创建附件信息表

drop table if exists `file_upload`.`attach`;

create table `file_upload`.`attach`
(
    `attach_id`   varchar(36)  not null comment '主键id',
    `attach_name` varchar(100) not null comment '附件名',
    `save_path`   varchar(100) not null comment '存储路径',
    `size`        int          not null default 0 comment '存储路径',
    `attach_type` varchar(30)  not null comment '附件类型',
    `upload_by`   varchar(50)  not null comment '上传者',
    `upload_time` timestamp             default current_timestamp comment '上传时间',
    primary key (`attach_id`)
) engine = InnoDB
  default charset = utf8 comment '附件信息表'
;
