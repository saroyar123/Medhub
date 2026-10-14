package com.medhub.RagChat.Service;

import com.medhub.RagChat.Model.ParentChunk;
import com.medhub.RagChat.Repository.ParentChunkRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.*;

public class ParentChildIngestionService {
    private final VectorStore vectorStore;
    private final ParentChunkRepository parentChunkRepository;

    public ParentChildIngestionService(VectorStore vectorStore,ParentChunkRepository parentChunkRepository){
        this.vectorStore=vectorStore;
        this.parentChunkRepository=parentChunkRepository;
    }
    public  void ingestWithParentChild(List<Document> rawDocument,String medicalCategory,String fileName){
        TokenTextSplitter parentSplitter=TokenTextSplitter.builder()
                .withChunkSize(800)
                .withMinChunkSizeChars(200)
                .withMinChunkLengthToEmbed(10)
                .withMaxNumChunks(50)
                .withKeepSeparator(true)
                .build();

        List<Document> parentChunks=parentSplitter.apply(rawDocument);

//        variable to save the chunks
        List<Document> allChildDocuments=new ArrayList<>();
        List<ParentChunk> parentEntitiesToSave=new ArrayList<>();

        for(Document parent : parentChunks){
            String parentId= UUID.randomUUID().toString();
            String parentText=parent.getText();

            ParentChunk parentEntity=new ParentChunk(parentId,parentText,medicalCategory,fileName);
            parentEntitiesToSave.add(parentEntity);

            TokenTextSplitter childSplitter=TokenTextSplitter.builder()
                    .withChunkSize(150)
                    .withMinChunkSizeChars(30)
                    .withMinChunkLengthToEmbed(5)
                    .withMaxNumChunks(20)
                    .withKeepSeparator(true)
                    .build();

            List<Document> childChunks=childSplitter.apply(List.of(parent));

            for (Document child:childChunks){
                Map<String, Object> childMetadata=new HashMap<>(child.getMetadata());
                childMetadata.put("parentId",parentId);
                childMetadata.put("medicalCategory",medicalCategory);

                Document linkedChild=new Document(child.getId(),child.getText(),childMetadata);
                allChildDocuments.add(linkedChild); 
            }
        }
    }
}
