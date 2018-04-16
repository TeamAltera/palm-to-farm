
CREATE TABLE ADMIN
(
	ADMIN_CODE           NUMBER(4) NOT NULL ,
	ID                   VARCHAR(20) NULL ,
	PWD                  VARCHAR(15) NULL ,
	REG_USER_CNT         NUMBER(4) NULL 
);



CREATE UNIQUE INDEX XPK관리자 ON ADMIN
(ADMIN_CODE   ASC);



ALTER TABLE ADMIN
	ADD CONSTRAINT  XPK관리자 PRIMARY KEY (ADMIN_CODE);



CREATE TABLE AP
(
	AP_SSID              VARCHAR(30) NULL ,
	AP_PUBLIC_IP         CHAR(18) NULL 
);



CREATE UNIQUE INDEX XPK_AP ON AP
(AP_PUBLIC_IP   ASC);



ALTER TABLE AP
	ADD CONSTRAINT  XPK_AP PRIMARY KEY (AP_PUBLIC_IP);



CREATE TABLE CS
(
	CS_PROVIDE_CNT       NUMBER(3) NULL ,
	CS_CNT               NUMBER(3) NULL ,
	SF_CODE              NUMBER(5) NOT NULL 
);



CREATE UNIQUE INDEX XPK배양액 ON CS
(SF_CODE   ASC);



ALTER TABLE CS
	ADD CONSTRAINT  XPK배양액 PRIMARY KEY (SF_CODE);



CREATE TABLE CS_LOG
(
	SF_CODE              NUMBER(5) NOT NULL ,
	CS_PROVIDE_DT        CHAR(18) NOT NULL ,
	CS_RES               CHAR(1) NULL 
);



CREATE UNIQUE INDEX XPK배양액공급기록 ON CS_LOG
(SF_CODE   ASC,CS_PROVIDE_DT   ASC);



ALTER TABLE CS_LOG
	ADD CONSTRAINT  XPK배양액공급기록 PRIMARY KEY (SF_CODE,CS_PROVIDE_DT);



CREATE TABLE DEVICE_LOG
(
	USED_DT              DATE NOT NULL ,
	ACT_NAME             VARCHAR(24) NULL ,
	USED_IP              CHAR(15) NULL ,
	USED_RES             VARCHAR(7) NULL ,
	SF_CODE              NUMBER(5) NOT NULL ,
	USER_ID              VARCHAR(20) NULL 
);



CREATE UNIQUE INDEX XPK로그기록 ON DEVICE_LOG
(USED_DT   ASC,SF_CODE   ASC);



ALTER TABLE DEVICE_LOG
	ADD CONSTRAINT  XPK로그기록 PRIMARY KEY (USED_DT,SF_CODE);



CREATE TABLE ELUM
(
	CALC_DT              DATE NOT NULL ,
	ELUM                 NUMBER(3) NULL ,
	SF_CODE              NUMBER(5) NOT NULL 
);



CREATE UNIQUE INDEX XPK조도 ON ELUM
(CALC_DT   ASC,SF_CODE   ASC);



ALTER TABLE ELUM
	ADD CONSTRAINT  XPK조도 PRIMARY KEY (CALC_DT,SF_CODE);



CREATE TABLE GROWTH
(
	PLANT_CODE           NUMBER(3) NOT NULL ,
	GROWTH_CODE          NUMBER(2) NOT NULL ,
	SF_PORT_NO           NUMBER(2) NULL ,
	FLOOR_NUM            NUMBER(2) NULL ,
	GROWTH_DT            DATE NULL ,
	FINISH_DT            DATE NULL ,
	SF_CODE              NUMBER(5) NOT NULL 
);



CREATE UNIQUE INDEX XPK생육 ON GROWTH
(GROWTH_CODE   ASC,SF_CODE   ASC);



ALTER TABLE GROWTH
	ADD CONSTRAINT  XPK생육 PRIMARY KEY (GROWTH_CODE,SF_CODE);



CREATE TABLE HUMI
(
	CALC_DT              DATE NOT NULL ,
	HUMI                 NUMBER(4,2) NULL ,
	SF_CODE              NUMBER(5) NOT NULL 
);



CREATE UNIQUE INDEX XPK습도 ON HUMI
(CALC_DT   ASC,SF_CODE   ASC);



ALTER TABLE HUMI
	ADD CONSTRAINT  XPK습도 PRIMARY KEY (CALC_DT,SF_CODE);



