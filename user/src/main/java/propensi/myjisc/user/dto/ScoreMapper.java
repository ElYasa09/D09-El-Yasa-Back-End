package propensi.myjisc.user.dto;

import org.mapstruct.Mapper;

import propensi.myjisc.user.model.Score;

@Mapper(componentModel = "spring")
public interface ScoreMapper {
    Score createScoreDTOToScore(CreateScoreDTO createScoreDTO);

    Score updateScoreDTOToScore(UpdateScoreDTO updateScoreDTO);
}
