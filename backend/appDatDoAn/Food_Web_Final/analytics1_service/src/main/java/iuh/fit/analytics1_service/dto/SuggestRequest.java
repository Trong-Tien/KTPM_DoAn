package iuh.fit.analytics1_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class SuggestRequest {
    private String prompt;
    private List<String> availableItems;
}
