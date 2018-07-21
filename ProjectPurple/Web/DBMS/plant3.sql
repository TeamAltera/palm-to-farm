
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
	AP_PUBLIC_IP         VARCHAR(15) NULL ,
	USER_CODE            NUMBER(8) NOT NULL ,
	AP_CODE              NUMBER(8) NOT NULL 
);



CREATE UNIQUE INDEX XPK공유기 ON AP
(AP_CODE   ASC);



ALTER TABLE AP
	ADD CONSTRAINT  XPK공유기 PRIMARY KEY (AP_CODE);



CREATE TABLE CS
(
	CS_PROVIDE_CNT       NUMBER(3) NULL ,
	CS_CNT               NUMBER(3) NULL ,
	SF_CODE              NUMBER(3) NOT NULL ,
	AP_CODE              NUMBER(8) NOT NULL 
);



CREATE UNIQUE INDEX XPK배양액 ON CS
(SF_CODE   ASC,AP_CODE   ASC);



ALTER TABLE CS
	ADD CONSTRAINT  XPK배양액 PRIMARY KEY (SF_CODE,AP_CODE);



CREATE TABLE CS_LOG
(
	SF_CODE              NUMBER(3) NOT NULL ,
	CS_PROVIDE_DT        CHAR(18) NOT NULL ,
	CS_RES               CHAR(1) NULL ,
	AP_CODE              NUMBER(8) NOT NULL 
);



CREATE UNIQUE INDEX XPK배양액공급기록 ON CS_LOG
(SF_CODE   ASC,CS_PROVIDE_DT   ASC,AP_CODE   ASC);



ALTER TABLE CS_LOG
	ADD CONSTRAINT  XPK배양액공급기록 PRIMARY KEY (SF_CODE,CS_PROVIDE_DT,AP_CODE);



CREATE TABLE DEVICE_LOG
(
	USED_DT              DATE NOT NULL ,
	ACT_NAME             VARCHAR(24) NULL ,
	USED_IP              VARCHAR(15) NULL ,
	USED_RES             CHAR(1) NULL ,
	SF_CODE              NUMBER(3) NOT NULL ,
	AP_CODE              NUMBER(8) NOT NULL 
);



CREATE UNIQUE INDEX XPK로그기록 ON DEVICE_LOG
(USED_DT   ASC,SF_CODE   ASC,AP_CODE   ASC);



ALTER TABLE DEVICE_LOG
	ADD CONSTRAINT  XPK로그기록 PRIMARY KEY (USED_DT,SF_CODE,AP_CODE);



CREATE TABLE GROWTH
(
	SF_PORT_NO           NUMBER(2) NOT NULL ,
	FLOOR_NUM            NUMBER(2) NULL ,
	GROWTH_DT            DATE NULL ,
	FINISH_DT            DATE NULL ,
	SF_CODE              NUMBER(3) NOT NULL ,
	AP_CODE              NUMBER(8) NOT NULL ,
	PLANT_CODE           NUMBER(3) NOT NULL 
);



CREATE UNIQUE INDEX XPK생육 ON GROWTH
(SF_CODE   ASC,AP_CODE   ASC,SF_PORT_NO   ASC);



ALTER TABLE GROWTH
	ADD CONSTRAINT  XPK생육 PRIMARY KEY (SF_CODE,AP_CODE,SF_PORT_NO);



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



CREATE TABLE PLANT_USER
(
	USER_CODE            NUMBER(8) NOT NULL ,
	PWD                  VARCHAR(64) NULL ,
	EMAIL                VARCHAR(30) NULL ,
	FIRST_NAME           VARCHAR(16) NULL ,
	SF_CNT               NUMBER(3) NULL ,
	SECOND_NAME          VARCHAR(30) NULL ,
	BLOCK                NUMBER(1) NULL  CONSTRAINT  Time_Limit_402066275 CHECK (BLOCK BETWEEN 0 AND 5)
);



CREATE UNIQUE INDEX XPK사용자 ON PLANT_USER
(USER_CODE   ASC);



ALTER TABLE PLANT_USER
	ADD CONSTRAINT  XPK사용자 PRIMARY KEY (USER_CODE);



