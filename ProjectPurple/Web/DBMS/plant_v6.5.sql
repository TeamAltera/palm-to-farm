
CREATE TABLE AP
(
	AP_SSID              VARCHAR(30) NULL ,
	AP_PUBLIC_IP         VARCHAR(15) NULL ,
	USER_CODE            NUMBER(8) NOT NULL ,
	AP_CODE              NUMBER(8) NOT NULL ,
	AP_REG_DATE          DATE NULL ,
	AP_SF_CNT            NUMBER(2) NULL 
);



CREATE UNIQUE INDEX XPK공유기 ON AP
(AP_CODE   ASC);



ALTER TABLE AP
	ADD CONSTRAINT  XPK공유기 PRIMARY KEY (AP_CODE);



CREATE TABLE CS_LOG
(
	CS_PROVIDE_DT        CHAR(18) NOT NULL ,
	CS_RES               CHAR(1) NULL ,
	AP_CODE              NUMBER(8) NOT NULL ,
	STAMP                NUMBER(11) NOT NULL 
);



CREATE UNIQUE INDEX XPK배양액공급기록 ON CS_LOG
(CS_PROVIDE_DT   ASC,AP_CODE   ASC,STAMP   ASC);



ALTER TABLE CS_LOG
	ADD CONSTRAINT  XPK배양액공급기록 PRIMARY KEY (CS_PROVIDE_DT,AP_CODE,STAMP);



CREATE TABLE DEVICE_LOG
(
	USED_DT              DATE NOT NULL ,
	ACT_NAME             VARCHAR(24) NULL ,
	USED_IP              VARCHAR(15) NULL ,
	USED_RES             CHAR(1) NULL ,
	AP_CODE              NUMBER(8) NOT NULL ,
	STAMP                NUMBER(11) NOT NULL 
);



CREATE UNIQUE INDEX XPK로그기록 ON DEVICE_LOG
(USED_DT   ASC,AP_CODE   ASC,STAMP   ASC);



ALTER TABLE DEVICE_LOG
	ADD CONSTRAINT  XPK로그기록 PRIMARY KEY (USED_DT,AP_CODE,STAMP);



CREATE TABLE G_PLANT
(
	FARMING_DATE         DATE NULL ,
	PLANT_CODE           NUMBER(4) NOT NULL ,
	AP_CODE              NUMBER(8) NOT NULL ,
	STAMP                NUMBER(11) NOT NULL 
);



CREATE UNIQUE INDEX XPK생육식물 ON G_PLANT
(PLANT_CODE   ASC,AP_CODE   ASC,STAMP   ASC);



ALTER TABLE G_PLANT
	ADD CONSTRAINT  XPK생육식물 PRIMARY KEY (PLANT_CODE,AP_CODE,STAMP);



CREATE TABLE OAUTH_CLIENT_DETAILS
(
	CLIENT_ID            VARCHAR(256) NOT NULL ,
	RESOURCE_IDS         VARCHAR(128) NULL ,
	CLIENT_SECRET        VARCHAR(256) NULL ,
	SCOPE                VARCHAR(256) NULL ,
	AUTHORIZED_GRANT_TYPES VARCHAR(256) NULL ,
	WEB_SERVER_REDIRECT_URI VARCHAR(256) NULL ,
	AUTHORITIES          VARCHAR(256) NULL ,
	ACCESS_TOKEN_VALIDITY NUMBER(8) NULL ,
	REFRESH_TOKEN_VALIDITY NUMBER(8) NULL ,
	ADDITIONAL_INFORMATION VARCHAR(256) NULL ,
	AUTOAPPROVE          VARCHAR(256) NULL 
);



CREATE UNIQUE INDEX XPK클라이언트 ON OAUTH_CLIENT_DETAILS
(CLIENT_ID   ASC);



ALTER TABLE OAUTH_CLIENT_DETAILS
	ADD CONSTRAINT  XPK클라이언트 PRIMARY KEY (CLIENT_ID);



