package com.email.writer.service

import com.email.writer.entity.EmailRequest
import com.email.writer.entity.EmailResponse
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import com.google.cloud.aiplatform.v1beta1.PredictRequest
import com.google.cloud.aiplatform.v1beta1.PredictResponse
import com.google.cloud.aiplatform.v1beta1.PredictionServiceClient
import com.google.protobuf.Value
import com.google.protobuf.Struct

@Service
class EmailGeneratorService(
    @Value("${gemini.project.id}") private val projectId: String,
    @Value("${gemini.location}") private val location: String,
    @Value("${gemini.model.id}") private val modelId: String
) {
    
    private val restTemplate = RestTemplate()
    
    fun generateEmailReply(emailRequest: EmailRequest): EmailResponse {
        validateEmailRequest(emailRequest)
        
        val prompt = buildPrompt(emailRequest)
        val aiResponse = callGeminiApi(prompt)
        
        return EmailResponse(
            subject = generateSubject(emailRequest.emailContent),
            body = formatEmailBody(aiResponse),
            tone = emailRequest.tone
        )
    }
    
    private fun buildPrompt(emailRequest: EmailRequest): String {
        val prompt = StringBuilder()
        prompt.append("Generate a professional email reply for the following email content.")
        
        if (!emailRequest.tone.isNullOrBlank()) {
            prompt.append(" Use a ").append(emailRequest.tone).append(" tone.")
        }
        
        if (!emailRequest.style.isNullOrBlank()) {
            prompt.append(" Style: ").append(emailRequest.style)
        }
        
        prompt.append("\nOriginal email:\n").append(emailRequest.emailContent)
        prompt.append("\n\nReply:")
        
        return prompt.toString()
    }
    
    private fun callGeminiApi(prompt: String): String {
        val client = PredictionServiceClient.create()
        try {
            val endpoint = "projects/$projectId/locations/$location/publishers/google/models/$modelId"
            val instances = listOf(Struct.newBuilder().putFields("content", Value.newBuilder().setStringValue(prompt).build()).build())
            
            val request = PredictRequest.newBuilder()
                .setEndpoint(endpoint)
                .addAllInstances(instances)
                .build()
            
            val response = client.predict(request)
            return response.predictionsList.firstOrNull()?.let { prediction ->
                (prediction as? Value)?.stringValue ?: ""
            } ?: throw RuntimeException("Failed to generate email reply")
        } finally {
            client.close()
        }
    }
    
    private fun generateSubject(originalEmail: String): String {
        return "Re: " + originalEmail.split("\n").firstOrNull()?.trim()?.take(50) ?: "No Subject"
    }
    
    private fun formatEmailBody(content: String): String {
        return content.trim().split("\n").joinToString("\n") { line ->
            if (line.isBlank()) "\n" else "${line.trim()}\n"
        }
    }
    
    private fun validateEmailRequest(request: EmailRequest) {
        if (request.emailContent.isNullOrBlank()) {
            throw IllegalArgumentException("Email content cannot be empty")
        }
        
        if (request.emailContent.length > 4000) {
            throw IllegalArgumentException("Email content is too long")
        }
    }
}