package com.namolog.message.controller.user;

import com.namolog.message.common.RegMethod;
import com.namolog.message.domain.BizLog;
import com.namolog.message.domain.BizMsg;
import com.namolog.message.domain.NmAccount;
import com.namolog.message.domain.SendPhone;
import com.namolog.message.dto.BizLogDto;
import com.namolog.message.dto.BizLogSearchConditon;
import com.namolog.message.dto.MessageParam;
import com.namolog.message.dto.SendPhoneDto;
import com.namolog.message.repository.BizLogRepository;
import com.namolog.message.repository.SendPhoneRepository;
import com.namolog.message.service.BizMsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final BizMsgService bizMsgService;
    private final BizLogRepository bizLogRepository;
    private final SendPhoneRepository sendPhoneRepository;

    /**
     * 문자 전송
     */
    @GetMapping("/user/message/send")
    public String sendMessage(Model model) {
        NmAccount account = (NmAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SendPhoneDto> phoneDtoList = sendPhoneRepository.findAllByAccountIdAndAccept(account.getId());
        model.addAttribute("phoneList", phoneDtoList);
        return "/user/message/send";
    }

    /**
     * 문자 전송
     */
    @PostMapping("/user/message/send")
    public String sendMessage(@Valid MessageParam param) {
        // 사용자 아이디 발송계정으로 설정
        NmAccount account = (NmAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        param.setCinfo(account.getUsername());
        BizMsg bizMsg = bizMsgService.sendMessage(param);
        return "redirect:/user/message/list";
    }

    /**
     * 문자 전송 내역
     */
    @RequestMapping("/user/message/list")
    public String getMessageList(BizLogSearchConditon condition, Pageable pageable, Model model) {
        Page<BizLogDto> messageList = bizLogRepository.bizLogSearch(condition, pageable);
        model.addAttribute("condition", condition);
        model.addAttribute("messageList", messageList);
        return "/user/message/list";
    }

    /**
     * 문자발송 상세 조회
     */
    @GetMapping("/user/message/{id}")
    public String getMessage(@PathVariable String id, Model model) {
        Optional<BizLog> optionalBizLog = bizLogRepository.findById(id);
        BizLog bizLog = optionalBizLog.get();
        BizLogDto bizLogDto = new BizLogDto(bizLog.getCmid(), bizLog.getMsgType(), bizLog.getCallStatus(),
                bizLog.getRequestTime(), bizLog.getSendPhone(), bizLog.getDestPhone(), bizLog.getSubject(),
                bizLog.getMsgBody(), bizLog.getTelInfo(), bizLog.getCinfo(), bizLog.getAttachedFile());
        model.addAttribute("message", bizLogDto);
        return "/user/message/detail";
    }

    @GetMapping("/user/message/phone/list")
    public String sendPhoneList(Model model) {
        NmAccount account = (NmAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SendPhoneDto> sendPhoneList = sendPhoneRepository.findAllByAccountId(account.getId());
        model.addAttribute("phoneList", sendPhoneList);
        return "/user/message/phone/list";
    }

    @GetMapping("/user/message/phone/detail/{id}")
    public String detailSendPhone(@PathVariable Integer id, Model model) {
        //List<String> regMethodList = Arrays.stream(RegMethod.values()).map(Enum::toString).collect(Collectors.toList());
        RegMethod[] regMethodList = RegMethod.values();
        SendPhone sendPhone = sendPhoneRepository.findById(id).orElse(null);
        model.addAttribute("regMethodList", regMethodList);
        model.addAttribute("sendPhone", sendPhone);
        return "/user/message/phone/detail";
    }

    @GetMapping("/user/message/phone/add")
    public String addSendPhone(Model model) {
//        List<String> regMethodList = Arrays.stream(RegMethod.values()).map(Enum::toString).collect(Collectors.toList());
//        model.addAttribute("regMethodList", regMethodList);
        return "/user/message/phone/add";
    }

    @GetMapping("/user/message/phone/auth/{id}")
    public String authSendPhone(@PathVariable Integer id) {
        SendPhone saveSendPhone = sendPhoneRepository.findById(id).orElse(null);
        saveSendPhone.setStatus(1);
        saveSendPhone.setRegMethod(RegMethod.MOBILE);
        saveSendPhone.setRegDate(LocalDateTime.now());
        sendPhoneRepository.save(saveSendPhone);
        return "redirect:/user/message/phone/list";
    }

    @PostMapping("/user/message/phone/add")
    public String addSendPhone(@Valid SendPhoneDto sendPhoneDto, Errors errors) {
//        if (errors.hasErrors())
//            return "/user/message/phone/add";
        NmAccount account = (NmAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SendPhone sendPhone = new SendPhone();
        sendPhone.setPhone(sendPhoneDto.getPhone());
        sendPhone.setAccount(account);
        sendPhone.setStatus(0);
        sendPhone.setRegMethod(RegMethod.MOBILE);
        sendPhone.setPostDate(LocalDateTime.now());
        sendPhoneRepository.save(sendPhone);
        return "redirect:/user/message/phone/list";
    }

}
