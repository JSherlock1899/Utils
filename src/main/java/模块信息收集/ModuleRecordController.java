package 模块信息收集;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author: jiang yonghui
 * @description:
 * @date: 2020/10/12 14:36
 */
@RestController
@RequestMapping(value = "/moduleRecord")
public class ModuleRecordController {

    @Resource
    private ModuleRecordService moduleRecordService;

    public  Map<String, String> getMap(String name) throws ClassNotFoundException {
        Map<String, String> record = new HashMap<>();
        Class clazz = Class.forName(name);
        //获取类注解中的相关信息
        Annotation[] annotations = clazz.getAnnotations();
        RequestMapping annotation1 = null;
        Api annotation2 = null;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == RequestMapping.class) {
                annotation1 = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            }
            if (annotation.annotationType() == Api.class) {
                annotation2 = (Api) clazz.getAnnotation(Api.class);
            }
        }
        if (!StringUtils.isEmpty(annotation1) && !StringUtils.isEmpty(annotation2))
            record.put(annotation1.value()[0], annotation2.tags()[0]);
        //遍历类中的方法
        RequestMapping finalAnnotation = annotation1;
        Stream.of(clazz.getDeclaredMethods()).forEach(field -> {
            if(field != null){
                //允许访问私有方法
                field.setAccessible(true);
                //判断是否有PostMapping注解或者GetMapping注解，若有则取出其中的value作为Map的key值
                PostMapping postMappingAnnotation = field.getAnnotation(PostMapping.class);
                GetMapping getMappingAnnotation = field.getAnnotation(GetMapping.class);
                RequestMapping requestMappingAnnotation = field.getAnnotation(RequestMapping.class);
                String key = null;
                if (postMappingAnnotation != null) {
                    if(postMappingAnnotation.value().length != 0)
                        key = postMappingAnnotation.value()[0];
                } else if (getMappingAnnotation != null) {
                    if(getMappingAnnotation.value().length != 0)
                        key = getMappingAnnotation.value()[0];
                }else if (requestMappingAnnotation != null) {
                    if(requestMappingAnnotation.value().length != 0)
                        key = requestMappingAnnotation.value()[0];
                }
                //判断是否有ApiOperation注解,若有则取出其中的value作为Map的value
                ApiOperation apiOperationAnnotation = field.getAnnotation(ApiOperation.class);
                String value = null;
                if (apiOperationAnnotation != null) {
                    value = apiOperationAnnotation.value();
                }
                //若存在key值则将其添加到Map中
                if (key != null) {
                    if(finalAnnotation != null)
                        record.put(finalAnnotation.value()[0] + key, value);
                    else
                        record.put(key, value);
                }
            }
        });
        return record;
    }


    public List<SysModule> helper(String path, String packageName) {
        List<String> list = listFiles(path, packageName);
        List<SysModule> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            try {
                Map<String, String> helper = getMap(list.get(i));
                String parentCode = null;
                //sort字段
                int sortNum = 1;
                for (String key : helper.keySet()) {
                    SysModule sysModule = new SysModule();
                    //生成并设置模块编号
//                    String moduleCode = idGeneratorService.generateCode(Constant.MODULE_TOP);
                    String moduleCode = "";
                    if (parentCode == null) {
                        parentCode = moduleCode;
                    }
                    sysModule.setModuleCode(moduleCode);
                    //设置模块名称
                    sysModule.setModuleName(helper.get(key));
                    //设置模块分类，这里统一默认设置为00-应用
                    sysModule.setModuleClass("00");
                    //设置模块类型，暂时统一设置为操作
                    sysModule.setModuleType(EnumModuleType.MODULE_TYPE_OPR.getCode());
                    //设置操作类型
                    EnumModuleSubType moduleSubType = getModuleSubType(key);
                    if (moduleSubType == null)
                        sysModule.setModuleSubType("N");
                    else
                        sysModule.setModuleSubType(moduleSubType.getCode());
                    //设置父模块编号
                    sysModule.setParentCode(parentCode);
                    //设置根模块编号
                    sysModule.setRootCode("MD1277958416202993666");
                    //设置是否需要登录
                    sysModule.setNeedLogin("1");
                    //设置是否需要权限校验
                    sysModule.setNeedVerify("0");
                    //设置排序字段
                    sysModule.setSort(sortNum++);
                    //设置内容（即路径）
                    sysModule.setContent(key);
                    //设置创建人账号
                    sysModule.setCreateBy("admin");
                    //设置创建时间
                    sysModule.setCreateTime(LocalDateTime.now());
                    res.add(sysModule);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
//        for (SysModule s : res) {
//            System.out.println(s.toString());
//        }
        return res;
    }

    public static List<String> listFiles(String path,String packageName) {
        List<String> res = new ArrayList<>();
        File[] files = new File(path)
                .listFiles(file -> file.getName().endsWith(".class") || file.isDirectory());
        for(File file : files){
            // 文件夹，递归遍历，暂省略
            if(file.isDirectory()){
                continue;
            }
            // 输出类名称
            String className = packageName + "." + file.getName().replace(".class", "");
            res.add(className);
        }
        return res;
    }

    /**
     * 获取操作类型
     * @param key
     * @return
     */
    private static EnumModuleSubType getModuleSubType(String key) {
        if (!StringUtils.isEmpty(key)) {
            if (key.contains("create") || key.contains("add")) {
                return EnumModuleSubType.MODULE_TYPE_ADD;
            } else if (key.contains("del")) {
                return EnumModuleSubType.MODULE_TYPE_DEL;
            } else if (key.contains("mod") || key.contains("update")) {
                return EnumModuleSubType.MODULE_TYPE_MOD;
            }  else if (key.contains("imp")) {
                return EnumModuleSubType.MODULE_TYPE_IMP;
            } else if (key.contains("exp")) {
                return EnumModuleSubType.MODULE_TYPE_EXP;
            } else if ((key.contains("query") || key.contains("page") || key.contains("list"))
                    && !(key.contains("detail") || key.contains("find"))) {
                return EnumModuleSubType.MODULE_TYPE_LIST;
            } else if (key.contains("detail") || key.contains("find")) {
                return EnumModuleSubType.MODULE_TYPE_DETAIL;
            } else {
                return null;
            }
        }else {
            return null;
        }
    }

    @PostMapping("insertList")
    public BaseResponse<Void> insertList(@RequestBody @Valid CreateModuleRecordIn moduleRecord) {
        String path = moduleRecord.getPath();
        String packageName = moduleRecord.getPackageName();
        List<SysModule> sysModuleList = helper(path, packageName);
        moduleRecordService.insertList(sysModuleList);
        return BaseResponse.success();
    }
//    public static void main(String[] args) throws IOException {
//
//    }


}
