package 模块信息收集;


import java.util.List;

/**
 * @author: jiang yonghui
 * @description:
 * @date: 2020/10/13 15:09
 */
public interface ModuleRecordService {

    void insertList(List<SysModule> list);

    boolean verifyContentExist(String content);
}
