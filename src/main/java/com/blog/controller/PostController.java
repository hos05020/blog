package com.blog.controller;

import com.blog.exception.InvalidRequest;
import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import com.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

      private  final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request){
        //Case1. 저장한 데이터 Entity -> Response로 응답
        //Case2. 저장한 데이터의 primary_id -> response로 응답
        // Client에서는 수신한 id를 글 조회 API를 통해서 글 데이터를 수신받음
        //Case3. 응답 필요 없음 -> 클라이언트에서 모든 Post(글) 데이터 Context를 잘 관리함
        // Bad Case : 서버에서 -> 반드시 이렇게 할것입니다! fix
        //                  ->서버에서 차라리 유연하게 대응하는 게 좋습니다. -> 코드를 잘 짜야겠죠?!
        //                  ->한 번에 일괄저긍로 잘 처리되는 케이스가 없습니다 . ->잘 관리하는 형태가 중요
          request.validate();
         postService.write(request);
    }


    /**
     *  /posts -> 글 전체 조회(검색+페이징)
     *  /posts/{postId} -> 글 한개만 조회
     *
     */

    //조회API
    //단건 조회 API (1개의 글을 가져오는 기능)
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
        
        //Request 클래스 -> for validation
        //Response 클래스 -> 서비스 정책을 위한 로직이 담겨 있다
        
        PostResponse response = postService.get(postId);
        return response;
    }

    //조회API
    //여러개의 글 조회 API
    // /posts
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId,  @RequestBody @Valid PostEdit request){
        postService.edit(postId,request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }

}
