package com.cloud.bookshop.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.util.Date;


@MappedSuperclass
@Data
public class DomainImpl {
    @Id
    @GeneratedValue(generator = "sequenceGenerator")
    @GenericGenerator(name = "sequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "ID_SEQUENCE"),
                    @Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1000"),
                    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = SequenceStyleGenerator.OPT_PARAM, value = "pooled")
            })
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();
}
