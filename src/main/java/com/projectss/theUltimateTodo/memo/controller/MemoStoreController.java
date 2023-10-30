package com.projectss.theUltimateTodo.memo.controller;

import com.projectss.theUltimateTodo.memo.domain.MemoStore;
import com.projectss.theUltimateTodo.memo.service.MemoStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo-stores")
@Tag(name = "메모 최상위 루트(구조) 조회 API")
public class MemoStoreController {

    private final MemoStoreService memoStoreService;

    @Operation(summary = "메모 최상위 루트(구조) 조회 API")
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemoStore> getRootByUser() {
        String loginUserEmail = "test@naver.com";
        MemoStore memoStoreByUser = memoStoreService.getMemoStoreByUser(loginUserEmail);
        return ResponseEntity.ok().body(memoStoreByUser);
    }
}
