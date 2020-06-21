package com.namolog.message.controller;

import com.namolog.message.common.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MobileIdentityController {

    private final AppProperties appProperties;

    @GetMapping("/phone")
    public String authPhone(HttpServletRequest request, HttpSession session, Model model) {
        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
        // NICE로부터 부여받은 사이트 코드
        String sSiteCode = appProperties.getNiceSiteCode();
        // NICE로부터 부여받은 사이트 패스워드
        String sSitePassword = appProperties.getNiceSitePassword();
        // 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.
        String sRequestNumber = niceCheck.getRequestNO(sSiteCode);
        session.setAttribute("REQ_SEQ" , sRequestNumber);

        // 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
        String sAuthType = "M";
        // Y : 취소버튼 있음 / N : 취소버튼 없음
        String popgubun 	= "N";
        // 없으면 기본 웹페이지 / Mobile : 모바일페이지
        String customize 	= "";
        // 없으면 기본 선택 값, 0 : 여자, 1 : 남자
        String sGender = "";

        String sReturnUrl = String.format("%s://%s:%s/auth/success", request.getScheme(), request.getServerName(), request.getServerPort());      // 성공시 이동될 URL
        String sErrorUrl = String.format("%s://%s:%s/auth/fail", request.getScheme(), request.getServerName(), request.getServerPort());         // 실패시 이동될 URL

        // 입력될 plain 데이타를 만든다.
        String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize +
                "6:GENDER" + sGender.getBytes().length + ":" + sGender;

        String sMessage = "";
        String sEncData = "";

        int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
        if( iReturn == 0 )
            sEncData = niceCheck.getCipherData();
        else if( iReturn == -1)
            sMessage = "암호화 시스템 에러입니다.";
        else if( iReturn == -2)
            sMessage = "암호화 처리오류입니다.";
        else if( iReturn == -3)
            sMessage = "암호화 데이터 오류입니다.";
        else if( iReturn == -9)
            sMessage = "입력 데이터 오류입니다.";
        else
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;

        model.addAttribute("sEncDate", sEncData);
        model.addAttribute("sMessage", sMessage);
        return "auth/phone";
    }

    @GetMapping("/success")
    public String authSuccess(HttpServletRequest request, HttpSession session, Model model) {
        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

        String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");
        // NICE로부터 부여받은 사이트 코드
        String sSiteCode = appProperties.getNiceSiteCode();
        // NICE로부터 부여받은 사이트 패스워드
        String sSitePassword = appProperties.getNiceSitePassword();

        String sCipherTime = "";			// 복호화한 시간
        String sRequestNumber = "";			// 요청 번호
        String sResponseNumber = "";		// 인증 고유번호
        String sAuthType = "";				// 인증 수단
        String sName = "";					// 성명
        String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
        String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
        String sBirthDate = "";				// 생년월일(YYYYMMDD)
        String sGender = "";				// 성별
        String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
        String sMobileNo = "";				// 휴대폰번호
        String sMobileCo = "";				// 통신사
        String sMessage = "";
        String sPlainData = "";

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

        if( iReturn == 0 )
        {
            sPlainData = niceCheck.getPlainData();
            sCipherTime = niceCheck.getCipherDateTime();

            // 데이타를 추출합니다.
            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

            sRequestNumber  = (String)mapresult.get("REQ_SEQ");
            sResponseNumber = (String)mapresult.get("RES_SEQ");
            sAuthType		= (String)mapresult.get("AUTH_TYPE");
            sName			= (String)mapresult.get("NAME");
            //sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
            sBirthDate		= (String)mapresult.get("BIRTHDATE");
            sGender			= (String)mapresult.get("GENDER");
            sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
            sDupInfo		= (String)mapresult.get("DI");
            sConnInfo		= (String)mapresult.get("CI");
            sMobileNo		= (String)mapresult.get("MOBILE_NO");
            sMobileCo		= (String)mapresult.get("MOBILE_CO");

            String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
            if(!sRequestNumber.equals(session_sRequestNumber))
            {
                sMessage = "세션값 불일치 오류입니다.";
                sResponseNumber = "";
                sAuthType = "";
            }
        }
        else if( iReturn == -1)
            sMessage = "복호화 시스템 오류입니다.";
        else if( iReturn == -4)
            sMessage = "복호화 처리 오류입니다.";
        else if( iReturn == -5)
            sMessage = "복호화 해쉬 오류입니다.";
        else if( iReturn == -6)
            sMessage = "복호화 데이터 오류입니다.";
        else if( iReturn == -9)
            sMessage = "입력 데이터 오류입니다.";
        else if( iReturn == -12)
            sMessage = "사이트 패스워드 오류입니다.";
        else
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;

        if (sMessage.equals("")) {
            session.setAttribute("di", sDupInfo);
            session.setAttribute("name", sName);
            session.setAttribute("mobile", sMobileNo);
            return "auth/success";
        }
        else {
            model.addAttribute("message", "본인확인 실패");
            return "auth/phone";
        }
    }

    public String requestReplace (String paramValue, String gubun) {
        String result = "";
        if (paramValue != null) {
            paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            paramValue = paramValue.replaceAll("\\*", "");
            paramValue = paramValue.replaceAll("\\?", "");
            paramValue = paramValue.replaceAll("\\[", "");
            paramValue = paramValue.replaceAll("\\{", "");
            paramValue = paramValue.replaceAll("\\(", "");
            paramValue = paramValue.replaceAll("\\)", "");
            paramValue = paramValue.replaceAll("\\^", "");
            paramValue = paramValue.replaceAll("\\$", "");
            paramValue = paramValue.replaceAll("'", "");
            paramValue = paramValue.replaceAll("@", "");
            paramValue = paramValue.replaceAll("%", "");
            paramValue = paramValue.replaceAll(";", "");
            paramValue = paramValue.replaceAll(":", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll("#", "");
            paramValue = paramValue.replaceAll("--", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll(",", "");

            if (!gubun.equals("encodeData")) {
                paramValue = paramValue.replaceAll("\\+", "");
                paramValue = paramValue.replaceAll("/", "");
                paramValue = paramValue.replaceAll("=", "");
            }
            result = paramValue;
        }
        return result;
    }

}
