package spider.pojo;

import lombok.Data;

@Data
public class SpiderDataVO {

    private String name;
    private String description;
    private String website;
    private String url;
    private String bizType; //'数据注册方式 1-不注册到中枢 2-json贡献 3-csv贡献',
    private String dataType; //'数据类型 1-csv,2-json,10-其他',
    private String dataId;  //'注册到odps返回data_id',
    private String dataContributionId;  //'贡献记录IDcsop_business data_contribution id',
    private String code; //'中枢code',
    private String tableName; //'注册到odps返回table_name',
    private String odpsUrl; //'注册到odps返回url',
    private String state; // '0-失败 1-成功',
}

//CREATE TABLE `spider_data` (
//  `name` varchar(2000) DEFAULT NULL COMMENT '标题',
//  `description` longtext COMMENT '描述',
//  `website` longtext COMMENT '数据详情页地址',
//  `url` longtext COMMENT '数据源地址',
//  `biz_type` int(11) DEFAULT NULL COMMENT '数据注册方式 1-不注册到中枢 2-json贡献 3-csv贡献',
//  `data_type` int(11) DEFAULT NULL COMMENT '数据类型 1-csv,2-json,10-其他',
//  `data_id` bigint(20) DEFAULT NULL COMMENT '注册到odps返回data_id',
//  `data_contribution_id` bigint(20) DEFAULT NULL COMMENT '贡献记录IDcsop_business data_contribution id',
//  `code` bigint(20) DEFAULT NULL COMMENT '中枢code',
//  `table_name` varchar(255) DEFAULT NULL COMMENT '注册到odps返回table_name',
//  `odps_url` varchar(255) DEFAULT NULL COMMENT '注册到odps返回url',
//  `state` int(11) DEFAULT NULL COMMENT '0-失败 1-成功',
//  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
//  `updated_at` datetime DEFAULT NULL COMMENT '修改时间'
//) ENGINE=InnoDB DEFAULT CHARSET=latin1;