CREATE TABLE LED_INFO
(
	LED_NO               NUMBER(2) NOT NULL ,
	LED_COLOR            VARCHAR(5) NULL ,
	LED_FLOOR            NUMBER(2) NULL ,
	SF_CODE              NUMBER(5) NOT NULL ,
	LED_AVAILABLE_DT     NUMBER(4) NULL ,
	LED_REG_DT           DATE NULL ,
	LED_0N_OFF_STATE     CHAR(1) NULL 
);



CREATE UNIQUE INDEX XPKLED ON LED_INFO
(LED_NO   ASC,SF_CODE   ASC);



ALTER TABLE LED_INFO
	ADD CONSTRAINT  XPKLED PRIMARY KEY (LED_NO,SF_CODE);



CREATE TABLE PLANT
(
	PLANT_CODE           NUMBER(3) NOT NULL ,
	PLANT_NAME           VARCHAR(30) NULL ,
	ADMIN_CODE           NUMBER(4) NOT NULL 
);



CREATE UNIQUE INDEX XPK식물 ON PLANT
(PLANT_CODE   ASC);



ALTER TABLE PLANT
	ADD CONSTRAINT  XPK식물 PRIMARY KEY (PLANT_CODE);



CREATE TABLE SF
(
	SF_CODE              NUMBER(5) NOT NULL ,
	SF_PORT_CNT          NUMBER(2) NULL ,
	FLOOR_CNT            NUMBER(2) NULL ,
	USER_CODE            NUMBER(4) NOT NULL ,
	COOLER_CNT           NUMBER(2) NULL ,
	LED_CTRL_MODE        CHAR(1) NULL ,
	TEMP_DELAY           VARCHAR(5) NULL  CONSTRAINT  Time_Limit_877264116 CHECK (TEMP_DELAY BETWEEN 60 AND 86401),
	HUMI_DELAY           VARCHAR(5) NULL  CONSTRAINT  Time_Limit_1077542139 CHECK (HUMI_DELAY BETWEEN 60 AND 86401),
	ELUM_DELAY           VARCHAR(5) NULL  CONSTRAINT  Time_Limit_1128461559 CHECK (ELUM_DELAY BETWEEN 60 AND 86401),
	WATER_TEMP_DELAY     VARCHAR(5) NULL  CONSTRAINT  Time_Limit_1933662567 CHECK (WATER_TEMP_DELAY BETWEEN 60 AND 86401),
	WATER_LIM_DELAY      VARCHAR(5) NULL  CONSTRAINT  Time_Limit_409220973 CHECK (WATER_LIM_DELAY BETWEEN 60 AND 86401),
	AP_PUBLIC_IP        CHAR(18) NULL ,
	INNER_IP           CHAR(15) NULL 
);

DROP TABLE SF;


CREATE UNIQUE INDEX XPK_SF ON SF
(SF_CODE   ASC);



ALTER TABLE SF
	ADD CONSTRAINT  XPK_SF PRIMARY KEY (SF_CODE);



CREATE TABLE TEMP
(
	CALC_DT              DATE NOT NULL ,
	TEMP                 NUMBER(4,2) NULL ,
	SF_CODE              NUMBER(5) NOT NULL 
);



CREATE UNIQUE INDEX XPK온도 ON TEMP
(CALC_DT   ASC,SF_CODE   ASC);



ALTER TABLE TEMP
	ADD CONSTRAINT  XPK온도 PRIMARY KEY (CALC_DT,SF_CODE);


CREATE TABLE PLANT_USER
(
	USER_CODE            NUMBER(4) NOT NULL ,
	PWD                  VARCHAR(64) NULL ,
	EMAIL                VARCHAR(30) NULL ,
	FIRST_NAME            CHAR(16) NULL ,
    SECOND_NAME            CHAR(30) NULL ,
	SF_CNT               NUMBER(3) NULL 
);

SELECT * FROM AP;

SELECT * FROM SF;

DELETE SF WHERE SF_CODE=13;

DELETE SF WHERE SF_CODE=14;

DELETE AP WHERE AP_SSID='pi3-ap';

SELECT * FROM PLANT_USER;

INSERT INTO PLANT_USER VALUES(0,'5ee02dcdcb96c6525b6313853e49ffc1b88900dcadb80d25e71e6cb87d5142e9','se@naver.com','홍','길동',0);

SELECT * FROM PLANT_USER WHERE USER_NAME='홍길동';

