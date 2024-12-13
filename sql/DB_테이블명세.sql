--------------------------------------------------------------------------------------------------------
-- Name              : 테이블 명세
-- Description       : 테이블 생성 쿼리
-- Author            : 강승원, 김홍현, 조민규 2024-11-18
--------------------------------------------------------------------------------------------------------
-- 회원 정보
CREATE     TABLE TUserInfo(
UserId     VARCHAR(15) NOT NULL PRIMARY KEY, -- 회원 아이디
UserNo     NUMBER,                           -- 회원 번호
UserPwd    VARCHAR2(300),                    -- 회원 비밀번호
UserName   VARCHAR2(50),                     -- 회원 이름
UserBirth  DATE,                             -- 생년월일
 
Gender     CHAR(1),                          -- 성별
UserEmail  VARCHAR2(100),                    -- 이메일
UserTelNum VARCHAR2(11),                     -- 전화번호
ZipCode    VARCHAR2(5),                      -- 우편번호
BaseAddr   VARCHAR2(100),                    -- 기본주소

DtlAddr    VARCHAR2(300),                    -- 상세주소
RegDate    DATE DEFAULT SYSDATE,             -- 회원 가입일
UserRoles  VARCHAR2(20) DEFAULT 'USER'       -- 권한
);

-- 회원 상세내역
CREATE      TABLE TUserHis(
UserNo      NUMBER,                      -- 유저 번호
UserId      VARCHAR(15),                 -- 회원 아이디
UpdDate     DATE,                        -- 수정일
AdminId     VARCHAR2(15),                -- 관리자 아이디

StateCode   NUMBER  DEFAULT 100,         -- 상태코드
Reason      VARCHAR(400)  DEFAULT '가입', -- 사유
SecReasonNo NUMBER                       -- 탈퇴사유 번호
);

-- 캠핑장 추천
CREATE TABLE TCampSuggestion(
CampNo NUMBER,     -- 캠핑장 번호
UserId VARCHAR(15) -- 추천 누른 유저
);

-- 문의
CREATE     TABLE THelp(
HelpNo     NUMBER NOT NULL PRIMARY KEY, -- 문의 번호
UserId     VARCHAR(15),                 -- 문의 작성자
HelpTitle  VARCHAR2(90),                -- 문의 제목
HelpDetail VARCHAR2(3000),              -- 문의 내용
RegDate    DATE  DEFAULT SYSDATE,       -- 등록일

AdminId    VARCHAR2(15),                -- 관리자 아이디
Answer     VARCHAR2(3000),              -- 문의 답변 내용
AnswerDate DATE,                        -- 답변일
StateCode  NUMBER                       -- 상태코드
);

-- 예약정보
CREATE            TABLE TReservation(
ReservationNo     NUMBER NOT NULL PRIMARY KEY, -- 예약 번호
CampNo            NUMBER,                      -- 캠핑장 번호
UserNo            VARCHAR2(15),                -- 예약자
StartDate         DATE,                        -- 예약 시작 날짜
EndDate           DATE,                        -- 예약 끝 날짜

RegDate           DATE DEFAULT SYSDATE,        -- 예약 등록일
PeopleCount       NUMBER,                      -- 인원
ReservationDetail VARCHAR2(900)                -- 예약 상세정보
);

-- 찜 
CREATE     TABLE TCartHis(
CartNo     NUMBER        NOT NULL PRIMARY KEY,     -- 찜 번호
RegDate    DATE          DEFAULT SYSDATE NOT NULL, -- 등록일
UserId     VARCHAR2(15),                           -- 회원 아이디
CampNo     NUMBER                                  -- 캠핑장 번호
);

-- 후기 댓글
CREATE          TABLE TReviewComment(
ReviewCommentNo NUMBER         NOT NULL PRIMARY KEY,     -- 후기 댓글번호
ReviewNo        NUMBER,                                  -- 후기 번호
UserId          VARCHAR2(15),                            -- 회원 아이디
Comments        VARCHAR2(300),                           -- 댓글 내용
RegDate         DATE           DEFAULT SYSDATE NOT NULL, -- 등록일

UpdDate         DATE,                                    -- 수정일
StateCode       NUMBER                                   -- 상태코드
);

-- 후기 관리
CREATE     TABLE TReviewHis(
ReviewNo   NUMBER,        -- 후기 번호
UpdDate    DATE,          -- 수정일
StateCode  NUMBER,        -- 상태코드
Reason     VARCHAR2(300), -- 사유
AdminId    VARCHAR2(15)   -- 관리자 아이디
);

-- 후기
CREATE            TABLE TReview(
ReviewNo          NUMBER         NOT NULL PRIMARY KEY,    -- 후기 번호
UserId            VARCHAR2(15),                           -- 회원 아이디
ReviewTitle       VARCHAR2(100),                          -- 후기 제목
ReviewDetail      VARCHAR2(500),                          -- 후기 내용
ReviewImgPath     VARCHAR2(500),                          -- 후기이미지 프로젝트경로
                                 
ReviewImgRootPath VARCHAR2(500),                          -- 후기이미지 절대경로
Views             NUMBER,                                 -- 조회수
RegDate           DATE           DEFAULT SYSDATE NOT NULL -- 등록일
);

-- 예약 관리
CREATE        TABLE TReservationHis(
ReservationNo NUMBER,        -- 예약 번호
UpdDate       DATE,          -- 수정일
AdminId       VARCHAR2(15),  -- 관리자 아이디
Reason        VARCHAR2(300), -- 사유
StateCode     NUMBER         -- 상태코드
);

