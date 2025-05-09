package iuh.fit.analytics1_service.controller;

import iuh.fit.analytics1_service.dto.ChatRequestDTO;
import iuh.fit.analytics1_service.dto.SuggestRequest;
import iuh.fit.analytics1_service.service.OllamaService;
import iuh.fit.analytics1_service.util.PromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final OllamaService ollamaService;

    @PostMapping("/suggest")
    public ResponseEntity<List<String>> suggest(@RequestBody SuggestRequest request) {
        String prompt = PromptBuilder.buildSuggestPrompt(request.getPrompt(), request.getAvailableItems());
        String aiResponse = ollamaService.ask(prompt);

        List<String> suggestions = Arrays.stream(aiResponse.split("[,\\n]"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();

        return ResponseEntity.ok(suggestions);
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody ChatRequestDTO request) {
        String prompt = "Bạn là nhân viên tư vấn thực phẩm. Trả lời lịch sự, ngắn gọn, rõ ràng. Không cần quá chi tiết .\n"
                + "Khách hàng hỏi: \"" + request.getMessage() + "\"";

        String response = ollamaService.ask(prompt);
        return ResponseEntity.ok(response);
    }




}