CREATE SEQUENCE PLANT_USER_SEQ;

CREATE SEQUENCE SF_SEQ;

DELETE PLANT_USER WHERE USER_CODE=0;  

DROP TABLE PLANT_USER;

CREATE UNIQUE INDEX XPK_PLANT_USER ON PLANT_USER
(USER_CODE   ASC);

ALTER TABLE PLANT_USER
	ADD CONSTRAINT  XPK_PLANT_USER PRIMARY KEY (USER_CODE);



CREATE TABLE WATER_LIM
(
	CALC_DT              DATE NOT NULL ,
	WATER_LIM            NUMBER(3) NULL ,
	SF_CODE              NUMBER(5) NOT NULL 
);



CREATE UNIQUE INDEX XPK수위 ON WATER_LIM
(CALC_DT   ASC,SF_CODE   ASC);



ALTER TABLE WATER_LIM
	ADD CONSTRAINT  XPK수위 PRIMARY KEY (CALC_DT,SF_CODE);



CREATE TABLE WATER_TEMP
(
	CALC_DT              DATE NOT NULL ,
	WATER_TEMP           NUMBER(2) NULL ,
	SF_CODE              NUMBER(5) NOT NULL 
);



CREATE UNIQUE INDEX XPK수온 ON WATER_TEMP
(CALC_DT   ASC,SF_CODE   ASC);



ALTER TABLE WATER_TEMP
	ADD CONSTRAINT  XPK수온 PRIMARY KEY (CALC_DT,SF_CODE);



ALTER TABLE CS
	ADD (CONSTRAINT R_27 FOREIGN KEY (SF_CODE) REFERENCES SF (SF_CODE));



ALTER TABLE CS_LOG
	ADD (CONSTRAINT R_28 FOREIGN KEY (SF_CODE) REFERENCES CS (SF_CODE));



ALTER TABLE DEVICE_LOG
	ADD (CONSTRAINT R_11 FOREIGN KEY (SF_CODE) REFERENCES SF (SF_CODE));



ALTER TABLE ELUM
	ADD (CONSTRAINT R_7 FOREIGN KEY (SF_CODE) REFERENCES SF (SF_CODE));



ALTER TABLE GROWTH
	ADD (CONSTRAINT R_4 FOREIGN KEY (PLANT_CODE) REFERENCES PLANT (PLANT_CODE));



ALTER TABLE GROWTH
	ADD (CONSTRAINT R_33 FOREIGN KEY (SF_CODE) REFERENCES SF (SF_CODE));



ALTER TABLE HUMI
	ADD (CONSTRAINT R_6 FOREIGN KEY (SF_CODE) REFERENCES SF (SF_CODE));



ALTER TABLE LED_INFO
	ADD (CONSTRAINT R_32 FOREIGN KEY (SF_CODE) REFERENCES SF (SF_CODE));



ALTER TABLE PLANT
	ADD (CONSTRAINT R_30 FOREIGN KEY (ADMIN_CODE) REFERENCES ADMIN (ADMIN_CODE));



ALTER TABLE SF
	ADD (CONSTRAINT R_1 FOREIGN KEY (USER_CODE) REFERENCES USER (USER_CODE));



ALTER TABLE SF
	ADD (CONSTRAINT R_45 FOREIGN KEY (AP_MAC) REFERENCES AP (AP_MAC));



ALTER TABLE TEMP
	ADD (CONSTRAINT R_5 FOREIGN KEY (SF_CODE) REFERENCES SF (SF_CODE));



ALTER TABLE WATER_LIM
	ADD (CONSTRAINT R_9 FOREIGN KEY (SF_CODE) REFERENCES SF (SF_CODE));



ALTER TABLE WATER_TEMP
	ADD (CONSTRAINT R_8 FOREIGN KEY (SF_CODE) REFERENCES SF (SF_CODE));



