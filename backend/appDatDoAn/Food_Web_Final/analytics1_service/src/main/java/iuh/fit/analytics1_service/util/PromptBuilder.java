package iuh.fit.analytics1_service.util;

import java.util.List;

public class PromptBuilder {

    public static String buildSuggestPrompt(String userInput, List<String> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("Bạn là chuyên gia ẩm thực.\n");
        sb.append("Người dùng nói: \"").append(userInput).append("\".\n");
        sb.append("Danh sách món ăn hiện có:\n");

        for (String item : items) {
            sb.append("- ").append(item).append("\n");
        }

        sb.append("""
            Hãy chọn các món phù hợp nhất với mong muốn người dùng. 
            Tránh chọn các món không liên quan. 
            Chỉ trả về danh sách tên món, ngắn gọn, cách nhau bởi dấu phẩy.
            Ví dụ: Cơm tấm, Chè thái
        """);

        return sb.toString();
    }
}
