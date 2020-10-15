package 模块信息收集;

import java.util.HashMap;
import java.util.Map;

/**
 * 模块类型：
 * module-模块；
 * menu-菜单；
 * page-页面；
 * add-新增；
 * del-删除；
 * mod-修改；
 * list-列表查询；
 * detail-详情查询；
 * imp-导入；
 * exp-导出；
 * status-状态更改
 */
public enum EnumModuleType {

    /**
     * 模块
     */
    MODULE_TYPE_MODULE("module", "模块"),
    /**
     * 菜单
     */
    MODULE_TYPE_MENU("menu", "菜单"),
    /**
     * 页面
     */
    MODULE_TYPE_PAGE("page", "页面"),
    /**
     * 操作
     */
    MODULE_TYPE_OPR("operation", "操作"),
    /**
     * 应用
     */
    MODULE_TYPE_APPLICATION("application", "应用"),
    ;

    private String code;
    private String desc;

    EnumModuleType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<>();
        for (EnumModuleType key : values()) {
            enumDataMap.put(key.getCode(), key.getDesc());
        }
        return enumDataMap;
    }

    static Map<String, EnumModuleType> pool = new HashMap<>();

    static {
        for (EnumModuleType e : values()) {
            pool.put(e.getCode(), e);
        }
    }

    public static EnumModuleType from(String code) {
        if (code == null || code.length() == 0) {
            return null;
        }
        return pool.get(code);
    }

    public static boolean check(String code) {
        return pool.get(code) != null;
    }
}