CREATE TABLE PLANT
(
	PLANT_CODE           NUMBER(4) NOT NULL ,
	OPT_TEMP             NUMBER(5,2) NULL ,
	MAX_TEMP             NUMBER(5,2) NULL ,
	MIN_TEMP             NUMBER(5,2) NULL ,
	MIN_PH               NUMBER(5,2) NULL ,
	MAX_PH               NUMBER(5,2) NULL ,
	PLANT_NAME           VARCHAR(30) NULL ,
	MIN_EC               NUMBER(5,2) NULL ,
	MAX_EC               NUMBER(5,2) NULL 
);



CREATE UNIQUE INDEX XPK식물 ON PLANT
(PLANT_CODE   ASC);



ALTER TABLE PLANT
	ADD CONSTRAINT  XPK식물 PRIMARY KEY (PLANT_CODE);



CREATE TABLE PLANT_USER
(
	USER_CODE            NUMBER(8) NOT NULL ,
	PWD                  VARCHAR(64) NULL ,
	EMAIL                VARCHAR(30) NULL ,
	FIRST_NAME           VARCHAR(16) NULL ,
	SF_CNT               INTEGER NULL ,
	SECOND_NAME          VARCHAR(30) NULL ,
	BLOCK                NUMBER(1) NULL ,
	ENABLED              CHAR(1) NULL 
);



CREATE UNIQUE INDEX XPK사용자 ON PLANT_USER
(USER_CODE   ASC);



ALTER TABLE PLANT_USER
	ADD CONSTRAINT  XPK사용자 PRIMARY KEY (USER_CODE);



CREATE TABLE PORT_INFO
(
	AP_CODE              NUMBER(8) NOT NULL ,
	PORT_ST              CHAR(1) NULL ,
	PORT_NO              NUMBER(2) NOT NULL ,
	STAMP                NUMBER(11) NOT NULL 
);



CREATE UNIQUE INDEX XPK포트정보 ON PORT_INFO
(AP_CODE   ASC,PORT_NO   ASC,STAMP   ASC);



ALTER TABLE PORT_INFO
	ADD CONSTRAINT  XPK포트정보 PRIMARY KEY (AP_CODE,PORT_NO,STAMP);



CREATE TABLE SENSOR_DATA
(
	CALC_DT              DATE NOT NULL ,
	TEMP                 NUMBER(5,2) NULL ,
	HUMI                 NUMBER(5,2) NULL ,
	ELUM                 NUMBER(4) NULL ,
	WATER_TEMP           NUMBER(5,2) NULL ,
	WATER_LIM            NUMBER(3) NULL ,
	AP_CODE              NUMBER(8) NOT NULL ,
	EC                   NUMBER(6,2) NULL ,
	PH                   NUMBER(5,2) NULL ,
	STAMP                NUMBER(11) NOT NULL 
);



CREATE UNIQUE INDEX XPK센서데이터 ON SENSOR_DATA
(CALC_DT   ASC,AP_CODE   ASC,STAMP   ASC);



ALTER TABLE SENSOR_DATA
	ADD CONSTRAINT  XPK센서데이터 PRIMARY KEY (CALC_DT,AP_CODE,STAMP);



CREATE TABLE SF
(
	SF_CODE              NUMBER(3) NULL ,
	SF_PORT_CNT          NUMBER(2) NULL ,
	FLOOR_CNT            NUMBER(2) NULL ,
	COOLER_CNT           NUMBER(2) NULL ,
	LED_CTRL_MODE        CHAR(1) NULL ,
	INNER_IP             VARCHAR(15) NULL ,
	AP_CODE              NUMBER(8) NOT NULL ,
	COOLER1_ST           CHAR(1) NULL ,
	LED21_ST             CHAR(1) NULL ,
	PUMP_ST              CHAR(1) NULL ,
	SF_REG_DATE          DATE NULL ,
	STAMP                NUMBER(11) NOT NULL ,
	LED22_ST             CHAR(1) NULL ,
	LED31_ST             CHAR(1) NULL ,
	LED32_ST             CHAR(1) NULL ,
	COOLER2_ST           CHAR(1) NULL ,
	COOLER3_ST           CHAR(1) NULL 
);



CREATE UNIQUE INDEX XPK수경재배기 ON SF
(AP_CODE   ASC,STAMP   ASC);



ALTER TABLE SF
	ADD CONSTRAINT  XPK수경재배기 PRIMARY KEY (AP_CODE,STAMP);



