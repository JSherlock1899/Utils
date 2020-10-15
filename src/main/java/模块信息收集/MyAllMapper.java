package 模块信息收集;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MyAllMapper<T> extends Mapper<T>, IdsMapper<T>, MySqlMapper<T>, ConditionMapper<T> {

}