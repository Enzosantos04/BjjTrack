package enzosdev.bjjtrack;

import org.springframework.boot.SpringApplication;

public class TestBjjTrackApplication {

    public static void main(String[] args) {
        SpringApplication.from(BjjTrackApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
