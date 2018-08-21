
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



CREATE TABLE G_PLANT
(
	FARMING_DATE         DATE NULL ,
	PLANT_CODE           NUMBER(4) NOT NULL ,
	SF_CODE              NUMBER(3) NOT NULL ,
	AP_CODE              NUMBER(8) NOT NULL 
);



CREATE UNIQUE INDEX XPK생육식물 ON G_PLANT
(PLANT_CODE   ASC,AP_CODE   ASC,SF_CODE   ASC);



ALTER TABLE G_PLANT
	ADD CONSTRAINT  XPK생육식물 PRIMARY KEY (PLANT_CODE,AP_CODE,SF_CODE);



CREATE TABLE PLANT
(
	PLANT_CODE           NUMBER(4) NOT NULL ,
	OPT_TEMP             NUMBER(5,2) NULL ,
	MAX_TEMP             NUMBER(5,2) NULL ,
	MIN_TEMP             NUMBER(5,2) NULL ,
	MIN_PH               NUMBER(5,2) NULL ,
	MAX_PH               NUMBER(5,2) NULL ,
	PLANT_NAME           VARCHAR(30) NULL 
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
	BLOCK                NUMBER(1) NULL 
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
	AP_CODE              NUMBER(8) NOT NULL ,
	EC                   NUMBER(5,2) NULL ,
	PH                   NUMBER(5,2) NULL 
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
	PUMP_ST              CHAR(1) NULL ,
	SF_REG_DATE          DATE NULL 
);



CREATE UNIQUE INDEX XPK수경재배기 ON SF
(SF_CODE   ASC,AP_CODE   ASC);



ALTER TABLE SF
	ADD CONSTRAINT  XPK수경재배기 PRIMARY KEY (SF_CODE,AP_CODE);



CREATE  VIEW DEV_VIEW ( AP_SSID,USER_CODE,AP_PUBLIC_IP,SF_CODE,SF_PORT_CNT,FLOOR_CNT,COOLER_CNT,LED_CTRL_MODE,INNER_IP ) 
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



ALTER TABLE G_PLANT
	ADD (CONSTRAINT R_53 FOREIGN KEY (PLANT_CODE) REFERENCES PLANT (PLANT_CODE));



ALTER TABLE G_PLANT
	ADD (CONSTRAINT R_54 FOREIGN KEY (SF_CODE, AP_CODE) REFERENCES SF (SF_CODE, AP_CODE));



ALTER TABLE SENSOR_DATA
	ADD (CONSTRAINT R_5 FOREIGN KEY (SF_CODE, AP_CODE) REFERENCES SF (SF_CODE, AP_CODE));



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


CREATE  TRIGGER tI_G_PLANT BEFORE INSERT ON G_PLANT for each row
-- ERwin Builtin Trigger
-- INSERT trigger on G_PLANT 
DECLARE NUMROWS INTEGER;
BEGIN
    /* ERwin Builtin Trigger */
    /* SF  G_PLANT on child insert restrict */
    /* ERWIN_RELATION:CHECKSUM="0001e760", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_54", FK_COLUMNS="SF_CODE""AP_CODE" */
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
  /* ERWIN_RELATION:CHECKSUM="0001ebe7", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_54", FK_COLUMNS="SF_CODE""AP_CODE" */
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
    /* SF  G_PLANT on parent delete restrict */
    /* ERWIN_RELATION:CHECKSUM="0003bf61", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_54", FK_COLUMNS="SF_CODE""AP_CODE" */
    SELECT count(*) INTO NUMROWS
      FROM G_PLANT
      WHERE
        /*  %JoinFKPK(G_PLANT,:%Old," = "," AND") */
        G_PLANT.SF_CODE = :old.SF_CODE AND
        G_PLANT.AP_CODE = :old.AP_CODE;
    IF (NUMROWS > 0)
    THEN
      raise_application_error(
        -20001,
        'Cannot delete SF because G_PLANT exists.'
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
  /* SF  G_PLANT on parent update restrict */
  /* ERWIN_RELATION:CHECKSUM="00056eba", PARENT_OWNER="", PARENT_TABLE="SF"
    CHILD_OWNER="", CHILD_TABLE="G_PLANT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_54", FK_COLUMNS="SF_CODE""AP_CODE" */
  IF
    /* %JoinPKPK(:%Old,:%New," <> "," OR ") */
    :old.SF_CODE <> :new.SF_CODE OR 
    :old.AP_CODE <> :new.AP_CODE
  THEN
    SELECT count(*) INTO NUMROWS
      FROM G_PLANT
      WHERE
        /*  %JoinFKPK(G_PLANT,:%Old," = "," AND") */
        G_PLANT.SF_CODE = :old.SF_CODE AND
        G_PLANT.AP_CODE = :old.AP_CODE;
    IF (NUMROWS > 0)
    THEN 
      raise_application_error(
        -20005,
        'Cannot update SF because G_PLANT exists.'
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


COMMENT ON TABLE AP IS '공유기';
 
    COMMENT ON COLUMN AP.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN AP.USER_CODE IS '사용자코드';  
    COMMENT ON COLUMN AP.AP_PUBLIC_IP IS '공용ip';  
    COMMENT ON COLUMN AP.AP_SSID IS 'SSID';  
    COMMENT ON COLUMN AP.AP_REG_DATE IS '공유기등록일';  
    COMMENT ON COLUMN AP.AP_SF_CNT IS '재배기수';  
    
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
    
COMMENT ON TABLE G_PLANT IS '생육식물';
 
    COMMENT ON COLUMN G_PLANT.PLANT_CODE IS '식물코드';  
    COMMENT ON COLUMN G_PLANT.AP_CODE IS '공유기코드';  
    COMMENT ON COLUMN G_PLANT.SF_CODE IS '재배기코드';  
    COMMENT ON COLUMN G_PLANT.FARMING_DATE IS '재배시작일';  
    
COMMENT ON TABLE PLANT IS '식물';
 
    COMMENT ON COLUMN PLANT.PLANT_CODE IS '식물코드';  
    COMMENT ON COLUMN PLANT.PLANT_NAME IS '식물이름';  
    COMMENT ON COLUMN PLANT.OPT_TEMP IS '최적온도';  
    COMMENT ON COLUMN PLANT.MAX_TEMP IS '최대온도';  
    COMMENT ON COLUMN PLANT.MIN_TEMP IS '최저온도';  
    COMMENT ON COLUMN PLANT.MIN_PH IS '최저ph';  
    COMMENT ON COLUMN PLANT.MAX_PH IS '최대ph';  
    
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
    COMMENT ON COLUMN SENSOR_DATA.EC IS 'ec';  
    COMMENT ON COLUMN SENSOR_DATA.PH IS 'ph';  
    
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
    COMMENT ON COLUMN SF.SF_REG_DATE IS '재배기등록일';  
    