CREATE TABLE SENSOR_DATA
(
	CALC_DT              DATE NOT NULL ,
	TEMP                 NUMBER(5,2) NULL ,
	SF_CODE              NUMBER(3) NOT NULL ,
	HUMI                 NUMBER(5,2) NULL ,
	ELUM                 NUMBER(3) NULL ,
	WATER_TEMP           NUMBER(5,2) NULL ,
	WATER_LIM            NUMBER(3) NULL ,
	AP_CODE              NUMBER(8) NOT NULL 
);



CREATE UNIQUE INDEX XPK센서데이터 ON SENSOR_DATA
(CALC_DT   ASC,SF_CODE   ASC,AP_CODE   ASC);



ALTER TABLE SENSOR_DATA
	ADD CONSTRAINT  XPK센서데이터 PRIMARY KEY (CALC_DT,SF_CODE,AP_CODE);



CREATE TABLE SF
(
	SF_CODE              NUMBER(3) NOT NULL ,
	SF_PORT_CNT          NUMBER(2) NULL ,
	FLOOR_CNT            NUMBER(2) NULL ,
	COOLER_CNT           NUMBER(2) NULL ,
	LED_CTRL_MODE        CHAR(1) NULL ,
	INNER_IP             VARCHAR(15) NULL ,
	AP_CODE              NUMBER(8) NOT NULL ,
	COOLER_ST            CHAR(1) NULL ,
	LED_ST               CHAR(1) NULL ,
	PUMP_ST              CHAR(1) NULL 
);



CREATE UNIQUE INDEX XPK수경재배기 ON SF
(SF_CODE   ASC,AP_CODE   ASC);



ALTER TABLE SF
	ADD CONSTRAINT  XPK수경재배기 PRIMARY KEY (SF_CODE,AP_CODE);



CREATE  VIEW 장비View ( AP_SSID,USER_CODE,AP_PUBLIC_IP,SF_CODE,SF_PORT_CNT,FLOOR_CNT,COOLER_CNT,LED_CTRL_MODE,INNER_IP ) 
	 AS  SELECT AP.AP_SSID,AP.USER_CODE,AP.AP_PUBLIC_IP,SF.SF_CODE,SF.SF_PORT_CNT,SF.FLOOR_CNT,SF.COOLER_CNT,SF.LED_CTRL_MODE,SF.INNER_IP
		FROM SF,AP;



ALTER TABLE AP
	ADD (CONSTRAINT R_48 FOREIGN KEY (USER_CODE) REFERENCES PLANT_USER (USER_CODE));



ALTER TABLE CS
	ADD (CONSTRAINT R_27 FOREIGN KEY (SF_CODE, AP_CODE) REFERENCES SF (SF_CODE, AP_CODE));



ALTER TABLE CS_LOG
	ADD (CONSTRAINT R_28 FOREIGN KEY (SF_CODE, AP_CODE) REFERENCES CS (SF_CODE, AP_CODE));



ALTER TABLE DEVICE_LOG
	ADD (CONSTRAINT R_11 FOREIGN KEY (SF_CODE, AP_CODE) REFERENCES SF (SF_CODE, AP_CODE));



ALTER TABLE GROWTH
	ADD (CONSTRAINT R_33 FOREIGN KEY (SF_CODE, AP_CODE) REFERENCES SF (SF_CODE, AP_CODE));



ALTER TABLE GROWTH
	ADD (CONSTRAINT R_50 FOREIGN KEY (PLANT_CODE) REFERENCES PLANT (PLANT_CODE));



ALTER TABLE PLANT
	ADD (CONSTRAINT R_30 FOREIGN KEY (ADMIN_CODE) REFERENCES ADMIN (ADMIN_CODE));



ALTER TABLE SENSOR_DATA
	ADD (CONSTRAINT R_5 FOREIGN KEY (SF_CODE, AP_CODE) REFERENCES SF (SF_CODE, AP_CODE));



ALTER TABLE SF
	ADD (CONSTRAINT R_45 FOREIGN KEY (AP_CODE) REFERENCES AP (AP_CODE));



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