ALTER TABLE AP
	ADD (CONSTRAINT R_48 FOREIGN KEY (USER_CODE) REFERENCES PLANT_USER (USER_CODE));



ALTER TABLE CS_LOG
	ADD (CONSTRAINT R_58 FOREIGN KEY (AP_CODE, STAMP) REFERENCES SF (AP_CODE, STAMP));



ALTER TABLE DEVICE_LOG
	ADD (CONSTRAINT R_11 FOREIGN KEY (AP_CODE, STAMP) REFERENCES SF (AP_CODE, STAMP));



ALTER TABLE G_PLANT
	ADD (CONSTRAINT R_53 FOREIGN KEY (PLANT_CODE) REFERENCES PLANT (PLANT_CODE));



ALTER TABLE G_PLANT
	ADD (CONSTRAINT R_54 FOREIGN KEY (AP_CODE, STAMP) REFERENCES SF (AP_CODE, STAMP));



ALTER TABLE PORT_INFO
	ADD (CONSTRAINT R_55 FOREIGN KEY (AP_CODE, STAMP) REFERENCES SF (AP_CODE, STAMP));



ALTER TABLE SENSOR_DATA
	ADD (CONSTRAINT R_5 FOREIGN KEY (AP_CODE, STAMP) REFERENCES SF (AP_CODE, STAMP));



ALTER TABLE SF
	ADD (CONSTRAINT R_45 FOREIGN KEY (AP_CODE) REFERENCES AP (AP_CODE));



