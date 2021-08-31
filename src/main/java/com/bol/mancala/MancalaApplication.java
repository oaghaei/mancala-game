package com.bol.mancala;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MancalaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MancalaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    static class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