CREATE  TRIGGER tI_CS BEFORE INSERT ON CS for each row
-- ERwin Builtin Trigger
-- INSERT trigger on CS 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  CS on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000e057", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE AND
        :new.AP_CODE = SF.AP_CODE;
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
    /* ERWIN_RELATION:CHECKSUM="0000dbcd", PARENT_OWNER="", PARENT_TABLE="CS"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM CS_LOG
      WHERE
        /*  %JoinFKPK(CS_LOG,:%Old," = "," AND") */
        CS_LOG.SF_CODE = :old.SF_CODE AND
        CS_LOG.AP_CODE = :old.AP_CODE;
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
  /* ERWIN_RELATION:CHECKSUM="0001fe67", PARENT_OWNER="", PARENT_TABLE="CS"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="SF_CODE""AP_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE OR 
    :old.AP_CODE <> :new.AP_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM CS_LOG
      WHERE
        /*  %JoinFKPK(CS_LOG,:%Old," = "," AND") */
        CS_LOG.SF_CODE = :old.SF_CODE AND
        CS_LOG.AP_CODE = :old.AP_CODE;
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
    FK_CONSTRAINT="R_27", FK_COLUMNS="SF_CODE""AP_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE AND
      :new.AP_CODE = SF.AP_CODE;
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
    /* ERWIN_RELATION:CHECKSUM="0000efe6", PARENT_OWNER="", PARENT_TABLE="CS"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM CS
      WHERE
        /* %JoinFKPK(:%New,CS," = "," AND") */
        :new.SF_CODE = CS.SF_CODE AND
        :new.AP_CODE = CS.AP_CODE;
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
  /* ERWIN_RELATION:CHECKSUM="0000ed6b", PARENT_OWNER="", PARENT_TABLE="CS"
    CHILD_OWNER="", CHILD_TABLE="CS_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="SF_CODE""AP_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM CS
    WHERE
      /* %JoinFKPK(:%New,CS," = "," AND") */
      :new.SF_CODE = CS.SF_CODE AND
      :new.AP_CODE = CS.AP_CODE;
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
    /* ERWIN_RELATION:CHECKSUM="0000edab", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE AND
        :new.AP_CODE = SF.AP_CODE;
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
  /* ERWIN_RELATION:CHECKSUM="0000e98c", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="DEVICE_LOG"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_11", FK_COLUMNS="SF_CODE""AP_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE AND
      :new.AP_CODE = SF.AP_CODE;
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


CREATE  TRIGGER tI_GROWTH BEFORE INSERT ON GROWTH for each row
-- ERwin Builtin Trigger
-- INSERT trigger on GROWTH 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* PLANT  GROWTH on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0001e705", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_50", FK_COLUMNS="PLANT_CODE" */
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

    /* ERwin Builtin Trigger */
    /* SF  GROWTH on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE AND
        :new.AP_CODE = SF.AP_CODE;
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


-- ERwin Builtin Trigger
END;
/

CREATE  TRIGGER tU_GROWTH AFTER UPDATE ON GROWTH for each row
-- ERwin Builtin Trigger
-- UPDATE trigger on GROWTH 
DECLARE NUMROWS INTEGER;
BEGIN
  /* ERwin Builtin Trigger */
  /* PLANT  GROWTH on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="0001ee71", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_50", FK_COLUMNS="PLANT_CODE" */
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

  /* ERwin Builtin Trigger */
  /* SF  GROWTH on child update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="SF_CODE""AP_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE AND
      :new.AP_CODE = SF.AP_CODE;
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
    /* ERWIN_RELATION:CHECKSUM="0000d9d1", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_50", FK_COLUMNS="PLANT_CODE" */
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
  /* ERWIN_RELATION:CHECKSUM="00020383", PARENT_OWNER="", PARENT_TABLE="PLANT"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_50", FK_COLUMNS="PLANT_CODE" */
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


