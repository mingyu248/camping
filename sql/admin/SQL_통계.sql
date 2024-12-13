--------------------------------------------------------------------------------------------------------
-- SQL Name          : 통계
-- Description       : 각 파트 별 통계
-- Author            : KSW, 2024-11-27
--------------------------------------------------------------------------------------------------------
-- 자체 서비스 회원(최근 30일간 가입현황)
SELECT TRUNC(RegDate) AS RegDate
      ,COUNT(*)       AS Count
FROM   TUserInfo
WHERE  RegDate >= SYSDATE - INTERVAL '30' DAY
GROUP BY TRUNC(RegDate)
ORDER BY RegDate;

-- 소셜 로그인 회원(최근 30일간 가입현황)
SELECT TRUNC(Created_Date) AS RegDate
      ,COUNT(*)            AS Count
FROM   User_Tbl
WHERE  Created_Date >= SYSDATE - INTERVAL '30' DAY
GROUP BY TRUNC(Created_Date)
ORDER BY RegDate;

-- 남녀 성비(자체 서비스)
SELECT CASE
           WHEN a.Gender = 'm'   THEN '남성'
           WHEN a.Gender = 'f'   THEN '여성'
           WHEN a.Gender IS NULL THEN '미선택'
       END AS Gender
      ,COUNT(*) AS Count
FROM   TUserInfo a INNER JOIN TUserRoles b ON a.UserId = b.UserId
WHERE  b.Role = 'ROLE_USER'
GROUP BY a.Gender;

-- 남녀 성비(소셜 로그인)
SELECT CASE
           WHEN Gender = 'm'   THEN '남성'
           WHEN Gender = 'f'   THEN '여성'
           WHEN Gender IS NULL THEN '미선택'
       END AS Gender
      ,COUNT(*) AS Count
FROM   User_Tbl
GROUP BY Gender;

-- 연령대 별 현황(자체 서비스)
SELECT CASE 
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 20 THEN '10대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 30 THEN '20대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 40 THEN '30대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 50 THEN '40대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 60 THEN '50대'
            WHEN UserBirth IS NULL THEN '미선택'
            ELSE '60대 이상'
        END      AS AgeGroup,
        COUNT(*) AS Count
FROM 
    TUserInfo a JOIN TUserRoles b ON a.UserId = b.UserId
WHERE b.Role = 'ROLE_USER'
GROUP BY 
    CASE 
        WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 20 THEN '10대'
        WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 30 THEN '20대'
        WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 40 THEN '30대'
        WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 50 THEN '40대'
        WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 60 THEN '50대'
        WHEN UserBirth IS NULL THEN '미선택'
        ELSE '60대 이상'
    END
ORDER BY AgeGroup;

-- 회원 상태 별 현황(자체 서비스)
SELECT CASE
           WHEN b.StateCode = 100 THEN '정상'
           WHEN b.StateCode = 900 THEN '정지'
           WHEN b.StateCode = 999 THEN '탈퇴대기'
       END AS StateCode
      ,COUNT(*) Count
FROM   TUserInfo a INNER JOIN TUserHis b ON a.UserId = b.UserIdOrEmail
                   INNER JOIN TUserRoles c ON a.UserId = c.UserId
WHERE  c.Role = 'ROLE_USER'
GROUP BY b.StateCode
ORDER BY b.StateCode;

-- 남녀 성비(소셜 로그인)
SELECT CASE
           WHEN Gender = 'm'   THEN '남성'
           WHEN Gender = 'f'   THEN '여성'
           WHEN Gender IS NULL THEN '미선택'
       END AS Gender
      ,COUNT(*) Count
FROM   User_Tbl
GROUP BY Gender;

-- 연령대 별(소셜 로그인)
SELECT CASE 
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 20 THEN '10대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 30 THEN '20대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 40 THEN '30대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 50 THEN '40대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 60 THEN '50대'
            WHEN UserBirth IS NULL THEN '미선택'
            ELSE '60대 이상'
        END      AS AgeGroup
       ,COUNT(*) AS Count
FROM    User_Tbl
GROUP BY CASE 
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 20 THEN '10대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 30 THEN '20대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 40 THEN '30대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 50 THEN '40대'
            WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, UserBirth) / 12) < 60 THEN '50대'
            WHEN UserBirth IS NULL THEN '미선택'
            ELSE '60대 이상'
        END
ORDER BY AgeGroup;

-- 상태 별 현황(소셜 로그인)
SELECT CASE
           WHEN StateCode = 100 THEN '정상'
           WHEN StateCode = 900 THEN '정지'
           WHEN StateCode = 999 THEN '탈퇴대기'
       END AS StateCode
      ,COUNT(*) AS Count
FROM   User_Tbl
GROUP BY StateCode
ORDER BY StateCode;

-- 플랫폼 현황(소셜 로그인)
SELECT Auth_Vendor AS Platform
      ,COUNT(*) AS Count
FROM   User_Tbl
GROUP BY Auth_Vendor;

-- 지역 별 캠핑장 현황
SELECT ProvinceName
      ,COUNT(*) AS Count
FROM   TCampInfo
GROUP BY ProvinceName
ORDER BY ProvinceName ASC;

-- 시/군/구 별 캠핑장 현황
SELECT City_Gun_GuName
      ,COUNT(*) AS Count
FROM   TCampInfo
WHERE  ProvinceName = '강원도'
GROUP BY City_Gun_GuName
ORDER BY City_Gun_GuName ASC;

-- 문의 상태 별 현황
SELECT CASE
           WHEN StateCode = 100 THEN '답변완료'
           WHEN StateCode = 200 THEN '답변전'
       END AS StateCode
      ,COUNT(*) Count
FROM   THelp
GROUP BY StateCode;

-- 주간 문의 현황
SELECT TO_CHAR(TRUNC(RegDate, 'IW')) AS RegWeek
      ,COUNT(*)                      AS Count
FROM   THelp
GROUP BY TO_CHAR(TRUNC(RegDate, 'IW'))
ORDER BY RegWeek;

-- 월간 문의 현황
SELECT TO_CHAR(RegDate, 'YYYY-MM') AS RegMonth
      ,COUNT(*)                    AS Count
FROM   THelp
WHERE  RegDate >= ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -12)
GROUP BY TO_CHAR(RegDate, 'YYYY-MM')
ORDER BY RegMonth;
