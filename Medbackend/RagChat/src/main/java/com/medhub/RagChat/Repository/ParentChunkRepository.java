package com.medhub.RagChat.Repository;

import com.medhub.RagChat.Model.ParentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParentChunkRepository extends JpaRepository<ParentChunk, UUID> {
}
