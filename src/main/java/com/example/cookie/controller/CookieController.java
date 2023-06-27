package com.example.cookie.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CookieController {
    @GetMapping("/get/cookies")
    public String cookies(HttpServletResponse response,
                          ModelMap modelMap,
                          @CookieValue(value = "visitCount",
                                  defaultValue = "0",
                                  required = true) String cookieValue) {

        // "/get/cookies" 경로로 GET 요청이 오면 처리하는 메서드입니다.
        // HttpServletRequest, HttpServletResponse, ModelMap 은 스프링이 제공하는 객체로 각각 요청, 응답, 데이터 전달을 담당합니다.
        // @CookieValue 어노테이션을 통해 "visitCount"라는 이름의 쿠키 값을 가져옵니다.
        // defaultValue 는 쿠키가 없을 경우 기본값으로 사용됩니다.

        cookieValue = getCookieValue(cookieValue);
        addCookieAndView(response, modelMap, cookieValue);

        return "cookies";
        // "cookies"라는 뷰를 반환합니다. 이는 뷰 리졸버에 의해 실제 뷰로 매핑됩니다.
    }

    private String getCookieValue(String cookieValue) {
        try {
            int i = Integer.parseInt(cookieValue);
            cookieValue = Integer.toString(++i);
        } catch (Exception e) {
            cookieValue = "1";
        }
        return cookieValue;
        // 쿠키 값인 cookieValue를 정수로 변환하여 1을 증가시키고 다시 문자열로 변환합니다.
        // 변환 과정에서 예외가 발생하면 cookieValue 를 "1"로 초기화합니다.
    }

    private void addCookieAndView(HttpServletResponse response, ModelMap modelMap, String cookieValue) {
        Cookie visitCountCookie = new Cookie("visitCount", cookieValue);
        visitCountCookie.setMaxAge(24 * 60 * 60);
        visitCountCookie.setPath("/");
        visitCountCookie.setHttpOnly(true); // HttpOnly 속성 추가

        // "visitCount"라는 이름과 cookieValue 값을 가지는 쿠키를 생성합니다.
        // setMaxAge() 메서드를 통해 쿠키의 유효 기간을 설정합니다. 여기서는 24시간(1일)로 설정합니다.
        // setPath() 메서드를 통해 쿠키의 적용 범위를 "/"(루트)로 설정합니다.
        // setHttpOnly() 메서드를 통해 JavaScript 를 통한 액세스가 제한되고, 브라우저와 서버 간의 통신을 안전하게 할 수 있다.
        response.addCookie(visitCountCookie);
        // 생성한 쿠키를 응답에 추가합니다. 이를 통해 클라이언트에게 쿠키가 전달됩니다.

        modelMap.addAttribute("visitCountCookie", cookieValue);
        // ModelMap을 사용하여 "visitCountCookie"라는 이름으로 cookieValue 값을 뷰로 전달합니다.
    }
}
