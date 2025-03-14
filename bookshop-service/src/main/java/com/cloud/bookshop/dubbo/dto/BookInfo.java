package com.cloud.bookshop.dubbo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookInfo implements Serializable {
    private static final long serialVersionUID = 344095840L;

    // we declare two different level views
    // one for presenting only {id and name} as response
    public interface BookListView {
    }

    // one for presenting all {id, name, and contents} as response
    public interface BookDetailView extends BookListView {
    }

    private Long id;

    private String name;

    private String content;

    private Date publishDate;
}
