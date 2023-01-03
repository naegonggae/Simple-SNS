package com.final_project_leesanghun_team2.domain.response;

import com.final_project_leesanghun_team2.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostGetResponse {
    private Integer id;
    private String title;
    private String body;
    private String userName;
    private LocalDateTime createdAt;

    public static PostGetResponse fromEntity(Post postEntity) {
        return PostGetResponse.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .userName(postEntity.getUser().getUserName())
                .createdAt(postEntity.getCreatedAt())
                .build();
    }

    /* Page<Entity> -> Page<Dto> 변환처리 */
    public static Page<PostGetResponse> toDtoList(Page<Post> postEntities){
        Page<PostGetResponse> postDtoList = postEntities.map(m -> PostGetResponse.builder()
                .id(m.getId())
                .title(m.getTitle())
                .body(m.getBody())
                .userName(m.getUser().getUserName())
                .createdAt(m.getCreatedAt())
                .build());
        return postDtoList;
    }
}
