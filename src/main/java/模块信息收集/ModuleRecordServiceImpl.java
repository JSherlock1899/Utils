package 模块信息收集;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Iterator;
import java.util.List;

/**
 * @author: jiang yonghui
 * @description:
 * @date: 2020/10/13 15:12
 */
@Service
//@Slf4j
public class ModuleRecordServiceImpl implements ModuleRecordService {

    @Autowired
    private ModuleRecordMapper moduleRecordMapper;

    @Override
    public void insertList(List<SysModule> list) {
//        log.info("list大小为：" + list.size());
        Iterator<SysModule> iterator = list.iterator();
        while (iterator.hasNext()) {
            SysModule s = iterator.next();
//            log.info(s.toString());
            //该路径已存在，移除
            if (verifyContentExist(s.getContent())) iterator.remove();
            //设置临时的模块名
            if (s.getModuleName() == null) s.setModuleName("temporary");
        }
//        log.info("list大小为：" + list.size());
        try {
            moduleRecordMapper.insertList(list);
        } catch (Exception e) {
            e.printStackTrace();
//            log.info("==============================================");
        }
    }

    @Override
    public boolean verifyContentExist(String content) {
        Example example = new Example(SysModule.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("content", content);
        SysModule sysModule = moduleRecordMapper.selectOneByExample(example);
        if (sysModule != null) return true;
        else return false;
    }
}
