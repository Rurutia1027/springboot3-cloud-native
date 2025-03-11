package com.cloud.web.service;

import org.springframework.security.access.prepost.PreAuthorize;

public interface BookService {

    @PreAuthorize("hasAuthority('xxx')")
    void getInfo(Long id);
}