CREATE  TRIGGER  tD_ADMIN AFTER DELETE ON ADMIN for each row
-- ERwin Builtin Trigger
-- DELETE trigger on ADMIN 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* ADMIN  PLANT on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0000d23e", PARENT_OWNER="", PARENT_TABLE="ADMIN"
    CHILD_OWNER="", CHILD_TABLE="PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_30", FK_COLUMNS="ADMIN_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM PLANT
      WHERE
        /*  %JoinFKPK(PLANT,:%Old," = "," AND") */
        PLANT.ADMIN_CODE = :old.ADMIN_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete ADMIN because PLANT exists.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_ADMIN AFTER UPDATE ON ADMIN for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on ADMIN 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* ADMIN  PLANT on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000fb60", PARENT_OWNER="", PARENT_TABLE="ADMIN"
    CHILD_OWNER="", CHILD_TABLE="PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_30", FK_COLUMNS="ADMIN_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.ADMIN_CODE <> :new.ADMIN_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM PLANT
      WHERE
        /*  %JoinFKPK(PLANT,:%Old," = "," AND") */
        PLANT.ADMIN_CODE = :old.ADMIN_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update ADMIN because PLANT exists.'
      );
    END IF;
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER  tD_AP AFTER DELETE ON AP for each row
-- ERwin Builtin Trigger
-- DELETE trigger on AP 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* AP  SF on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0000c573", PARENT_OWNER="", PARENT_TABLE="AP"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_45", FK_COLUMNS="AP_MAC" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /*  %JoinFKPK(SF,:%Old," = "," AND") */
        SF.AP_MAC = :old.AP_MAC;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete AP because SF exists.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_AP AFTER UPDATE ON AP for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on AP 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* AP  SF on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000f0f9", PARENT_OWNER="", PARENT_TABLE="AP"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_45", FK_COLUMNS="AP_MAC" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.AP_MAC <> :new.AP_MAC
  THEN
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /*  %JoinFKPK(SF,:%Old," = "," AND") */
        SF.AP_MAC = :old.AP_MAC;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update AP because SF exists.'
      );
    END IF;
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_CS BEFORE INSERT ON CS for each row
-- ERwin Builtin Trigger
-- INSERT trigger on CS 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  CS on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000cb3b", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert CS because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER  tD_CS AFTER DELETE ON CS for each row
-- ERwin Builtin Trigger
-- DELETE trigger on CS 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* CS  CS_LOG on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0000cbfb", PARENT_OWNER="", PARENT_TABLE="CS"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM CS_LOG
      WHERE
        /*  %JoinFKPK(CS_LOG,:%Old," = "," AND") */
        CS_LOG.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete CS because CS_LOG exists.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_CS AFTER UPDATE ON CS for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on CS 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* CS  CS_LOG on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="0001d01f", PARENT_OWNER="", PARENT_TABLE="CS"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM CS_LOG
      WHERE
        /*  %JoinFKPK(CS_LOG,:%Old," = "," AND") */
        CS_LOG.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update CS because CS_LOG exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  CS on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update CS because SF does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_CS_LOG BEFORE INSERT ON CS_LOG for each row
-- ERwin Builtin Trigger
-- INSERT trigger on CS_LOG 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* CS  CS_LOG on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000e0d3", PARENT_OWNER="", PARENT_TABLE="CS"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM CS
      WHERE
        /* %JoinFKPK(:%New,CS," = "," AND") */
        :new.SF_CODE = CS.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert CS_LOG because CS does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_CS_LOG AFTER UPDATE ON CS_LOG for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on CS_LOG 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* CS  CS_LOG on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000e296", PARENT_OWNER="", PARENT_TABLE="CS"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM CS
    WHERE
      /* %JoinFKPK(:%New,CS," = "," AND") */
      :new.SF_CODE = CS.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update CS_LOG because CS does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_DEVICE_LOG BEFORE INSERT ON DEVICE_LOG for each row
-- ERwin Builtin Trigger
-- INSERT trigger on DEVICE_LOG 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  DEVICE_LOG on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000d727", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert DEVICE_LOG because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_DEVICE_LOG AFTER UPDATE ON DEVICE_LOG for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on DEVICE_LOG 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  DEVICE_LOG on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000d3a9", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update DEVICE_LOG because SF does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_ELUM BEFORE INSERT ON ELUM for each row
-- ERwin Builtin Trigger
-- INSERT trigger on ELUM 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  ELUM on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000d652", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="ELUM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert ELUM because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_ELUM AFTER UPDATE ON ELUM for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on ELUM 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  ELUM on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000d487", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="ELUM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update ELUM because SF does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_GROWTH BEFORE INSERT ON GROWTH for each row
-- ERwin Builtin Trigger
-- INSERT trigger on GROWTH 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  GROWTH on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0001cfba", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert GROWTH because SF does not exist.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* PLANT  GROWTH on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_4", FK_COLUMNS="PLANT_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM PLANT
      WHERE
        /* %JoinFKPK(:%New,PLANT," = "," AND") */
        :new.PLANT_CODE = PLANT.PLANT_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert GROWTH because PLANT does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_GROWTH AFTER UPDATE ON GROWTH for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on GROWTH 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  GROWTH on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0001d216", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update GROWTH because SF does not exist.'
    );
  END IF;

  /* ERwin Builtin Trigger */
  /* PLANT  GROWTH on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_4", FK_COLUMNS="PLANT_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM PLANT
    WHERE
      /* %JoinFKPK(:%New,PLANT," = "," AND") */
      :new.PLANT_CODE = PLANT.PLANT_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update GROWTH because PLANT does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_HUMI BEFORE INSERT ON HUMI for each row
