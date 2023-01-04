package com.final_project_leesanghun_team2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor // 원인은 간단했다. Entity클래스를 반환해주는 과정에서 클래스의 JSON Serialize 과정에서 오류가 났었다.
//Resolved [org.springframework.http.converter.HttpMessageNotReadableException:
// JSON parse error: Cannot construct instance of `com.final_project_leesanghun_team2.domain.dto.CommentRequest` (although at least one Creator exists):
// cannot deserialize from Object value (no delegate- or property-based Creator);
// nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException:<< 이거 검색하면 나옴
// Cannot construct instance of `com.final_project_leesanghun_team2.domain.dto.CommentRequest` (although at least one Creator exists):
// cannot deserialize from Object value (no delegate- or property-based Creator)<EOL> at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream);
// line: 2, column: 3]]
@Getter
public class CommentRequest {
    private String comment;
}
