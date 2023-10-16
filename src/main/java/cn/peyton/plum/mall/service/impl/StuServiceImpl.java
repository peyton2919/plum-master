package cn.peyton.plum.mall.service.impl;

import cn.peyton.plum.core.anno.timestamp.AutoWriteTimestamp;
import cn.peyton.plum.mall.mapper.StuMapper;
import cn.peyton.plum.mall.pojo.Stu;
import cn.peyton.plum.mall.service.StuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.mall.service.impl.StuServiceImpl
 * @date 2023年10月14日 12:14
 * @version 1.0.0
 * </pre>
 */
@Service
public class StuServiceImpl implements StuService {

    @Resource
    private StuMapper stuMapper;

    @AutoWriteTimestamp("createTime,updateTime")
    @Override
    public Stu add(Stu stu) {
        return stuMapper.add(stu) > 0 ? stu : null;
    }

    @Override
    public List<Stu> finds() {
        return stuMapper.finds();
    }

    @Override
    public Stu findById(Integer id) {
        return stuMapper.findById(id);
    }
}