CREATE  TRIGGER tI_SENSOR_DATA BEFORE INSERT ON SENSOR_DATA for each row
-- ERwin Builtin Trigger
-- INSERT trigger on SENSOR_DATA 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  SENSOR_DATA on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0000f75b", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="SENSOR_DATA"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SF
      WHERE
        /* %JoinFKPK(:%New,SF," = "," AND") */
        :new.SF_CODE = SF.SF_CODE AND
        :new.AP_CODE = SF.AP_CODE;
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
  /* ERWIN_RELATION:CHECKSUM="0000f484", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="SENSOR_DATA"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="SF_CODE""AP_CODE" */
  SELECT count(*) INTO NUMROWS
    FROM SF
    WHERE
      /* %JoinFKPK(:%New,SF," = "," AND") */
      :new.SF_CODE = SF.SF_CODE AND
      :new.AP_CODE = SF.AP_CODE;
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
    /* SF  GROWTH on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0003ab15", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM GROWTH
      WHERE
        /*  %JoinFKPK(GROWTH,:%Old," = "," AND") */
        GROWTH.SF_CODE = :old.SF_CODE AND
        GROWTH.AP_CODE = :old.AP_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because GROWTH exists.'
      );
    END IF;

    /* ERwin Builtin Trigger */
    /* SF  CS on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM CS
      WHERE
        /*  %JoinFKPK(CS,:%Old," = "," AND") */
        CS.SF_CODE = :old.SF_CODE AND
        CS.AP_CODE = :old.AP_CODE;
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
    FK_CONSTRAINT="R_11", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM DEVICE_LOG
      WHERE
        /*  %JoinFKPK(DEVICE_LOG,:%Old," = "," AND") */
        DEVICE_LOG.SF_CODE = :old.SF_CODE AND
        DEVICE_LOG.AP_CODE = :old.AP_CODE;
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
    FK_CONSTRAINT="R_5", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM SENSOR_DATA
      WHERE
        /*  %JoinFKPK(SENSOR_DATA,:%Old," = "," AND") */
        SENSOR_DATA.SF_CODE = :old.SF_CODE AND
        SENSOR_DATA.AP_CODE = :old.AP_CODE;
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
  /* SF  GROWTH on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00055c1e", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="GROWTH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="SF_CODE""AP_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE OR 
    :old.AP_CODE <> :new.AP_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM GROWTH
      WHERE
        /*  %JoinFKPK(GROWTH,:%Old," = "," AND") */
        GROWTH.SF_CODE = :old.SF_CODE AND
        GROWTH.AP_CODE = :old.AP_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because GROWTH exists.'
      );
    END IF;
  END IF;

  /* ERwin Builtin Trigger */
  /* SF  CS on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="CS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="SF_CODE""AP_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE OR 
    :old.AP_CODE <> :new.AP_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM CS
      WHERE
        /*  %JoinFKPK(CS,:%Old," = "," AND") */
        CS.SF_CODE = :old.SF_CODE AND
        CS.AP_CODE = :old.AP_CODE;
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
    FK_CONSTRAINT="R_11", FK_COLUMNS="SF_CODE""AP_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE OR 
    :old.AP_CODE <> :new.AP_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM DEVICE_LOG
      WHERE
        /*  %JoinFKPK(DEVICE_LOG,:%Old," = "," AND") */
        DEVICE_LOG.SF_CODE = :old.SF_CODE AND
        DEVICE_LOG.AP_CODE = :old.AP_CODE;
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
    FK_CONSTRAINT="R_5", FK_COLUMNS="SF_CODE""AP_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE OR 
    :old.AP_CODE <> :new.AP_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM SENSOR_DATA
      WHERE
        /*  %JoinFKPK(SENSOR_DATA,:%Old," = "," AND") */
        SENSOR_DATA.SF_CODE = :old.SF_CODE AND
        SENSOR_DATA.AP_CODE = :old.AP_CODE;
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


COMMENT ON TABLE ADMIN IS '관리자';
 
    COMMENT ON COLUMN ADMIN.ADMIN_CODE IS '관리자코드';  
    COMMENT ON COLUMN ADMIN.ID IS '아이디';  
    COMMENT ON COLUMN ADMIN.PWD IS '패스워드';  
    COMMENT ON COLUMN ADMIN.REG_USER_CNT IS '관리하는사용자수';  
    
COMMENT ON TABLE AP IS '공유기';
 
    COMMENT ON COLUMN AP.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN AP.USER_CODE IS '사용자코드';  
    COMMENT ON COLUMN AP.AP_PUBLIC_IP IS '공용ip';  
    COMMENT ON COLUMN AP.AP_SSID IS 'SSID';  
    
