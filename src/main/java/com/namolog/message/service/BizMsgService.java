package com.namolog.message.service;

import com.namolog.message.domain.BizMsg;
import com.namolog.message.dto.MessageParam;
import com.namolog.message.repository.BizMsgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface BizMsgService {
    BizMsg sendMessage(MessageParam param);
}
