package 模块信息收集;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "sys_module")
public class SysModule implements Serializable {
    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 模块编号
     */
    @Column(name = "module_code")
    private String moduleCode;

    /**
     * 模块名称
     */
    @Column(name = "module_name")
    private String moduleName;

    /**
     * 模块分类[00-应用,01-菜单]
     */
    @Column(name = "module_class")
    private String moduleClass;

    /**
     * 模块类型：module-模块；menu-菜单；page-页面；operation-操作
     */
    @Column(name = "module_type")
    private String moduleType;

    /**
     * 操作类型: add-新增；del-删除；mod-修改；list-列表查询；detail-详情查询；imp-导入；exp-导出；status-状态更改
     */
    @Column(name = "module_sub_type")
    private String moduleSubType;

    /**
     * 父模块编号
     */
    @Column(name = "parent_code")
    private String parentCode;

    /**
     * 根模块编号
     */
    @Column(name = "root_code")
    private String rootCode;

    /**
     * 是否需要登录[0-否,1-是]
     */
    @Column(name = "need_login")
    private String needLogin;

    /**
     * 是否需要权限校验[0-否,1-是]
     */
    @Column(name = "need_verify")
    private String needVerify;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标[url、font_awesome]
     */
    private String icon;

    /**
     * 内容 [访问UR]
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人帐号
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新人帐号
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取模块编号
     *
     * @return module_code - 模块编号
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * 设置模块编号
     *
     * @param moduleCode 模块编号
     */
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * 获取模块名称
     *
     * @return module_name - 模块名称
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 设置模块名称
     *
     * @param moduleName 模块名称
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * 获取模块分类[00-应用,01-菜单]
     *
     * @return module_class - 模块分类[00-应用,01-菜单]
     */
    public String getModuleClass() {
        return moduleClass;
    }

    /**
     * 设置模块分类[00-应用,01-菜单]
     *
     * @param moduleClass 模块分类[00-应用,01-菜单]
     */
    public void setModuleClass(String moduleClass) {
        this.moduleClass = moduleClass;
    }

    /**
     * 获取模块类型：module-模块；menu-菜单；page-页面；operation-操作
     *
     * @return module_type - 模块类型：module-模块；menu-菜单；page-页面；operation-操作
     */
    public String getModuleType() {
        return moduleType;
    }

    /**
     * 设置模块类型：module-模块；menu-菜单；page-页面；operation-操作
     *
     * @param moduleType 模块类型：module-模块；menu-菜单；page-页面；operation-操作
     */
    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    /**
     * 获取操作类型: add-新增；del-删除；mod-修改；list-列表查询；detail-详情查询；imp-导入；exp-导出；status-状态更改
     *
     * @return module_sub_type - 操作类型: add-新增；del-删除；mod-修改；list-列表查询；detail-详情查询；imp-导入；exp-导出；status-状态更改
     */
    public String getModuleSubType() {
        return moduleSubType;
    }

    /**
     * 设置操作类型: add-新增；del-删除；mod-修改；list-列表查询；detail-详情查询；imp-导入；exp-导出；status-状态更改
     *
     * @param moduleSubType 操作类型: add-新增；del-删除；mod-修改；list-列表查询；detail-详情查询；imp-导入；exp-导出；status-状态更改
     */
    public void setModuleSubType(String moduleSubType) {
        this.moduleSubType = moduleSubType;
    }

    /**
     * 获取父模块编号
     *
     * @return parent_code - 父模块编号
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 设置父模块编号
     *
     * @param parentCode 父模块编号
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * 获取根模块编号
     *
     * @return root_code - 根模块编号
     */
    public String getRootCode() {
        return rootCode;
    }

    /**
     * 设置根模块编号
     *
     * @param rootCode 根模块编号
     */
    public void setRootCode(String rootCode) {
        this.rootCode = rootCode;
    }

    /**
     * 获取是否需要登录[0-否,1-是]
     *
     * @return need_login - 是否需要登录[0-否,1-是]
     */
    public String getNeedLogin() {
        return needLogin;
    }

    /**
     * 设置是否需要登录[0-否,1-是]
     *
     * @param needLogin 是否需要登录[0-否,1-是]
     */
    public void setNeedLogin(String needLogin) {
        this.needLogin = needLogin;
    }

    /**
     * 获取是否需要权限校验[0-否,1-是]
     *
     * @return need_verify - 是否需要权限校验[0-否,1-是]
     */
    public String getNeedVerify() {
        return needVerify;
    }

    /**
     * 设置是否需要权限校验[0-否,1-是]
     *
     * @param needVerify 是否需要权限校验[0-否,1-是]
     */
    public void setNeedVerify(String needVerify) {
        this.needVerify = needVerify;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取图标[url、font_awesome]
     *
     * @return icon - 图标[url、font_awesome]
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标[url、font_awesome]
     *
     * @param icon 图标[url、font_awesome]
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取内容 [访问UR]
     *
     * @return content - 内容 [访问UR]
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容 [访问UR]
     *
     * @param content 内容 [访问UR]
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建人帐号
     *
     * @return create_by - 创建人帐号
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人帐号
     *
     * @param createBy 创建人帐号
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人帐号
     *
     * @return update_by - 更新人帐号
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人帐号
     *
     * @param updateBy 更新人帐号
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SysModule{" +
                "id=" + id +
                ", moduleCode='" + moduleCode + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", moduleClass='" + moduleClass + '\'' +
                ", moduleType='" + moduleType + '\'' +
                ", moduleSubType='" + moduleSubType + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", rootCode='" + rootCode + '\'' +
                ", needLogin='" + needLogin + '\'' +
                ", needVerify='" + needVerify + '\'' +
                ", sort=" + sort +
                ", icon='" + icon + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}