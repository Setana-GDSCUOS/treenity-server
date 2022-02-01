package org.setana.treenity.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class })
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedBy
    protected LocalDateTime modifiedDate;
}
