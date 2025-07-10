package com.github.YewonKimMe.create_spring_app.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

/**
 * 사용자 입력값에 대한 XSS 필터 유틸
 */
@Component
public class XssSanitizer {

    /**
     * 기본 HTML 태그만 허용하면서, 나머지 스크립트 및 속성 제거
     * 예: <b>, <i>, <ul>, <li>, <a href=...> 등은 유지됨
     */
    public String sanitizeBasic(String htmlInput) {
        return Jsoup.clean(htmlInput, Safelist.basic());
    }

    /**
     * 완전히 모든 HTML 태그 제거 (순수 텍스트만 유지)
     */
    public String sanitizePlainText(String input) {
        return Jsoup.clean(input, Safelist.none());
    }

    /**
     * 사용자 지정 whitelist 설정
     */
    public String sanitizeWithCustomPolicy(String htmlInput) {
        Safelist safelist = Safelist.basic()
                .addTags("span", "div")
                .addAttributes("span", "style")
                .addAttributes("a", "target"); // 예: <a target="_blank">

        return Jsoup.clean(htmlInput, safelist);
    }
}