package com.example.board.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// (modelmapper 사용하기 위한)
@Configuration // 환경설정 파일(클래스)
public class RootConfig {
    @Bean // springboot에서 new해서 관리하라.

    public ModelMapper getMappter() {
        ModelMapper modelmapper = new ModelMapper();

        modelmapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelmapper;

    }
}