CREATE  TRIGGER tI_AP BEFORE INSERT ON AP for each row
-- ERwin Builtin Trigger
-- INSERT trigger on AP 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* PLANT_USER  AP on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000f637", PARENT_OWNER="", PARENT_TABLE="PLANT_USER"
    CHILD_OWNER="", CHILD_TABLE="AP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_48", FK_COLUMNS="USER_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM PLANT_USER
      WHERE
        /* %JoinFKPK(:%New,PLANT_USER," = "," AND") */
        :new.USER_CODE = PLANT_USER.USER_CODE;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert AP because PLANT_USER does not exist.'
      );
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
    /* ERWIN_RELATION:CHECKSUM="0000c65f", PARENT_OWNER="", PARENT_TABLE="AP"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_45", FK_COLUMNS="AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /*  %JoinFKPK(SF,:%Old," = "," AND") */
        SF.AP_CODE = :old.AP_CODE;
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
  /* ERWIN_RELATION:CHECKSUM="0001f18f", PARENT_OWNER="", PARENT_TABLE="AP"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_45", FK_COLUMNS="AP_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.AP_CODE <> :new.AP_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /*  %JoinFKPK(SF,:%Old," = "," AND") */
        SF.AP_CODE = :old.AP_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update AP because SF exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* PLANT_USER  AP on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="PLANT_USER"
    CHILD_OWNER="", CHILD_TABLE="AP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_48", FK_COLUMNS="USER_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM PLANT_USER
    WHERE
      /* %JoinFKPK(:%New,PLANT_USER," = "," AND") */
      :new.USER_CODE = PLANT_USER.USER_CODE;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update AP because PLANT_USER does not exist.'
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
    /* SF  CS_LOG on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000e0d5", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_58", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.AP_CODE = SF.AP_CODE AND
        :new.STAMP = SF.STAMP;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert CS_LOG because SF does not exist.'
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
  /* SF  CS_LOG on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000dcf3", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_58", FK_COLUMNS="AP_CODE""STAMP" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.AP_CODE = SF.AP_CODE AND
      :new.STAMP = SF.STAMP;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update CS_LOG because SF does not exist.'
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
    /* ERWIN_RELATION:CHECKSUM="0000f688", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.AP_CODE = SF.AP_CODE AND
        :new.STAMP = SF.STAMP;
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
  /* ERWIN_RELATION:CHECKSUM="0000f4ff", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="AP_CODE""STAMP" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.AP_CODE = SF.AP_CODE AND
      :new.STAMP = SF.STAMP;
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


CREATE  TRIGGER tI_G_PLANT BEFORE INSERT ON G_PLANT for each row
-- ERwin Builtin Trigger
-- INSERT trigger on G_PLANT 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  G_PLANT on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0001dc39", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_54", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.AP_CODE = SF.AP_CODE AND
        :new.STAMP = SF.STAMP;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert G_PLANT because SF does not exist.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* PLANT  G_PLANT on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_53", FK_COLUMNS="PLANT_CODE" */
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
        'Cannot insert G_PLANT because PLANT does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_G_PLANT AFTER UPDATE ON G_PLANT for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on G_PLANT 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  G_PLANT on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0001d9a0", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_54", FK_COLUMNS="AP_CODE""STAMP" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.AP_CODE = SF.AP_CODE AND
      :new.STAMP = SF.STAMP;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update G_PLANT because SF does not exist.'
    );
  END IF;

  /* ERwin Builtin Trigger */
  /* PLANT  G_PLANT on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_53", FK_COLUMNS="PLANT_CODE" */
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
      'Cannot update G_PLANT because PLANT does not exist.'
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
    /* PLANT  G_PLANT on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0000d9e3", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_53", FK_COLUMNS="PLANT_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM G_PLANT
      WHERE
        /*  %JoinFKPK(G_PLANT,:%Old," = "," AND") */
        G_PLANT.PLANT_CODE = :old.PLANT_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete PLANT because G_PLANT exists.'
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
  /* PLANT  G_PLANT on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="0001023b", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_53", FK_COLUMNS="PLANT_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.PLANT_CODE <> :new.PLANT_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM G_PLANT
      WHERE
        /*  %JoinFKPK(G_PLANT,:%Old," = "," AND") */
        G_PLANT.PLANT_CODE = :old.PLANT_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update PLANT because G_PLANT exists.'
      );
    END IF;
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER  tD_PLANT_USER AFTER DELETE ON PLANT_USER for each row
-- ERwin Builtin Trigger
-- DELETE trigger on PLANT_USER 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* PLANT_USER  AP on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0000cbdd", PARENT_OWNER="", PARENT_TABLE="PLANT_USER"
    CHILD_OWNER="", CHILD_TABLE="AP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_48", FK_COLUMNS="USER_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM AP
      WHERE
        /*  %JoinFKPK(AP,:%Old," = "," AND") */
        AP.USER_CODE = :old.USER_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete PLANT_USER because AP exists.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_PLANT_USER AFTER UPDATE ON PLANT_USER for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on PLANT_USER 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* PLANT_USER  AP on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000f9db", PARENT_OWNER="", PARENT_TABLE="PLANT_USER"
    CHILD_OWNER="", CHILD_TABLE="AP"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_48", FK_COLUMNS="USER_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.USER_CODE <> :new.USER_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM AP
      WHERE
        /*  %JoinFKPK(AP,:%Old," = "," AND") */
        AP.USER_CODE = :old.USER_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update PLANT_USER because AP exists.'
      );
    END IF;
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_PORT_INFO BEFORE INSERT ON PORT_INFO for each row
-- ERwin Builtin Trigger
-- INSERT trigger on PORT_INFO 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  PORT_INFO on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000ec8d", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="PORT_INFO"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_55", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.AP_CODE = SF.AP_CODE AND
        :new.STAMP = SF.STAMP;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert PORT_INFO because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_PORT_INFO AFTER UPDATE ON PORT_INFO for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on PORT_INFO 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  PORT_INFO on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000eeee", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="PORT_INFO"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_55", FK_COLUMNS="AP_CODE""STAMP" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.AP_CODE = SF.AP_CODE AND
      :new.STAMP = SF.STAMP;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update PORT_INFO because SF does not exist.'
    );
  END IF;


-- ERwin Builtin Trigger
END;
/