-- 캠핑장 정보
CREATE TABLE TCampInfo(
CampNo	                     NUMBER,         -- 캠핑장 번호
FacilityName	             VARCHAR2(500),  -- 시설명
CampingType	                 VARCHAR2(500),  -- 캠핑 유형
ProvinceName	             VARCHAR2(50),   -- 시도 명칭
City_Gun_GuName	             VARCHAR2(50),   -- 시군구 명칭

LegalTown_Village_DongName	 VARCHAR2(50),   -- 법정읍면동명칭
RiName	                     VARCHAR2(50),   -- 리 명칭
LotNumber	                 VARCHAR2(50),   -- 번지
RoadName	                 VARCHAR2(50),   -- 도로명 이름
BuildingNumber           	 VARCHAR2(50),   -- 건물번호

Latitude	                 NUMBER,         -- 위도
Longitude	                 NUMBER,         -- 경도
PostalCode	                 VARCHAR2(5),    -- 우편번호
RoadAddress	                 VARCHAR2(200),  -- 도로명 주소
LandAddress	                 VARCHAR2(200),  -- 지번 주소

TelNumber	                 VARCHAR2(20),   -- 전회번호
Website                      VARCHAR2(2000), -- 홈페이지
BusinessEntity	             VARCHAR2(20),   -- 사업주체
WeekdayOperation	         CHAR(1),        -- 평일 운영 여부
WeekendOperation	         CHAR(1),        -- 주말 운영 여부

SpringOperation	             CHAR(1),        -- 봄 운영 여부
SummerOperation	             CHAR(1),        -- 여름 운영 여부
AutumnOperation	             CHAR(1),        -- 가을 운영 여부
WinterOperation	             CHAR(1),        -- 겨울 운영 여부
Electricity_Subfacility	     CHAR(1),        -- 부대시설 전기

HotWater_Subfacility	     CHAR(1),        -- 부대시설 온수
WirelessInternet_Subfacility CHAR(1),        -- 부대시설 무선인터넷
FirewoodSales_Subfacility	 CHAR(1),        -- 부대시설 장작판매
WalkingTrail_Subfacility	 CHAR(1),        -- 부대시설 산책로
WaterPlayArea_Subfacility	 CHAR(1),        -- 부대시설 물놀이장

Playground_Subfacility	     CHAR(1),        -- 부대시설 놀이터
Mart_Subfacility	         CHAR(1),        -- 부대시설 마트
NumberOfToilets	             NUMBER,         -- 부대시설 화장실 수
NumberOfShowers	             NUMBER,         -- 부대시설 샤워실 수
NumberOfSinks	             NUMBER,         -- 부대시설 씽크대 수

NumberOfFireExtinguishers	 NUMBER,         -- 부대시설 소화기 수
Bed_Glamping                 CHAR(1),        -- 글램핑 침대
TV_Glamping	                 CHAR(1),        -- 글램핑 티비
Refrigerator_Glamping	     CHAR(1),        -- 글램핑 냉장고
Wired_Internet_Glamping	     CHAR(1),        -- 글램핑 유무선인터넷

InternalToilet_Glamping	     CHAR(1),        -- 글램핑 내부화장실
AirConditioner_Glamping	     CHAR(1),        -- 글램핑 에어컨
HeatingDevice_Glamping	     CHAR(1),        -- 글램핑 난방기구
CookingUtensils_Glamping	 CHAR(1),        -- 글램핑 취사도구
FacilityFeatures	         VARCHAR2(4000), -- 시설 특징

FacilityIntroduction	     VARCHAR2(4000), -- 시설 소개
LastUpdatedDate              DATE            -- 최종작성일
);

-- 캠핑장 이미지
CREATE TABLE TCampImg(
CampNo        NUMBER,        -- 캠핑장 번호
FacilityName  VARCHAR2(500), -- 기관 이름
ImagePath     VARCHAR2(300), -- 이미지 경로
ImageRootPath VARCHAR2(300)  -- 이미지 저장경로
);

-- 캠핑장 예약
CREATE TABLE TCampReservation (
ReservationNo NUMBER       NOT NULL PRIMARY KEY,
CampNo        NUMBER       NOT NULL,
CampRoomNo    NUMBER       NOT NULL,
UserIdOrEmail VARCHAR2(15) NOT NULL,
CheckInDate   DATE         NOT NULL,
CheckOutDate  DATE         NOT NULL,
RegDate       DATE,
PeopleCount   NUMBER       NOT NULL
);

CREATE TABLE SPRING_SESSION (
PRIMARY_ID            CHAR(36)     NOT NULL,
SESSION_ID            CHAR(36)     NOT NULL,
CREATION_TIME         NUMBER(19,0) NOT NULL,
LAST_ACCESS_TIME      NUMBER(19,0) NOT NULL,
MAX_INACTIVE_INTERVAL NUMBER(10,0) NOT NULL,

EXPIRY_TIME           NUMBER(19,0) NOT NULL,
PRINCIPAL_NAME        VARCHAR2(100 CHAR),
 
CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);
CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
SESSION_PRIMARY_ID CHAR(36)           NOT NULL,
ATTRIBUTE_NAME     VARCHAR2(200 CHAR) NOT NULL,
ATTRIBUTE_BYTES    BLOB               NOT NULL,

CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES

SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);

CREATE SEQUENCE UserNo_SEQ
START WITH      2024000001
INCREMENT BY    1;

CREATE SEQUENCE CampNo_SEQ
START WITH      2024002417
INCREMENT BY    1;

CREATE SEQUENCE HelpNo_SEQ
START WITH      2024000001
INCREMENT BY    1;

CREATE SEQUENCE ReservationNo_SEQ
START WITH      2024000001
INCREMENT BY    1;

CREATE SEQUENCE ReviewCommentNo_SEQ
START WITH      2024000001
INCREMENT BY    1;

CREATE SEQUENCE CartNo_SEQ
START WITH      2024000001
INCREMENT BY    1;