COMMENT ON TABLE CS IS '배양액';
 
    COMMENT ON COLUMN CS.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN CS.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN CS.CS_PROVIDE_CNT IS '공급횟수';  
    COMMENT ON COLUMN CS.CS_CNT IS '남은횟수';  
    
COMMENT ON TABLE CS_LOG IS '배양액공급기록';
 
    COMMENT ON COLUMN CS_LOG.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN CS_LOG.CS_PROVIDE_DT IS '공급일자';  
    COMMENT ON COLUMN CS_LOG.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN CS_LOG.CS_RES IS '결과';  
    
COMMENT ON TABLE DEVICE_LOG IS '로그기록';
 
    COMMENT ON COLUMN DEVICE_LOG.USED_DT IS '사용일자';  
    COMMENT ON COLUMN DEVICE_LOG.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN DEVICE_LOG.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN DEVICE_LOG.ACT_NAME IS '동작이름';  
    COMMENT ON COLUMN DEVICE_LOG.USED_IP IS '수행IP';  
    COMMENT ON COLUMN DEVICE_LOG.USED_RES IS '수행결과';  
    
COMMENT ON TABLE GROWTH IS '생육';
 
    COMMENT ON COLUMN GROWTH.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN GROWTH.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN GROWTH.SF_PORT_NO IS '화분번호';  
    COMMENT ON COLUMN GROWTH.PLANT_CODE IS '식물코드';  
    COMMENT ON COLUMN GROWTH.FLOOR_NUM IS '층번호';  
    COMMENT ON COLUMN GROWTH.GROWTH_DT IS '생육일자';  
    COMMENT ON COLUMN GROWTH.FINISH_DT IS '예상완료일자';  
    
COMMENT ON TABLE PLANT IS '식물';
 
    COMMENT ON COLUMN PLANT.PLANT_CODE IS '식물코드';  
    COMMENT ON COLUMN PLANT.PLANT_NAME IS '식물이름';  
    COMMENT ON COLUMN PLANT.ADMIN_CODE IS '관리자코드';  
    
COMMENT ON TABLE PLANT_USER IS '사용자';
 
    COMMENT ON COLUMN PLANT_USER.USER_CODE IS '사용자코드';  
    COMMENT ON COLUMN PLANT_USER.PWD IS '패스워드';  
    COMMENT ON COLUMN PLANT_USER.EMAIL IS '이메일';  
    COMMENT ON COLUMN PLANT_USER.FIRST_NAME IS '성';  
    COMMENT ON COLUMN PLANT_USER.SF_CNT IS '보유재배기갯수';  
    COMMENT ON COLUMN PLANT_USER.SECOND_NAME IS '이름';  
    COMMENT ON COLUMN PLANT_USER.BLOCK IS '계정중지';  
    
COMMENT ON TABLE SENSOR_DATA IS '센서데이터';
 
    COMMENT ON COLUMN SENSOR_DATA.CALC_DT IS '측정시간';  
    COMMENT ON COLUMN SENSOR_DATA.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN SENSOR_DATA.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN SENSOR_DATA.TEMP IS '온도';  
    COMMENT ON COLUMN SENSOR_DATA.HUMI IS '습도';  
    COMMENT ON COLUMN SENSOR_DATA.ELUM IS '조도';  
    COMMENT ON COLUMN SENSOR_DATA.WATER_TEMP IS '수온';  
    COMMENT ON COLUMN SENSOR_DATA.WATER_LIM IS '수위';  
    
COMMENT ON TABLE SF IS '수경재배기';
 
    COMMENT ON COLUMN SF.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN SF.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN SF.SF_PORT_CNT IS '화분수';  
    COMMENT ON COLUMN SF.FLOOR_CNT IS '층수';  
    COMMENT ON COLUMN SF.COOLER_CNT IS '쿨러갯수';  
    COMMENT ON COLUMN SF.LED_CTRL_MODE IS 'LED제어모드';  
    COMMENT ON COLUMN SF.INNER_IP IS '내부ip';  
    COMMENT ON COLUMN SF.COOLER_ST IS '쿨러상태';  
    COMMENT ON COLUMN SF.LED_ST IS 'LED상태';  
    COMMENT ON COLUMN SF.PUMP_ST IS '펌프상태';  
    




