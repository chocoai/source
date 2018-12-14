/*
Date: 2018-11-27 09:19:58 /
*/

CREATE TABLE js_ding_sync_elearing(
  createTime char(12) COMMENT '创建时间:年月日时分',
  syncName char(1) COMMENT '同步方法名字:0,组织同步;1,岗位同步;2,人员同步',
  message text COMMENT '同步数据后返回的信息'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用于保存调用同步e-learning接口后返回的结果数据';
