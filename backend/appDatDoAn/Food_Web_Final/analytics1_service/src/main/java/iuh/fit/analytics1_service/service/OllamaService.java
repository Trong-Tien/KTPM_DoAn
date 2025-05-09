package iuh.fit.analytics1_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OllamaService {

    private final WebClient.Builder webClientBuilder;

    public String ask(String prompt) {
        Map<String, Object> body = Map.of(
                "model", "mistral",   // Hoặc model nào bạn đã `ollama run`
                "prompt", prompt,
                "stream", false       // ⛔ Quan trọng: tắt stream để xử lý dễ hơn
        );

        System.out.println("📤 Prompt gửi đến Ollama:\n" + prompt);

        String result = webClientBuilder.build()
                .post()
                .uri("http://localhost:11434/api/generate")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("📥 Phản hồi từ Ollama:\n" + response);
                    return (String) response.get("response");
                })
                .block();

        return result;
    }
}
