package ru.gitverse.bizzareowl.mgateway.web.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.gitverse.bizzareowl.mgateway.gateway.MessageSourceData;
import ru.gitverse.bizzareowl.mgateway.gateway.RawAlertMessage;
import ru.gitverse.bizzareowl.mgateway.web.dto.MessageSourceDataDto;
import ru.gitverse.bizzareowl.mgateway.web.dto.RawAlertMessageDto;

@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RawAlertMassageDtoMapper {

    RawAlertMessage toDomain(RawAlertMessageDto rawAlertMessageDto);

    MessageSourceData toDomain(MessageSourceDataDto messageSourceDataDto);

}
