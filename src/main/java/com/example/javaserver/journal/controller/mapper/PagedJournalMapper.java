package com.example.javaserver.journal.controller.mapper;

import com.example.javaserver.journal.controller.dto.PagedJournalDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {
        JournalMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true)
)
@Component
public interface PagedJournalMapper {
    PagedJournalDto toPagedJournalDto(PagedJournal pagedJournal);
}