-- ERwin Builtin Trigger
-- INSERT trigger on HUMI 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  HUMI on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000d2d1", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="HUMI"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_6", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert HUMI because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_HUMI AFTER UPDATE ON HUMI for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on HUMI 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  HUMI on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000d301", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="HUMI"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_6", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update HUMI because SF does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_LED_INFO BEFORE INSERT ON LED_INFO for each row
-- ERwin Builtin Trigger
-- INSERT trigger on LED_INFO 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  LED_INFO on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000cc63", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="LED_INFO"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_32", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert LED_INFO because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_LED_INFO AFTER UPDATE ON LED_INFO for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on LED_INFO 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  LED_INFO on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000d0a7", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="LED_INFO"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_32", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update LED_INFO because SF does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_PLANT BEFORE INSERT ON PLANT for each row
-- ERwin Builtin Trigger
-- INSERT trigger on PLANT 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* ADMIN  PLANT on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000e0f4", PARENT_OWNER="", PARENT_TABLE="ADMIN"
    CHILD_OWNER="", CHILD_TABLE="PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_30", FK_COLUMNS="ADMIN_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM ADMIN
      WHERE
        /* %JoinFKPK(:%New,ADMIN," = "," AND") */
        :new.ADMIN_CODE = ADMIN.ADMIN_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert PLANT because ADMIN does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER  tD_PLANT AFTER DELETE ON PLANT for each row
-- ERwin Builtin Trigger
-- DELETE trigger on PLANT 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* PLANT  GROWTH on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0000cfd2", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_4", FK_COLUMNS="PLANT_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM GROWTH
      WHERE
        /*  %JoinFKPK(GROWTH,:%Old," = "," AND") */
        GROWTH.PLANT_CODE = :old.PLANT_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete PLANT because GROWTH exists.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_PLANT AFTER UPDATE ON PLANT for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on PLANT 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* PLANT  GROWTH on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="0001e737", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_4", FK_COLUMNS="PLANT_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.PLANT_CODE <> :new.PLANT_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM GROWTH
      WHERE
        /*  %JoinFKPK(GROWTH,:%Old," = "," AND") */
        GROWTH.PLANT_CODE = :old.PLANT_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update PLANT because GROWTH exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* ADMIN  PLANT on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="ADMIN"
    CHILD_OWNER="", CHILD_TABLE="PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_30", FK_COLUMNS="ADMIN_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM ADMIN
    WHERE
      /* %JoinFKPK(:%New,ADMIN," = "," AND") */
      :new.ADMIN_CODE = ADMIN.ADMIN_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update PLANT because ADMIN does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_SF BEFORE INSERT ON SF for each row
-- ERwin Builtin Trigger
-- INSERT trigger on SF 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* AP  SF on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0001cb20", PARENT_OWNER="", PARENT_TABLE="AP"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_45", FK_COLUMNS="AP_MAC" */
    SELECT count(*) INTO NUMROWS
      FROM AP
      WHERE
        /* %JoinFKPK(:%New,AP," = "," AND") */
        :new.AP_MAC = AP.AP_MAC;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert SF because AP does not exist.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* USER  SF on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="USER"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="USER_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM USER
      WHERE
        /* %JoinFKPK(:%New,USER," = "," AND") */
        :new.USER_CODE = USER.USER_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert SF because USER does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER  tD_SF AFTER DELETE ON SF for each row
