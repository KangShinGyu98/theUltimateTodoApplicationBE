package com.projectss.theUltimateTodo.memo.controller;

import com.projectss.theUltimateTodo.memo.dto.DirectoryRequest;
import com.projectss.theUltimateTodo.memo.service.DirectoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/directories")
@Tag(name = "디렉토리 API")
public class DirectoryController {

    private final DirectoryService directoryService;

    @Operation(summary = "최상위 디렉토리 저장 & 업데이트 API", description = "최상위 디렉토리 저장 & 업데이트")
    @PostMapping("/root")
    public ResponseEntity<?> createRootDirectory(@RequestBody DirectoryRequest dto) {
        String loginUserEmail = "test@naver.com";
        directoryService.saveRootDirectory(loginUserEmail, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "디렉토리 안에 디렉토리 저장 API", description = "디렉토리 저장")
    @PostMapping("/{directoryId}")
    public ResponseEntity<?> createDirectory(@PathVariable String directoryId,
                                             @RequestBody DirectoryRequest dto) {
        String loginUserEmail = "test@naver.com";
        directoryService.saveDirectoryInDirectory(loginUserEmail, directoryId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "최상단 디렉토리 위치 수정 API", description = "최상단 디렉토리 드래그 앤 드랍")
    @PatchMapping("/{directoryId}/{targetDirectoryId}")
    public ResponseEntity<?> updateRootDirectoryLocation(@PathVariable String directoryId,
                                                     @PathVariable String targetDirectoryId) {
        String loginUserEmail = "test@naver.com";
        directoryService.moveRootDirectoryLocation(loginUserEmail, directoryId, targetDirectoryId);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "디렉토리 위치 수정 API", description = "디렉토리 드래그 앤 드랍")
    @PatchMapping("/{parentDirectoryId}/{directoryId}/{targetDirectoryId}")
    public ResponseEntity<?> updateDirectoryLocation(@PathVariable String parentDirectoryId,
                                                     @PathVariable String directoryId,
                                                     @PathVariable String targetDirectoryId) {
        String loginUserEmail = "test@naver.com";
        directoryService.moveDirectoryLocation(loginUserEmail, parentDirectoryId, directoryId, targetDirectoryId);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "디렉토리 삭제 API", description = "디렉토리 삭제")
    @DeleteMapping("/{directoryId}")
    public ResponseEntity<?> deleteDirectory(@PathVariable String directoryId) {
        String loginUserEmail = "test@naver.com";
        directoryService.deleteDirectory(loginUserEmail, directoryId);
        return ResponseEntity.ok().body("ok");
    }
}
