package io.flinqo.fileupload.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhulongkun20@163.com
 * @since 2021/6/18 11:48 PM
 */
@Data
@Entity
@Table(name = "attach")
public class AttachPo {

    @Id
    @Column(name = "attach_id")
    private String attachId;

    @Column(name = "attach_name")
    private String attachName;

    @Column(name = "save_path")
    private String savePath;

    @Column(name = "size")
    private int size;

    @Column(name = "attach_type")
    private String attachType;

    @Column(name = "upload_by")
    private String uploadBy;

    @Column(name = "upload_time")
    private Date uploadTime;
}
