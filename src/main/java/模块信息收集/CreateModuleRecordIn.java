package 模块信息收集;


import javax.validation.constraints.NotNull;
import lombok.Data;


/**
 * @author: jiang yonghui
 * @description:
 * @date: 2020/10/13 16:35
 */
@Data
public class CreateModuleRecordIn {

    /**
     * 编译后的类所在的路径
     */
    @NotNull(message = "编译后的类所在的路径不能为空")
    private String path;

    /**
     * 要收集模块信息的包
     */
    @NotNull(message = "要收集模块信息的包不能为空")
    private String packageName;

}
