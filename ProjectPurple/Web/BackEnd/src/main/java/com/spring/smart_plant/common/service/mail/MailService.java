package com.spring.smart_plant.common.service.mail;

public interface MailService {
	/** 
     *  @param subject 제목
     *  @param text 내용
     *  @param from 보내는 메일 주소
     *  @param to 받는 메일 주소
     *  @param filePath 첨부 파일 경로: 첨부파일 없을시 null **/
	 public boolean send(String subject, String text, String from, String to, String filePath);
}
