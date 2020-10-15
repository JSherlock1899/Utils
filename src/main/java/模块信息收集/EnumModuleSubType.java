package 模块信息收集;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作类型：
 * add-新增；
 * del-删除；
 * mod-修改；
 * list-列表查询；
 * detail-详情查询；
 * imp-导入；
 * exp-导出；
 * status-状态更改
 */
public enum EnumModuleSubType {
    MODULE_TYPE_ADD("add", "新增"),
    MODULE_TYPE_DEL("del", "删除"),
    MODULE_TYPE_MOD("mod", "修改"),
    MODULE_TYPE_LIST("list", "列表"),
    MODULE_TYPE_DETAIL("detail", "详情"),
    MODULE_TYPE_IMP("imp", "导入"),
    MODULE_TYPE_EXP("exp", "导出"),
    MODULE_TYPE_STATUS("status", "状态")
    ;

    // 成员变量
    private String code;
    private String desc;

    EnumModuleSubType(String code, String desc) {
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
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumModuleSubType key : values()) {
            enumDataMap.put(key.getCode(), key.getDesc());
        }
        return enumDataMap;
    }
}
