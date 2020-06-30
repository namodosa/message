package com.namolog.message.repository;

import com.namolog.message.domain.BizLog;
import com.namolog.message.dto.BizLogDto;
import com.namolog.message.dto.BizLogSearchConditon;
import com.namolog.message.dto.QBizLogDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.namolog.message.domain.QBizLog.bizLog;

public class BizLogRepositoryImpl implements BizLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BizLogRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<BizLogDto> bizLogSearch(BizLogSearchConditon condition, Pageable pageable) {
        List<BizLogDto> content = queryFactory
                .select(new QBizLogDto(
                        bizLog.cmid,
                        bizLog.msgType,
                        bizLog.callStatus,
                        bizLog.sendTime,
                        bizLog.sendPhone,
                        bizLog.destPhone,
                        bizLog.msgBody,
                        bizLog.telInfo,
                        bizLog.cinfo
                ))
                .from(bizLog)
                .where(
                        cinfoEq(condition.getCinfo()),
                        statusEq(condition.getStatus()),
                        telInfoEq(condition.getTelInfo()),
                        sendPhoneContains(condition.getDestPhone()),
                        destPhoneContains(condition.getDestPhone()),
                        requestDateRange(condition.getFromRequestDate(), condition.getToRequestDate()),
                        sendDateRange(condition.getFromSendDate(), condition.getToSendDate()),
                        msgBodyContains(condition.getMsgBody())
                )
                .orderBy(bizLog.cmid.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<BizLog> countQuery = queryFactory
                .select(bizLog)
                .from(bizLog)
                .where(
                        cinfoEq(condition.getCinfo()),
                        statusEq(condition.getStatus()),
                        telInfoEq(condition.getTelInfo()),
                        sendPhoneContains(condition.getDestPhone()),
                        destPhoneContains(condition.getDestPhone()),
                        requestDateRange(condition.getFromRequestDate(), condition.getToRequestDate()),
                        sendDateRange(condition.getFromSendDate(), condition.getToSendDate()),
                        msgBodyContains(condition.getMsgBody())
                );

        //return new PageImpl<>(content, pageable, total);
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression statusEq(Integer status) {
        if (status == null)
            return null;
        else if (status == 1) {
            return bizLog.callStatus.eq("4100").or(bizLog.callStatus.eq("6600"))
                    .or(bizLog.callStatus.eq("7000")).or(bizLog.callStatus.eq("8000"));
        }
        else {
            return bizLog.callStatus.ne("4100").and(bizLog.callStatus.ne("6600"))
                    .and(bizLog.callStatus.ne("7000")).and(bizLog.callStatus.ne("8000"));
        }
    }

    private BooleanExpression telInfoEq(String telInfo) {
        return StringUtils.hasText(telInfo) ? bizLog.telInfo.eq(telInfo) : null;
    }

    private BooleanExpression cinfoEq(String cinfo) {
        return StringUtils.hasText(cinfo) ? bizLog.cinfo.eq(cinfo) : null;
    }

    private BooleanExpression sendPhoneContains(String sendPhone) {
        return StringUtils.hasText(sendPhone) ? bizLog.sendPhone.contains(sendPhone) : null;
    }

    private BooleanExpression destPhoneContains(String destPhone) {
        return StringUtils.hasText(destPhone) ? bizLog.destPhone.contains(destPhone) : null;
    }

    private BooleanExpression requestDateRange(String fromDate, String toDate) {
        if (StringUtils.hasText(fromDate) && StringUtils.hasText(toDate)) {
            LocalDateTime from = LocalDateTime.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime to = LocalDateTime.parse(toDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return bizLog.requestTime.between(from, to);
        }
        else if (StringUtils.hasText(fromDate)) {
            LocalDateTime from = LocalDateTime.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return bizLog.requestTime.loe(from);
        }
        else if (StringUtils.hasText(toDate)) {
            LocalDateTime to = LocalDateTime.parse(toDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return bizLog.requestTime.goe(to);
        }
        else
            return null;
    }

    private BooleanExpression sendDateRange(String fromDate, String toDate) {
        if (StringUtils.hasText(fromDate) && StringUtils.hasText(toDate)) {
            LocalDateTime from = LocalDateTime.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime to = LocalDateTime.parse(toDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return bizLog.sendTime.between(from, to);
        }
        else if (StringUtils.hasText(fromDate)) {
            LocalDateTime from = LocalDateTime.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return bizLog.sendTime.loe(from);
        }
        else if (StringUtils.hasText(toDate)) {
            LocalDateTime to = LocalDateTime.parse(toDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return bizLog.sendTime.goe(to);
        }
        else
            return null;
    }


    private BooleanExpression msgBodyContains(String msgBody) {
        return StringUtils.hasText(msgBody) ? bizLog.msgBody.contains(msgBody) : null;
    }

//    private BooleanExpression destPhoneEq(String destPhone) {
//        return StringUtils.isEmpty(destPhone) ? null : bizLog.destPhone.eq(destPhone);
//    }
}