-- ERwin Builtin Trigger
-- DELETE trigger on SF 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  GROWTH on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0007b817", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM GROWTH
      WHERE
        /*  %JoinFKPK(GROWTH,:%Old," = "," AND") */
        GROWTH.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because GROWTH exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  LED_INFO on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="LED_INFO"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_32", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM LED_INFO
      WHERE
        /*  %JoinFKPK(LED_INFO,:%Old," = "," AND") */
        LED_INFO.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because LED_INFO exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  CS on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM CS
      WHERE
        /*  %JoinFKPK(CS,:%Old," = "," AND") */
        CS.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because CS exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  DEVICE_LOG on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM DEVICE_LOG
      WHERE
        /*  %JoinFKPK(DEVICE_LOG,:%Old," = "," AND") */
        DEVICE_LOG.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because DEVICE_LOG exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  WATER_LIM on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="WATER_LIM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_9", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM WATER_LIM
      WHERE
        /*  %JoinFKPK(WATER_LIM,:%Old," = "," AND") */
        WATER_LIM.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because WATER_LIM exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  WATER_TEMP on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="WATER_TEMP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_8", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM WATER_TEMP
      WHERE
        /*  %JoinFKPK(WATER_TEMP,:%Old," = "," AND") */
        WATER_TEMP.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because WATER_TEMP exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  ELUM on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="ELUM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM ELUM
      WHERE
        /*  %JoinFKPK(ELUM,:%Old," = "," AND") */
        ELUM.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because ELUM exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  HUMI on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="HUMI"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_6", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM HUMI
      WHERE
        /*  %JoinFKPK(HUMI,:%Old," = "," AND") */
        HUMI.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because HUMI exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  TEMP on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="TEMP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM TEMP
      WHERE
        /*  %JoinFKPK(TEMP,:%Old," = "," AND") */
        TEMP.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because TEMP exists.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_SF AFTER UPDATE ON SF for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on SF 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  GROWTH on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="000aaafb", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM GROWTH
      WHERE
        /*  %JoinFKPK(GROWTH,:%Old," = "," AND") */
        GROWTH.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because GROWTH exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  LED_INFO on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="LED_INFO"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_32", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM LED_INFO
      WHERE
        /*  %JoinFKPK(LED_INFO,:%Old," = "," AND") */
        LED_INFO.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because LED_INFO exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  CS on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM CS
      WHERE
        /*  %JoinFKPK(CS,:%Old," = "," AND") */
        CS.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because CS exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  DEVICE_LOG on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM DEVICE_LOG
      WHERE
        /*  %JoinFKPK(DEVICE_LOG,:%Old," = "," AND") */
        DEVICE_LOG.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because DEVICE_LOG exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  WATER_LIM on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="WATER_LIM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_9", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM WATER_LIM
      WHERE
        /*  %JoinFKPK(WATER_LIM,:%Old," = "," AND") */
        WATER_LIM.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because WATER_LIM exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  WATER_TEMP on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="WATER_TEMP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_8", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM WATER_TEMP
      WHERE
        /*  %JoinFKPK(WATER_TEMP,:%Old," = "," AND") */
        WATER_TEMP.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because WATER_TEMP exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  ELUM on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="ELUM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM ELUM
      WHERE
        /*  %JoinFKPK(ELUM,:%Old," = "," AND") */
        ELUM.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because ELUM exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  HUMI on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="HUMI"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_6", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM HUMI
      WHERE
        /*  %JoinFKPK(HUMI,:%Old," = "," AND") */
        HUMI.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because HUMI exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  TEMP on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="TEMP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="SF_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM TEMP
      WHERE
        /*  %JoinFKPK(TEMP,:%Old," = "," AND") */
        TEMP.SF_CODE = :old.SF_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because TEMP exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* AP  SF on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="AP"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_45", FK_COLUMNS="AP_MAC" */
  SELECT count(*) INTO NUMROWS
    FROM AP
    WHERE
      /* %JoinFKPK(:%New,AP," = "," AND") */
      :new.AP_MAC = AP.AP_MAC;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update SF because AP does not exist.'
    );
  END IF;

  /* ERwin Builtin Trigger */
  /* USER  SF on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="USER"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="USER_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM USER
    WHERE
      /* %JoinFKPK(:%New,USER," = "," AND") */
      :new.USER_CODE = USER.USER_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update SF because USER does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_TEMP BEFORE INSERT ON TEMP for each row
