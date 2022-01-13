package spider.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Table(name = "`spider_data`")
public class spiderData {
    @Id
    @Column(name = "`id`")
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 数据注册方式 1-不注册到中枢 2-json贡献 3-csv贡献
     */
    @Column(name = "`biz_type`")
    private Integer bizType;

    /**
     * 数据类型 1-csv,2-json,10-其他
     */
    @Column(name = "`data_type`")
    private Integer dataType;

    /**
     * 注册到odps返回data_id
     */
    @Column(name = "`data_id`")
    private Long dataId;

    /**
     * 贡献记录IDcsop_business data_contribution id
     */
    @Column(name = "`data_contribution_id`")
    private Long dataContributionId;

    /**
     * 中枢code
     */
    @Column(name = "`code`")
    private Long code;

    /**
     * 注册到odps返回table_name
     */
    @Column(name = "`table_name`")
    private String tableName;

    /**
     * 注册到odps返回url
     */
    @Column(name = "`odps_url`")
    private String odpsUrl;

    /**
     * 0-失败 1-成功
     */
    @Column(name = "`state`")
    private Integer state;

    /**
     * 创建时间
     */
    @Column(name = "`created_at`")
    private Date createdAt;

    /**
     * 修改时间
     */
    @Column(name = "`updated_at`")
    private Date updatedAt;

    /**
     * 错误信息
     */
    @Column(name = "`err`")
    private String err;

    /**
     * 描述
     */
    @Column(name = "`description`")
    private String description;

    /**
     * 数据详情页地址
     */
    @Column(name = "`website`")
    private String website;

    /**
     * 数据源地址
     */
    @Column(name = "`url`")
    private String url;

    public static final String ID = "id";

    public static final String DB_ID = "id";

    public static final String NAME = "name";

    public static final String DB_NAME = "name";

    public static final String BIZ_TYPE = "bizType";

    public static final String DB_BIZ_TYPE = "biz_type";

    public static final String DATA_TYPE = "dataType";

    public static final String DB_DATA_TYPE = "data_type";

    public static final String DATA_ID = "dataId";

    public static final String DB_DATA_ID = "data_id";

    public static final String DATA_CONTRIBUTION_ID = "dataContributionId";

    public static final String DB_DATA_CONTRIBUTION_ID = "data_contribution_id";

    public static final String CODE = "code";

    public static final String DB_CODE = "code";

    public static final String TABLE_NAME = "tableName";

    public static final String DB_TABLE_NAME = "table_name";

    public static final String ODPS_URL = "odpsUrl";

    public static final String DB_ODPS_URL = "odps_url";

    public static final String STATE = "state";

    public static final String DB_STATE = "state";

    public static final String CREATED_AT = "createdAt";

    public static final String DB_CREATED_AT = "created_at";

    public static final String UPDATED_AT = "updatedAt";

    public static final String DB_UPDATED_AT = "updated_at";

    public static final String ERR = "err";

    public static final String DB_ERR = "err";

    public static final String DESCRIPTION = "description";

    public static final String DB_DESCRIPTION = "description";

    public static final String WEBSITE = "website";

    public static final String DB_WEBSITE = "website";

    public static final String URL = "url";

    public static final String DB_URL = "url";
}