package 同步两个表中的数据;//package cn.yulintech.community.common.web.controller;
//
//import cn.yulintech.community.common.domain.SysDictItem;
//import cn.yulintech.community.common.domain.ext.SysDictExt;
//import cn.yulintech.community.common.service.SysDictItemService;
//import cn.yulintech.community.common.service.SysDictService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author: jiang yonghui
// * @description:
// * @date: 2020/10/14 10:24
// */
//@RestController
//@RequestMapping(value = "synsql")
//@Slf4j
//public class SynSqlController {
//
//    @Resource
//    private SysDictService dictService;
//
//    @Resource
//    private SysDictItemService sysDictItemService;
//
//    @PostMapping("/updateDictCode")
//    public void updateDictCode() {
//        List<SysDictItem> sysDictItems = sysDictItemService.selectAll();
//        log.info(sysDictItems.toString());
//        for (SysDictItem sysDictItem : sysDictItems) {
//            Integer dictId = sysDictItem.getDictId();
//            log.info("dictId = " + dictId);
//            String dictCode = dictService.selectOne(dictId).getDictCode();
//            log.info("dictCode = " + dictCode);
//            sysDictItem.setDictCode(dictCode);
//            sysDictItemService.update(sysDictItem);
//        }
//    }
//}