-- ERwin Builtin Trigger
-- INSERT trigger on TEMP 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  TEMP on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000dbc7", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="TEMP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert TEMP because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_TEMP AFTER UPDATE ON TEMP for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on TEMP 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  TEMP on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000e094", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="TEMP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update TEMP because SF does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER  tD_USER AFTER DELETE ON USER for each row
-- ERwin Builtin Trigger
-- DELETE trigger on USER 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* USER  SF on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0000c7b5", PARENT_OWNER="", PARENT_TABLE="USER"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="USER_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /*  %JoinFKPK(SF,:%Old," = "," AND") */
        SF.USER_CODE = :old.USER_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete USER because SF exists.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_USER AFTER UPDATE ON USER for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on USER 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* USER  SF on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000f2e8", PARENT_OWNER="", PARENT_TABLE="USER"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="USER_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.USER_CODE <> :new.USER_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /*  %JoinFKPK(SF,:%Old," = "," AND") */
        SF.USER_CODE = :old.USER_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update USER because SF exists.'
      );
    END IF;
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_WATER_LIM BEFORE INSERT ON WATER_LIM for each row
-- ERwin Builtin Trigger
-- INSERT trigger on WATER_LIM 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  WATER_LIM on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000dd65", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="WATER_LIM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_9", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert WATER_LIM because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_WATER_LIM AFTER UPDATE ON WATER_LIM for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on WATER_LIM 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  WATER_LIM on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000d42f", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="WATER_LIM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_9", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update WATER_LIM because SF does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_WATER_TEMP BEFORE INSERT ON WATER_TEMP for each row
-- ERwin Builtin Trigger
-- INSERT trigger on WATER_TEMP 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  WATER_TEMP on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000d610", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="WATER_TEMP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_8", FK_COLUMNS="SF_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert WATER_TEMP because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_WATER_TEMP AFTER UPDATE ON WATER_TEMP for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on WATER_TEMP 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  WATER_TEMP on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000d893", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="WATER_TEMP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_8", FK_COLUMNS="SF_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update WATER_TEMP because SF does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


COMMENT ON TABLE ADMIN IS '관리자';
 
    COMMENT ON COLUMN ADMIN.ADMIN_CODE IS '관리자코드';  
    COMMENT ON COLUMN ADMIN.ID IS '아이디';  
    COMMENT ON COLUMN ADMIN.PWD IS '패스워드';  
    COMMENT ON COLUMN ADMIN.REG_USER_CNT IS '관리하는사용자수';  
    
COMMENT ON TABLE AP IS '공유기';
 
    COMMENT ON COLUMN AP.AP_MAC IS 'MAC';  
    COMMENT ON COLUMN AP.AP_SSID IS 'SSID';  
    COMMENT ON COLUMN AP.AP_PWD IS 'password';  
    COMMENT ON COLUMN AP.AP_PRIVATE_IP IS '사설ip';  
    COMMENT ON COLUMN AP.AP_PUBLIC_IP IS '공용ip';  
    
COMMENT ON TABLE CS IS '배양액';
 
    COMMENT ON COLUMN CS.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN CS.CS_PROVIDE_CNT IS '공급횟수';  
    COMMENT ON COLUMN CS.CS_CNT IS '남은횟수';  
    
COMMENT ON TABLE CS_LOG IS '배양액공급기록';
 
    COMMENT ON COLUMN CS_LOG.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN CS_LOG.CS_PROVIDE_DT IS '공급일자';  
    COMMENT ON COLUMN CS_LOG.CS_RES IS '결과';  
    
COMMENT ON TABLE DEVICE_LOG IS '로그기록';
 
    COMMENT ON COLUMN DEVICE_LOG.USED_DT IS '사용일자';  
    COMMENT ON COLUMN DEVICE_LOG.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN DEVICE_LOG.ACT_NAME IS '동작이름';  
    COMMENT ON COLUMN DEVICE_LOG.USED_IP IS '수행IP';  
    COMMENT ON COLUMN DEVICE_LOG.USED_RES IS '수행결과';  
    COMMENT ON COLUMN DEVICE_LOG.USER_ID IS '사용자ID';  
    
COMMENT ON TABLE ELUM IS '조도';
 
    COMMENT ON COLUMN ELUM.CALC_DT IS '측정시간';  
    COMMENT ON COLUMN ELUM.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN ELUM.ELUM IS '조도';  
    
COMMENT ON TABLE GROWTH IS '생육';
 
    COMMENT ON COLUMN GROWTH.GROWTH_CODE IS '생육코드';  
    COMMENT ON COLUMN GROWTH.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN GROWTH.PLANT_CODE IS '식물코드';  
    COMMENT ON COLUMN GROWTH.SF_PORT_NO IS '화분번호';  
    COMMENT ON COLUMN GROWTH.FLOOR_NUM IS '층번호';  
    COMMENT ON COLUMN GROWTH.GROWTH_DT IS '생육일자';  
    COMMENT ON COLUMN GROWTH.FINISH_DT IS '예상완료일자';  
    
