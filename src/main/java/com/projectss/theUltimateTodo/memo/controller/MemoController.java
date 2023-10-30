package com.projectss.theUltimateTodo.memo.controller;

import com.projectss.theUltimateTodo.memo.dto.MemoRequest;
import com.projectss.theUltimateTodo.memo.service.MemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memos")
@Tag(name = "메모 API")
public class MemoController {

    private final MemoService memoService;

    @Operation(summary = "디렉토리 안에 메모 저장 & 업데이트 API", description = "메모 저장 & 업데이트")
    @PostMapping("/{directoryId}")
    public ResponseEntity<?> createMemo(@PathVariable String directoryId,
                                        @RequestBody MemoRequest dto) {
        String loginUserEmail = "test@naver.com";
        memoService.createMemo(loginUserEmail, directoryId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "메모 업데이트 API")
    @PatchMapping("/{memoId}")
    public ResponseEntity<?> updateMemo(@PathVariable String memoId,
                                        @RequestBody MemoRequest dto) {
        String loginUserEmail = "test@naver.com";
        memoService.updateMemo(loginUserEmail, memoId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "메모 위치 수정 API", description = "메모 드래그 앤 드랍")
    @PatchMapping("/{memoId}/{directoryId}/{targetDirectoryId}")
    public ResponseEntity<?> updateMemoLocation(@PathVariable String memoId,
                                                @PathVariable String directoryId,
                                                @PathVariable String targetDirectoryId) {
        String loginUserEmail = "test@naver.com";
        memoService.moveLocation(loginUserEmail, memoId, directoryId, targetDirectoryId);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "메모 삭제 API")
    @DeleteMapping("/{memoId}")
    public ResponseEntity<?> deleteMemo(@PathVariable String memoId) {
        String loginUserEmail = "test@naver.com";
        memoService.deleteMemo(loginUserEmail, memoId);
        return ResponseEntity.ok().body("ok");
    }
}
