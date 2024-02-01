package com.project.novel.util;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class CalculatedAge {
    public int rrnAge(String rrnFront, String rrnBack) {
        // 오늘 날짜
        LocalDate today = LocalDate.now();
        int todayYear = today.getYear();
        int todayMonth = today.getMonthValue();
        int todayDay = today.getDayOfMonth();

        // 주민등록번호를 통해 입력 받은 날짜 ex) 97/05/30
        int year = Integer.parseInt(rrnFront.substring(0,2));
        int month = Integer.parseInt(rrnFront.substring(2,4));
        int day = Integer.parseInt(rrnFront.substring(4,6));

        // 주민등록번호 뒷자리로 몇년대인지 ex) xxxxxx-1xxxxxxx => 1900년대생
        String gender = rrnBack.substring(0,1);
        switch (gender) {
            case "1", "2" -> year += 1900;
            case "3", "4" -> year += 2000;
            case "0", "9" -> year += 1800;
        }

        // 올해년도 - 태어난년도 ex) 2024 - 1997 = 27
        int calculatedAge = todayYear - year;

        // 생일이 안지났으면 - 1
        if(month > todayMonth) {
            calculatedAge--;
        } else if(month == todayMonth) {
            if(day > todayDay) {
                calculatedAge--;
            }
        }
        return calculatedAge + 1;
    }
}
