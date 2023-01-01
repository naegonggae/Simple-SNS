package com.final_project_leesanghun_team2.domain.dto;

import com.final_project_leesanghun_team2.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostDto {
    private Integer id;
    private String title;
    private String body;
    private String userName;
    private LocalDateTime createdAt;

    public static PostDto fromEntity(Post postEntity) {
        return PostDto.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .userName(postEntity.getUser().getUserName())
                .createdAt(postEntity.getCreatedAt())
                .build();
    }

    /* Page<Entity> -> Page<Dto> 변환처리 */
    public static Page<PostDto> toDtoList(Page<Post> postEntities){
        Page<PostDto> postDtoList = postEntities.map(m -> PostDto.builder()
                .id(m.getId())
                .title(m.getTitle())
                .body(m.getBody())
                .userName(m.getUser().getUserName())
                .createdAt(m.getCreatedAt())
                .build());
        return postDtoList;
    }
}