CREATE  TRIGGER tI_SENSOR_DATA BEFORE INSERT ON SENSOR_DATA for each row
-- ERwin Builtin Trigger
-- INSERT trigger on SENSOR_DATA 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  SENSOR_DATA on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000ed83", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="SENSOR_DATA"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.AP_CODE = SF.AP_CODE AND
        :new.STAMP = SF.STAMP;
    IF (
      /* %NotnullFK(:%New," IS NOT NULL AND") */
      
      NUMROWS = 0
    )
    THEN
      raise_application_error(
        -20002,
        'Cannot insert SENSOR_DATA because SF does not exist.'
      );
    END IF;


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_SENSOR_DATA AFTER UPDATE ON SENSOR_DATA for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on SENSOR_DATA 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* SF  SENSOR_DATA on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0000edbf", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="SENSOR_DATA"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="AP_CODE""STAMP" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.AP_CODE = SF.AP_CODE AND
      :new.STAMP = SF.STAMP;
  IF (
    /* %NotnullFK(:%New," IS NOT NULL AND") */
    
    NUMROWS = 0
  )
  THEN
    raise_application_error(
      -20007,
      'Cannot update SENSOR_DATA because SF does not exist.'
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
    /* ERWIN_RELATION:CHECKSUM="0000de04", PARENT_OWNER="", PARENT_TABLE="AP"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_45", FK_COLUMNS="AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM AP
      WHERE
        /* %JoinFKPK(:%New,AP," = "," AND") */
        :new.AP_CODE = AP.AP_CODE;
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


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER  tD_SF AFTER DELETE ON SF for each row
-- ERwin Builtin Trigger
-- DELETE trigger on SF 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  CS_LOG on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0004ab1e", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_58", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM CS_LOG
      WHERE
        /*  %JoinFKPK(CS_LOG,:%Old," = "," AND") */
        CS_LOG.AP_CODE = :old.AP_CODE AND
        CS_LOG.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because CS_LOG exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  PORT_INFO on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="PORT_INFO"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_55", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM PORT_INFO
      WHERE
        /*  %JoinFKPK(PORT_INFO,:%Old," = "," AND") */
        PORT_INFO.AP_CODE = :old.AP_CODE AND
        PORT_INFO.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because PORT_INFO exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  G_PLANT on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_54", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM G_PLANT
      WHERE
        /*  %JoinFKPK(G_PLANT,:%Old," = "," AND") */
        G_PLANT.AP_CODE = :old.AP_CODE AND
        G_PLANT.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because G_PLANT exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  DEVICE_LOG on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM DEVICE_LOG
      WHERE
        /*  %JoinFKPK(DEVICE_LOG,:%Old," = "," AND") */
        DEVICE_LOG.AP_CODE = :old.AP_CODE AND
        DEVICE_LOG.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because DEVICE_LOG exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  SENSOR_DATA on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="SENSOR_DATA"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="AP_CODE""STAMP" */
    SELECT count(*) INTO NUMROWS
      FROM SENSOR_DATA
      WHERE
        /*  %JoinFKPK(SENSOR_DATA,:%Old," = "," AND") */
        SENSOR_DATA.AP_CODE = :old.AP_CODE AND
        SENSOR_DATA.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because SENSOR_DATA exists.'
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
  /* SF  CS_LOG on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="000668ef", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_58", FK_COLUMNS="AP_CODE""STAMP" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.AP_CODE <> :new.AP_CODE OR 
    :old.STAMP <> :new.STAMP
  THEN
    SELECT count(*) INTO NUMROWS
      FROM CS_LOG
      WHERE
        /*  %JoinFKPK(CS_LOG,:%Old," = "," AND") */
        CS_LOG.AP_CODE = :old.AP_CODE AND
        CS_LOG.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because CS_LOG exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  PORT_INFO on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="PORT_INFO"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_55", FK_COLUMNS="AP_CODE""STAMP" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.AP_CODE <> :new.AP_CODE OR 
    :old.STAMP <> :new.STAMP
  THEN
    SELECT count(*) INTO NUMROWS
      FROM PORT_INFO
      WHERE
        /*  %JoinFKPK(PORT_INFO,:%Old," = "," AND") */
        PORT_INFO.AP_CODE = :old.AP_CODE AND
        PORT_INFO.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because PORT_INFO exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  G_PLANT on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_54", FK_COLUMNS="AP_CODE""STAMP" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.AP_CODE <> :new.AP_CODE OR 
    :old.STAMP <> :new.STAMP
  THEN
    SELECT count(*) INTO NUMROWS
      FROM G_PLANT
      WHERE
        /*  %JoinFKPK(G_PLANT,:%Old," = "," AND") */
        G_PLANT.AP_CODE = :old.AP_CODE AND
        G_PLANT.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because G_PLANT exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  DEVICE_LOG on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="AP_CODE""STAMP" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.AP_CODE <> :new.AP_CODE OR 
    :old.STAMP <> :new.STAMP
  THEN
    SELECT count(*) INTO NUMROWS
      FROM DEVICE_LOG
      WHERE
        /*  %JoinFKPK(DEVICE_LOG,:%Old," = "," AND") */
        DEVICE_LOG.AP_CODE = :old.AP_CODE AND
        DEVICE_LOG.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because DEVICE_LOG exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  SENSOR_DATA on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="SENSOR_DATA"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="AP_CODE""STAMP" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.AP_CODE <> :new.AP_CODE OR 
    :old.STAMP <> :new.STAMP
  THEN
    SELECT count(*) INTO NUMROWS
      FROM SENSOR_DATA
      WHERE
        /*  %JoinFKPK(SENSOR_DATA,:%Old," = "," AND") */
        SENSOR_DATA.AP_CODE = :old.AP_CODE AND
        SENSOR_DATA.STAMP = :old.STAMP;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because SENSOR_DATA exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* AP  SF on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="AP"
    CHILD_OWNER="", CHILD_TABLE="SF"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_45", FK_COLUMNS="AP_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM AP
    WHERE
      /* %JoinFKPK(:%New,AP," = "," AND") */
      :new.AP_CODE = AP.AP_CODE;
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


-- ERwin Builtin Trigger
END;
/


COMMENT ON TABLE AP IS '공유기';
 
    COMMENT ON COLUMN AP.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN AP.USER_CODE IS '사용자코드';  
    COMMENT ON COLUMN AP.AP_PUBLIC_IP IS '공용ip';  
    COMMENT ON COLUMN AP.AP_SSID IS 'SSID';  
    COMMENT ON COLUMN AP.AP_REG_DATE IS '공유기등록일';  
    COMMENT ON COLUMN AP.AP_SF_CNT IS '재배기수';  
    
COMMENT ON TABLE CS_LOG IS '배양액공급기록';
 
    COMMENT ON COLUMN CS_LOG.CS_PROVIDE_DT IS '공급일자';  
    COMMENT ON COLUMN CS_LOG.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN CS_LOG.STAMP IS '스탬프';  
    COMMENT ON COLUMN CS_LOG.CS_RES IS '결과';  
    
COMMENT ON TABLE DEVICE_LOG IS '로그기록';
 
    COMMENT ON COLUMN DEVICE_LOG.USED_DT IS '사용일자';  
    COMMENT ON COLUMN DEVICE_LOG.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN DEVICE_LOG.STAMP IS '스탬프';  
    COMMENT ON COLUMN DEVICE_LOG.ACT_NAME IS '동작이름';  
    COMMENT ON COLUMN DEVICE_LOG.USED_IP IS '수행IP';  
    COMMENT ON COLUMN DEVICE_LOG.USED_RES IS '수행결과';  
    
COMMENT ON TABLE G_PLANT IS '생육식물';
 
    COMMENT ON COLUMN G_PLANT.PLANT_CODE IS '식물코드';  
    COMMENT ON COLUMN G_PLANT.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN G_PLANT.STAMP IS '스탬프';  
    COMMENT ON COLUMN G_PLANT.FARMING_DATE IS '재배시작일';  
    
COMMENT ON TABLE OAUTH_CLIENT_DETAILS IS '클라이언트';
 
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.CLIENT_ID IS '클라이언트코드';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.RESOURCE_IDS IS '리소스아이디';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.CLIENT_SECRET IS '시크릿';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.SCOPE IS '범위';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.AUTHORIZED_GRANT_TYPES IS '그랜트';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.WEB_SERVER_REDIRECT_URI IS '리디렉션';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.AUTHORITIES IS '인증권한';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.ACCESS_TOKEN_VALIDITY IS '토큰';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.REFRESH_TOKEN_VALIDITY IS '리프레쉬';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.ADDITIONAL_INFORMATION IS '부가정보';  
    COMMENT ON COLUMN OAUTH_CLIENT_DETAILS.AUTOAPPROVE IS '자동어프로브';  
    
COMMENT ON TABLE PLANT IS '식물';
 
    COMMENT ON COLUMN PLANT.PLANT_CODE IS '식물코드';  
    COMMENT ON COLUMN PLANT.PLANT_NAME IS '식물이름';  
    COMMENT ON COLUMN PLANT.OPT_TEMP IS '최적온도';  
    COMMENT ON COLUMN PLANT.MAX_TEMP IS '최대온도';  
    COMMENT ON COLUMN PLANT.MIN_TEMP IS '최저온도';  
    COMMENT ON COLUMN PLANT.MIN_PH IS '최저ph';  
    COMMENT ON COLUMN PLANT.MAX_PH IS '최대ph';  
    COMMENT ON COLUMN PLANT.MIN_EC IS '최저ec';  
    COMMENT ON COLUMN PLANT.MAX_EC IS '최대ec';  
    
COMMENT ON TABLE PLANT_USER IS '사용자';
 
    COMMENT ON COLUMN PLANT_USER.USER_CODE IS '사용자코드';  
    COMMENT ON COLUMN PLANT_USER.PWD IS '패스워드';  
    COMMENT ON COLUMN PLANT_USER.EMAIL IS '이메일';  
    COMMENT ON COLUMN PLANT_USER.FIRST_NAME IS '성';  
    COMMENT ON COLUMN PLANT_USER.SF_CNT IS '보유재배기갯수';  
    COMMENT ON COLUMN PLANT_USER.SECOND_NAME IS '이름';  
    COMMENT ON COLUMN PLANT_USER.BLOCK IS '계정중지';  
    COMMENT ON COLUMN PLANT_USER.ENABLED IS '사용가능여부';  
    
COMMENT ON TABLE PORT_INFO IS '포트정보';
 
    COMMENT ON COLUMN PORT_INFO.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN PORT_INFO.PORT_NO IS '포트번호';  
    COMMENT ON COLUMN PORT_INFO.STAMP IS '스탬프';  
    COMMENT ON COLUMN PORT_INFO.PORT_ST IS '포트상태';  
    
COMMENT ON TABLE SENSOR_DATA IS '센서데이터';
 
    COMMENT ON COLUMN SENSOR_DATA.CALC_DT IS '측정시간';  
    COMMENT ON COLUMN SENSOR_DATA.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN SENSOR_DATA.STAMP IS '스탬프';  
    COMMENT ON COLUMN SENSOR_DATA.TEMP IS '온도';  
    COMMENT ON COLUMN SENSOR_DATA.HUMI IS '습도';  
    COMMENT ON COLUMN SENSOR_DATA.ELUM IS '조도';  
    COMMENT ON COLUMN SENSOR_DATA.WATER_TEMP IS '수온';  
    COMMENT ON COLUMN SENSOR_DATA.WATER_LIM IS '수위';  
    COMMENT ON COLUMN SENSOR_DATA.EC IS 'ec';  
    COMMENT ON COLUMN SENSOR_DATA.PH IS 'ph';  
    
COMMENT ON TABLE SF IS '수경재배기';
 
    COMMENT ON COLUMN SF.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN SF.STAMP IS '스탬프';  
    COMMENT ON COLUMN SF.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN SF.SF_PORT_CNT IS '화분수';  
    COMMENT ON COLUMN SF.FLOOR_CNT IS '층수';  
    COMMENT ON COLUMN SF.COOLER_CNT IS '쿨러갯수';  
    COMMENT ON COLUMN SF.LED_CTRL_MODE IS 'LED제어모드';  
    COMMENT ON COLUMN SF.INNER_IP IS '내부ip';  
    COMMENT ON COLUMN SF.COOLER1_ST IS '쿨러A상태';  
    COMMENT ON COLUMN SF.LED21_ST IS 'LED21상태';  
    COMMENT ON COLUMN SF.LED22_ST IS 'LED22상태';  
    COMMENT ON COLUMN SF.LED31_ST IS 'LED31상태';  
    COMMENT ON COLUMN SF.LED32_ST IS 'LED32상태';  
    COMMENT ON COLUMN SF.PUMP_ST IS '펌프상태';  
    COMMENT ON COLUMN SF.SF_REG_DATE IS '재배기등록일';  
    COMMENT ON COLUMN SF.COOLER2_ST IS '쿨러B상태';  
    COMMENT ON COLUMN SF.COOLER3_ST IS '쿨러C상태';  
    




