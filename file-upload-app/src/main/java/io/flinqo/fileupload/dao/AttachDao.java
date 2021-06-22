package io.flinqo.fileupload.dao;

import io.flinqo.fileupload.po.AttachPo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhulongkun20@163.com
 * @since 2021/6/22 12:44 AM
 */
public interface AttachDao extends Repository<AttachPo, String> {

    void save(AttachPo attachPo);

    List<AttachPo> findAll();

    @Modifying
    @Transactional
    int deleteByAttachId(String attachId);
}
