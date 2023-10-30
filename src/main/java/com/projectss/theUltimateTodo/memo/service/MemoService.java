package com.projectss.theUltimateTodo.memo.service;

import com.projectss.theUltimateTodo.memo.domain.Directory;
import com.projectss.theUltimateTodo.memo.domain.Memo;
import com.projectss.theUltimateTodo.memo.dto.MemoRequest;
import com.projectss.theUltimateTodo.memo.repository.DirectoryRepository;
import com.projectss.theUltimateTodo.memo.repository.MemoRepository;
import com.projectss.theUltimateTodo.memo.repository.MemoStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoStoreRepository memoStoreRepository;
    private final MemoRepository memoRepository;
    private final DirectoryRepository directoryRepository;

    public void createMemo(String email, String directoryId, MemoRequest dto) {
        if(memoStoreRepository.existsByEmail(email)) {
            throw new IllegalStateException("no memo store by user email");
        }
        Memo memo = new Memo(dto);

        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new IllegalStateException("no directory by directory id"));

        memoRepository.save(memo);
        directory.saveMemo(memo);
        directoryRepository.save(directory);
    }

    public void updateMemo(String email, String memoId, MemoRequest dto) {
        if(memoStoreRepository.existsByEmail(email)) {
            throw new IllegalStateException("no memo store by user email");
        }
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalStateException("no memo by memo id"));
        memo.update(dto);
        memoRepository.save(memo);
    }

    public void deleteMemo(String email, String memoId) {
        if(memoStoreRepository.existsByEmail(email)) {
            throw new IllegalStateException("no memo store by user email");
        }
        memoRepository.deleteById(memoId);
    }

    public void moveLocation(String email, String memoId, String directoryId, String targetDirectoryId) {
        if(memoStoreRepository.existsByEmail(email)) {
            throw new IllegalStateException("no memo store by user email");
        }
        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new IllegalStateException("no directory by directory id"));
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalStateException("no memo by memo id"));
        directory.deleteMemo(memo);
        directoryRepository.save(directory);

        Directory targetDirectory = directoryRepository.findById(targetDirectoryId)
                .orElseThrow(() -> new IllegalStateException("no directory by target directory id"));
        targetDirectory.saveMemo(memo);
        directoryRepository.save(targetDirectory);
    }
}