COMMENT ON TABLE HUMI IS '습도';
 
    COMMENT ON COLUMN HUMI.CALC_DT IS '측정시간';  
    COMMENT ON COLUMN HUMI.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN HUMI.HUMI IS '습도';  
    
COMMENT ON TABLE LED_INFO IS 'LED';
 
    COMMENT ON COLUMN LED_INFO.LED_NO IS 'LED번호';  
    COMMENT ON COLUMN LED_INFO.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN LED_INFO.LED_COLOR IS 'LED색상';  
    COMMENT ON COLUMN LED_INFO.LED_FLOOR IS 'LED위치층';  
    COMMENT ON COLUMN LED_INFO.LED_AVAILABLE_DT IS 'LED수명';  
    COMMENT ON COLUMN LED_INFO.LED_REG_DT IS 'LED등록일자';  
    COMMENT ON COLUMN LED_INFO.LED_0N_OFF_STATE IS 'LED상태';  
    
COMMENT ON TABLE PLANT IS '식물';
 
    COMMENT ON COLUMN PLANT.PLANT_CODE IS '식물코드';  
    COMMENT ON COLUMN PLANT.PLANT_NAME IS '식물이름';  
    COMMENT ON COLUMN PLANT.ADMIN_CODE IS '관리자코드';  
    
COMMENT ON TABLE SF IS '수경재배기';
 
    COMMENT ON COLUMN SF.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN SF.SF_PORT_CNT IS '화분수';  
    COMMENT ON COLUMN SF.FLOOR_CNT IS '층수';  
    COMMENT ON COLUMN SF.USER_CODE IS '사용자코드';  
    COMMENT ON COLUMN SF.COOLER_CNT IS '쿨러갯수';  
    COMMENT ON COLUMN SF.LED_CTRL_MODE IS 'LED제어모드';  
    COMMENT ON COLUMN SF.TEMP_DELAY IS '온도측정딜레이';  
    COMMENT ON COLUMN SF.HUMI_DELAY IS '습도측정딜레이';  
    COMMENT ON COLUMN SF.ELUM_DELAY IS '조도측정딜레이';  
    COMMENT ON COLUMN SF.WATER_TEMP_DELAY IS '수온측정딜레이';  
    COMMENT ON COLUMN SF.WATER_LIM_DELAY IS '수위측정딜레이';  
    COMMENT ON COLUMN SF.AP_MAC IS 'MAC';  
    COMMENT ON COLUMN SF.AP_PRIVATE_IP IS '사설ip';  
    COMMENT ON COLUMN SF.INNER_PORT IS '내부포트';  
    COMMENT ON COLUMN SF.OUTER_PORT IS '외부포트';  
    
COMMENT ON TABLE TEMP IS '온도';
 
    COMMENT ON COLUMN TEMP.CALC_DT IS '측정시간';  
    COMMENT ON COLUMN TEMP.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN TEMP.TEMP IS '온도';  
    
COMMENT ON TABLE USER IS '사용자';
 
    COMMENT ON COLUMN USER.USER_CODE IS '사용자코드';  
    COMMENT ON COLUMN USER.ID IS '아이디';  
    COMMENT ON COLUMN USER.PWD IS '패스워드';  
    COMMENT ON COLUMN USER.EMAIL IS '이메일';  
    COMMENT ON COLUMN USER.PHONE_NUM IS '전화번호';  
    COMMENT ON COLUMN USER.USER_NAME IS '사용자명';  
    COMMENT ON COLUMN USER.SF_CNT IS '보유재배기갯수';  
    
COMMENT ON TABLE WATER_LIM IS '수위';
 
    COMMENT ON COLUMN WATER_LIM.CALC_DT IS '측정시간';  
    COMMENT ON COLUMN WATER_LIM.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN WATER_LIM.WATER_LIM IS '수위';  
    
COMMENT ON TABLE WATER_TEMP IS '수온';
 
    COMMENT ON COLUMN WATER_TEMP.CALC_DT IS '측정시간';  
    COMMENT ON COLUMN WATER_TEMP.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN WATER_TEMP.WATER_TEMP IS '수온';  
    




