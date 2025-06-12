package com.email.writer.service

import com.email.writer.entity.EmailRequest
import org.springframework.stereotype.Service


@Service
class EmailGeneratorService {

    fun generateEmailReply(emailRequest: EmailRequest){
        val prompt = buildPrompt(emailRequest);
         
    }
    fun buildPrompt(emailRequest: EmailRequest):String{
        val prompt: StringBuilder = StringBuilder()
        prompt.append("Generate a professional email reply for the following email content.")
        if(!emailRequest.tone.equals(null) && !emailRequest.tone.isEmpty()){
            prompt.append("Use a").append(emailRequest.tone).append(" tone.")
        }
        prompt.append("\nOriginal email:\n").append(emailRequest.emailContent);
        return prompt.toString()
    }
}