package com.namolog.message.repository;

import com.namolog.message.dto.BizLogDto;
import com.namolog.message.dto.BizLogSearchConditon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BizLogRepositoryCustom {
    Page<BizLogDto> bizLogSearch(BizLogSearchConditon conditon, Pageable pageable);
}
