package org.yangxin.contentcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yangxin.contentcenter.dao.content.ShareMapper;
import org.yangxin.contentcenter.domain.entity.content.Share;

import java.util.Date;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ShareMapper shareMapper;

    @GetMapping("/test")
    public List<Share> testInsert() {
        Share share = new Share();
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setTitle("xxx");
        share.setCover("xxx");
        share.setAuthor("yangxin");
        share.setBuyCount(1);

        this.shareMapper.insertSelective(share);

        return this.shareMapper.selectAll();
    }
}
