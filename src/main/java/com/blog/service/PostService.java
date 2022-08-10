package com.blog.service;

import com.blog.domain.Post;
import com.blog.domain.PostEditor;
import com.blog.exception.PostNotFound;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
          //PostCreateDTO -> Post
        Post post = Post.builder()
                        .title(postCreate.getTitle())
                                .content(postCreate.getContent())
                                        .build();

        System.out.println("write");
         postRepository.save(post);
    }

    //조회API
    //단건 조회 API (1개의 글을 가져오는 기능)
    public PostResponse get(Long postId) {
        //엔티티를 바로 반환하면 안된다.
        Post post= postRepository.findById(postId).orElseThrow(()->new PostNotFound());

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return response;
    }





    //문제점:페이징 처리X , 페이징하기 어렵다
    public List<PostResponse> getList(PostSearch postSearch){
//        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"id"));
         return postRepository.getList(postSearch).stream().map(post ->
             new PostResponse(post)) //빌더로 하기에는 반복작업이 너무 많다.
                 .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);

        return new PostResponse(post);
    }


    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        postRepository.delete(post);
    }
}
