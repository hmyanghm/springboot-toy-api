package com.toy.api.springboot.service.posts;

import com.toy.api.springboot.domain.posts.Posts;
import com.toy.api.springboot.domain.posts.PostsRepository;
import com.toy.api.springboot.web.dto.PostsListResponseDto;
import com.toy.api.springboot.web.dto.PostsResponseDto;
import com.toy.api.springboot.web.dto.PostsSaveRequestDto;
import com.toy.api.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //update 기능에서 쿼리를 날리는 부분이 없는 이유는 JPA의 영속성 컨텍스트 때문
    //영속성 컨텍스트란? entity를 영구 저장하는 환경
    //JPA의 핵심 내용은 entity가 영속성 컨텍스트에 포함이 되어 있느냐 아니냐로 갈림
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

}
