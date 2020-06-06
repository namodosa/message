package com.namolog.message.service.impl;

import com.namolog.message.domain.BizMsg;
import com.namolog.message.dto.MessageParam;
import com.namolog.message.repository.BizMsgRepository;
import com.namolog.message.service.BizMsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class BizMsgServiceImpl implements BizMsgService {
    private final BizMsgRepository bizMsgRepository;

    public BizMsg sendMessage(MessageParam param) {
        double rvalue = Math.random();
        String value = String.format("%03d", (int)(rvalue * 1000));

        String msgBody = param.getMsgBody();
        // 내용 치환
        if (StringUtils.hasText(param.getReplace1()))
            msgBody = msgBody.replaceAll("##replace1##", param.getReplace1());
        if (StringUtils.hasText(param.getReplace2()))
            msgBody = msgBody.replaceAll("##replace2##", param.getReplace2());
        if (StringUtils.hasText(param.getReplace3()))
            msgBody = msgBody.replaceAll("##replace3##", param.getReplace3());
        int msgCount = msgBody.getBytes(StandardCharsets.UTF_8).length;
        LocalDateTime now = LocalDateTime.now();
        BizMsg msg = new BizMsg();
        // 단문
        if (msgCount <= 90) {
            msg.setMsgType(0);
        }
        else { // 장문
            msg.setMsgType(5);
            msg.setSubject(param.getSubject() == null ? "" : param.getSubject());
            if (!param.getAttachedFile().isEmpty())
                msg.setAttachedFile(param.getAttachedFile());
        }
        if (StringUtils.hasText(param.getRequestTime())) {
            LocalDateTime requestTime = LocalDateTime.parse(param.getRequestTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            msg.setRequestTime(requestTime);
            msg.setSendTime(requestTime);
        }
        else {
            msg.setRequestTime(now);
            msg.setSendTime(now);
        }
        msg.setCinfo(param.getCinfo());
        msg.setCmid(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")).concat(value));
        msg.setDestPhone(param.getDestPhone());
        msg.setSendPhone(param.getSendPhone());
        msg.setMsgBody(msgBody);
        return bizMsgRepository.save(msg);
    }

}
